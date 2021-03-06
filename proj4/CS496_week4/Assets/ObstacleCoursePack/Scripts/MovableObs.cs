﻿using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class MovableObs : MonoBehaviour
{
	public float distance = 5f; //Distance that moves the object
	public bool horizontal = true; //If the movement is horizontal or vertical
	public float speed = 3f;
	public float offset = 0f; //If yo want to modify the position at the start 

	private bool isForward = true; //If the movement is out
	private Vector3 startPos;

	public int moveType;
	/* 0: horizontal
	   1: vertical
	   2: up
	   3: down
	   4: horizontal2*/

	void Awake()
	{
		startPos = transform.position;
		setType();
		switch (moveType)
		{
			case 0:
				transform.position += Vector3.right * offset;
				break;
			case 1:
				transform.position += Vector3.forward * offset;
				break;
			case 2:
				transform.position += Vector3.down * offset;
				break;
			case 3:
				startPos = new Vector3(startPos.x, startPos.y + distance, startPos.z);
				transform.position += Vector3.down * offset;
				break;
			case 4:
				transform.position += Vector3.right * offset;
				distance = 9f;
				speed = 15f;
				break;
			case 5:
				distance = 9f;
				speed = 15f;
				startPos = new Vector3(startPos.x - distance, startPos.y, startPos.z);
				transform.position += Vector3.right * offset;
				break;

		}
	}
	private void setType()
	{
		if (moveType < 2)
		{
			if (horizontal)
				moveType = 0;
			else
				moveType = 1;
		}
		else if (moveType < 4)
        {
			distance = 5f;
			speed = 5f;
		}
	}

	// Update is called once per frame
	void Update()
	{
		switch (moveType)
		{
			case 0:
			case 4:
			case 5:
				if (isForward)
				{
					if (transform.position.x < startPos.x + distance)
					{
						transform.position += Vector3.right * Time.deltaTime * speed;
					}
					else
						isForward = false;
				}
				else
				{
					if (transform.position.x > startPos.x)
					{
						transform.position -= Vector3.right * Time.deltaTime * speed;
					}
					else
						isForward = true;
				}
				break;
			case 1:
				if (isForward)
				{
					if (transform.position.z < startPos.z + distance)
					{
						transform.position += Vector3.forward * Time.deltaTime * speed;
					}
					else
						isForward = false;
				}
				else
				{
					if (transform.position.z > startPos.z)
					{
						transform.position -= Vector3.forward * Time.deltaTime * speed;
					}
					else
						isForward = true;
				}
				break;
			case 2:
			case 3:
				if (isForward)
				{
					if (transform.position.y > startPos.y - distance)
					{
						transform.position += Vector3.down * Time.deltaTime * speed;
					}
                    else
						isForward = false;
				}
				else
				{
					if (transform.position.y < startPos.y)
					{
						transform.position -= Vector3.down * Time.deltaTime * speed;
					}
					else
						isForward = true;
				}
				break;
		}
	}
}
