package com.example.proj1_tablayout

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.view.MotionEvent
import android.view.SurfaceView
import android.view.View
import kotlin.math.sqrt

class GameView(context: Context): View(context) {
    val paint: Paint
    var posX:Float
    var posY:Float
    var v_x: Float = 10f
    var v_y: Float = 10f
    var a_x:Float = 0f
    var a_y: Float = 0f
    var friction_coef = 0.05f*9.8f
    var bgColor = Color.CYAN


    init {
        paint = Paint()
        paint.isFilterBitmap = true
        paint.isAntiAlias = true
        paint.color = Color.YELLOW
        posX = 200f
        posY = 200f
    }

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)
        canvas?.drawColor(bgColor)
        canvas?.drawCircle(posX, posY, 50f, paint)

        //canvas.drawText()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        posX = event!!.x
        posY = event!!.y
//        v_x = -v_x
//        v_y = -v_y
        invalidate()
        return true
    }

    fun update(aX: Float, aY: Float){

        a_x = aX
        a_y = aY

        val velocity_magnitude = sqrt(v_x*v_x + v_y*v_y)

        v_x -= a_x + friction_coef*v_x/velocity_magnitude
        v_y += a_y - friction_coef*v_y/velocity_magnitude

        if (posX < 50f  || posX > 1000f){
            v_x = -0.8f*v_x
            if (posX < 50f){
                posX = 50f
            }
            else {
                posX = 1000f
            }
        }

        if (posY < 50f || posY >1000f){
            v_y = -0.8f*v_y

            if (posY < 50f){
                posY = 50f
            }
            else {
                posY = 1000f
            }
        }

        posX += 0.05f*v_x
        posY += 0.05f*v_y

    }



}