package com.example.proj1_tablayout.fragments

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.fragment.app.Fragment
import com.example.proj1_tablayout.GameView
import com.example.proj1_tablayout.R
import com.example.proj1_tablayout.RollingBallGameActivity
import kotlinx.android.synthetic.main.gamefragment.*
import kotlin.math.sqrt
import kotlin.random.Random


class TBDFragmentTab : Fragment(), SensorEventListener {
    var name = ""

    private val colorList = mutableListOf(Color.MAGENTA, Color.GREEN, Color.DKGRAY, Color.CYAN)
    private var sensorManager: SensorManager? = null

    private var a_x:Float = 0f
    private var a_y:Float = 0f
    private var a_z:Float = 0f

    lateinit var mainHandler: Handler
    lateinit var gameView: GameView

    private val updatePosition = object : Runnable {
        override fun run() {

            gameView.updateUserBall(a_x, a_y)
            gameView.invalidate()
            mainHandler.postDelayed(this, 10)

        }
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event!!.sensor.type == Sensor.TYPE_ACCELEROMETER) {
            a_x = event.values[0]
            a_y = event.values[1]
            a_z = event.values[2]

            val accel_magnitude = sqrt(a_x*a_x + a_y*a_y + a_z*a_z) /9.8

            if (accel_magnitude>2.5){
                colorList.shuffle()
                gameView.bgColor = colorList[0]
                gameView.invalidate()
            }

        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        sensorManager = context!!.getSystemService(Context.SENSOR_SERVICE) as SensorManager

        return inflater.inflate(R.layout.gamefragment,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        gameView = ballWindow

        ballWindow.drawText = false

        ballWindow.viewTreeObserver.addOnGlobalLayoutListener {
            val width = ballWindow.measuredWidth
            val height = ballWindow.measuredHeight
            ballWindow.screenMaxX = width.toFloat()
            ballWindow.screenMaxY = height.toFloat()+50f
        }

        //handling aniamtion on button click

        gameStartBtn.setOnClickListener {
            startActivity(Intent(context, RollingBallGameActivity::class.java))
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