package com.example.proj1_tablayout

import android.content.Context
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.rolling_ball_game_activity.*
import kotlin.math.floor
import kotlin.math.sqrt
import kotlin.random.Random

class RollingBallGameActivity: AppCompatActivity(), SensorEventListener{

    private var sensorManager: SensorManager? = null

    private val colorList = mutableListOf(Color.MAGENTA, Color.GREEN, Color.DKGRAY, Color.CYAN)

    private var a_x:Float = 0f
    private var a_y:Float = 0f
    private var a_z:Float = 0f

    lateinit var mainHandler: Handler
    lateinit var gameView: GameView

    private val updatePosition = object : Runnable {
        override fun run() {
            gameView.update(a_x, a_y)
            gameView.invalidate()
            mainHandler.postDelayed(this, 10)
        }
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event!!.sensor.type == Sensor.TYPE_ACCELEROMETER) {
            a_x = event.values[0]
            a_y = event.values[1]
            a_z = event.values[2]

            val accel_magnitude = sqrt(a_x*a_x + a_y*a_y + a_z*a_z)/9.8

            if (accel_magnitude>2.5){
                colorList.shuffle()
                gameView.bgColor = colorList[0]
                gameView.invalidate()
            }

        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }

//    private fun getAccelerometer(event: SensorEvent) {
//        // Movement
//        val xVal = event.values[0]
//        val yVal = event.values[1]
//        val zVal = event.values[2]
////        textView2.text = "X Value: ".plus(xVal.toString())
////        textView3.text = "Y Value: ".plus(yVal.toString())
////        textView4.text = "Z Value: ".plus(zVal.toString())
////
////        val accelerationSquareRoot = (xVal * xVal + yVal * yVal + zVal * zVal) / (SensorManager.GRAVITY_EARTH * SensorManager.GRAVITY_EARTH)
////
////        if (accelerationSquareRoot >= 3) {
////            Toast.makeText(this, "Device was shuffled", Toast.LENGTH_SHORT).show()
////            if (color) {
////                rollingballframe.setBackgroundColor(0x000000)
////            } else {
////                rollingballframe.setBackgroundColor(Color.YELLOW)
////            }
////            color = !color
////        }
//    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //val gameView = GameView(this)
        sensorManager = getSystemService(Context.SENSOR_SERVICE) as SensorManager

        gameView = GameView(this)

        setContentView(gameView)

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