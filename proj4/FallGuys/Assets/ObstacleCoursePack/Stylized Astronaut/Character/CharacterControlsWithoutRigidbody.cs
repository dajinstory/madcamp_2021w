using UnityEngine;
using System.Collections;

[RequireComponent(typeof(Rigidbody))]
[RequireComponent(typeof(CapsuleCollider))]

public class CharacterControlsWithoutRigidbody : MonoBehaviour {

	// externel components
	public GameObject cam;
	private Animator anim;
	private CharacterController controller;

	// parameters
	public float gravity = 10.0f;
	public float speed = 10.0f;
	public float rotateSpeed = 25f; //Speed the player rotate
	public float fallSpeed = 0.0f;
	public float maxFallSpeed = 100000.0f;
	public float jumpHeight = 4.0f;

	// variables related to controlling movement
	private Rigidbody rb;
	public Vector3 moveVelocity;
	public Vector3 jumpVelocity;
	public Vector3 fallVelocity;

	private Vector3 moveDir = Vector3.zero;
	private bool isMove;
	private bool isJump;
	private bool isGrounded;

	// Externel Force
	private bool canMove = true; //If player is not hitted
	private bool isStuned = false;
	private bool wasStuned = false; //If player was stunned before get stunned another time
	private float pushForce;
	private Vector3 pushDir;
	private bool slide = false;

	// Checkpoint
	public Vector3 checkPoint;

	void Start() {
		controller = GetComponent<CharacterController>();
		anim = gameObject.GetComponentInChildren<Animator>();
	}

	void Awake()
	{
		rb = GetComponent<Rigidbody>();
		rb.freezeRotation = true;
		rb.useGravity = false;

		checkPoint = transform.position;
		Cursor.visible = false;
	}

	private void FixedUpdate()
	{
		// Rotation
		if (isMove)
		{
			Vector3 targetDir = moveDir; //Direction of the character

			targetDir.y = 0;
			if (targetDir == Vector3.zero)
			{
				targetDir = transform.forward;
			}
			Quaternion tr = Quaternion.LookRotation(targetDir); //Rotation of the character to where it moves
            Quaternion targetRotation = Quaternion.Slerp(transform.rotation, tr, Time.deltaTime * rotateSpeed); //Rotate the character little by little
            transform.rotation = targetRotation;
		}

		// (x,z) Movement
		moveVelocity = moveDir * speed;

		// (y) Jump and Gravity
		if (isJump)  // Jump
		{
			jumpVelocity = new Vector3(0, CalculateJumpVerticalSpeed(), 0);

			fallSpeed += gravity * Time.deltaTime;
			fallSpeed = Mathf.Clamp(fallSpeed, 0.0f, maxFallSpeed);
			fallVelocity = new Vector3(0, -fallSpeed, 0);
		}
		else if (!isGrounded) // Fall
        {
			jumpVelocity = new Vector3(0, 0, 0);

			fallSpeed += gravity * Time.deltaTime;
			fallSpeed = Mathf.Clamp(fallSpeed, 0.0f, maxFallSpeed);
			fallVelocity = new Vector3(0, -fallSpeed, 0);
		}
		else // Ground
        {
			jumpVelocity = new Vector3(0, 0, 0);

			fallSpeed = 0.0f;
			fallVelocity = new Vector3(0, -fallSpeed, 0);

			isJump = false;
		}

		// Adjust Movement
		controller.Move((moveVelocity + jumpVelocity + fallVelocity) * Time.deltaTime);
		isGrounded = controller.isGrounded;
	}


    void Update (){
        // moveDir & isJump
        float h = Input.GetAxis("Horizontal");
        float v = Input.GetAxis("Vertical");
        Vector3 v2 = v * cam.transform.forward; //Vertical axis to which I want to move with respect to the camera
        Vector3 h2 = h * cam.transform.right; //Horizontal axis to which I want to move with respect to the camera
        moveDir = (v2 + h2).normalized; //Global position to which I want to move in magnitude 1
        isMove = moveDir.x != 0 || moveDir.z != 0;
		isJump = Input.GetButton("Jump");


		// Animation Effect
		if (isGrounded)
		{
			// Idle : 0
			// Run : 1
			// Jump : 2
			// Fall : 3
			if (isJump)
			{
				anim.SetInteger("AnimationPar", 2);
			}
			else if (isMove)
			{
				anim.SetInteger("AnimationPar", 1);
			}
			else
			{
				anim.SetInteger("AnimationPar", 0);
			}
		}
	}

	float CalculateJumpVerticalSpeed()
	{
		return Mathf.Sqrt(2 * jumpHeight * gravity);
	}

	public void HitPlayer(Vector3 velocityF, float time)
	{
		rb.velocity = velocityF;

		pushForce = velocityF.magnitude;
		pushDir = Vector3.Normalize(velocityF);
		StartCoroutine(Decrease(velocityF.magnitude, time));
	}

	public void LoadCheckPoint()
	{
		transform.position = checkPoint;
	}

	private IEnumerator Decrease(float value, float duration)
	{
		if (isStuned)
			wasStuned = true;
		isStuned = true;
		canMove = false;

		float delta = 0;
		delta = value / duration;

		for (float t = 0; t < duration; t += Time.deltaTime)
		{
			yield return null;
			if (!slide) //Reduce the force if the ground isnt slide
			{
				pushForce = pushForce - Time.deltaTime * delta;
				pushForce = pushForce < 0 ? 0 : pushForce;
				//Debug.Log(pushForce);
			}
			rb.AddForce(new Vector3(0, -gravity * GetComponent<Rigidbody>().mass, 0)); //Add gravity
		}

		if (wasStuned)
		{
			wasStuned = false;
		}
		else
		{
			isStuned = false;
			canMove = true;
		}
	}
}
