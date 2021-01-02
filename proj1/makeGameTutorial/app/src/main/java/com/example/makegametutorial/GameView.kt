package com.example.makegametutorial

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.MotionEvent
import android.view.View

class GameView(context:Context): View(context) {

    val paint: Paint
    var posX:Float
    var posY:Float
    init {
        paint = Paint()
        paint.isFilterBitmap = true
        paint.isAntiAlias = true
        paint.color = Color.YELLOW
        posX = 100f
        posY = 100f
    }

    override fun draw(canvas: Canvas?) {
        super.draw(canvas)

        canvas?.drawColor(Color.RED)
        canvas?.drawCircle(posX, posY, 50f, paint)

    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        posX = event!!.x
        posY = event!!.y
        invalidate()
        return true
    }
}