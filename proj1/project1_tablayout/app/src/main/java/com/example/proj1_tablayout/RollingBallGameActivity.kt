package com.example.proj1_tablayout

import android.content.Context
import android.content.Intent
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Rect
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.Surface
import android.view.SurfaceHolder
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.rolling_ball_game_activity.*
import kotlin.math.sqrt
import kotlin.random.Random.Default.nextFloat
import kotlin.random.Random.Default.nextInt

class RollingBallGameActivity: AppCompatActivity(), SensorEventListener{

    private var sensorManager: SensorManager? = null

    //private val colorList = mutableListOf(Color.MAGENTA, Color.GREEN, Color.DKGRAY, Color.CYAN)

    private var a_x:Float = 0f
    private var a_y:Float = 0f
    private var a_z:Float = 0f

    lateinit var mainHandler: Handler
    lateinit var gameView: GameView

    lateinit var restartButton: Button
    lateinit var quitButton: Button

    private val updatePosition = object : Runnable {
        fun normalUpdate(){
            gameView.updateUserBall(a_x, a_y)

            gameView.invalidate()

            if (nextFloat()>0.99){
                gameView.loadRandomBullet(nextInt(1,5))
            }
            mainHandler.postDelayed(this, 10)
        }

        override fun run() {
            when (gameView.life) {
                3 -> {
                    normalUpdate()
                    scoreText.text = "Score : ${gameView.score}"
                }
                2 -> {
                    normalUpdate()
                    heart3.setColorFilter(Color.BLACK)
                    scoreText.text = "Score : ${gameView.score}"
                }
                1 -> {
                    normalUpdate()
                    heart2.setColorFilter(Color.BLACK)
                    scoreText.text = "Score : ${gameView.score}"
                }
                else -> {
                    restartButton.visibility = View.VISIBLE
                    quitButton.visibility = View.VISIBLE
                    gameView.invalidate()
                    heart1.setColorFilter(Color.BLACK)
                }
            }
        }
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event!!.sensor.type == Sensor.TYPE_ACCELEROMETER) {
            a_x = event.values[0]
            a_y = event.values[1]
            a_z = event.values[2]

//            val accel_magnitude = sqrt(a_x*a_x + a_y*a_y + a_z*a_z)/9.8
//
//            if (accel_magnitude>2.5){
//                colorList.shuffle()
//                gameView.bgColor = colorList[0]
//                gameView.invalidate()
//            }

        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //val gameView = GameView(this)
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

        setContentView(R.layout.rolling_ball_game_activity)
        gameView = findViewById(R.id.gameView)

        quitButton = quit
        restartButton = restart

        restartButton.setOnClickListener {
            finish()
            overridePendingTransition(0,0)
            startActivity(Intent(this, RollingBallGameActivity::class.java))
            overridePendingTransition(0,0)
        }

        quitButton.setOnClickListener {
            finish()
        }

        mainHandler = Handler(Looper.getMainLooper())

    }

    override fun onResume() {
        super.onResume()
        sensorManager!!.registerListener(this, sensorManager!!.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL)
        mainHandler.post(updatePosition)
    }

    override fun onPause() {
        super.onPause()
        sensorManager!!.unregisterListener(this)
        mainHandler.removeCallbacks(updatePosition)
    }

}