package com.example.proj1_tablayout

import android.content.Context
import android.graphics.*
import android.os.Build
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat
import kotlinx.android.synthetic.main.rolling_ball_game_activity.view.*
import kotlin.math.sqrt
import kotlin.random.Random.Default.nextFloat

class GameView@JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : View(context, attrs, defStyle){
    val paint: Paint = Paint()
    var posX:Float
    var posY:Float
    var v_x: Float = 10f
    var v_y: Float = 10f
    var a_x:Float = 0f
    var a_y: Float = 0f
    var friction_coef = 0.05f*9.8f
    var bgColor = Color.CYAN
    var screenMaxX = 1080f
    var screenMaxY = 1920f

    var goodbulletPaint:Paint = Paint()
    var badbulletPaint: Paint = Paint()
    var life = 3
    var score = 0

    var bulletList = mutableListOf<Bullet>()
    //@RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    //val airplane = VectorDrawableCompat.create(getContext().getResources(), R.drawable.airplane, null).apply { setBounds() }

//    val textPaint:Paint = Paint().apply{
//        isFilterBitmap = true
//        isAntiAlias = true
//        color = Color.WHITE
//        textSize = 80f
//    }


    init {
        paint.isFilterBitmap = true
        paint.isAntiAlias = true
        paint.color = Color.YELLOW

        goodbulletPaint.isFilterBitmap = true
        goodbulletPaint.isAntiAlias = true
        goodbulletPaint.color = Color.WHITE

        badbulletPaint.isFilterBitmap = true
        badbulletPaint.isAntiAlias = true
        badbulletPaint.color = Color.BLACK

        posX = 200f
        posY = 200f
    }


    override fun draw(canvas: Canvas?) {
        super.draw(canvas)
        //canvas?.drawColor(bgColor)
        canvas?.drawCircle(posX, posY, 50f, paint)


        for (bullet in bulletList){
            canvas?.drawCircle(bullet.posX, bullet.posY, 10f, if (bullet.good) goodbulletPaint else badbulletPaint)
            bullet.posY += 2*bullet.velocity
            if (sqrt((bullet.posX-posX)*(bullet.posX-posX)+(bullet.posY-posY)*(bullet.posY-posY))<60f){
                if (bullet.good)
                    score += 1
                else {
                    life -= 1
                }
            }
        }

        bulletList = bulletList.filter{ bullet ->
            sqrt((bullet.posX-posX)*(bullet.posX-posX)+(bullet.posY-posY)*(bullet.posY-posY))>60f
                    && bullet.posY<1920} as MutableList<Bullet>

    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        posX = event!!.x
        posY = event!!.y
//        v_x = -v_x
//        v_y = -v_y
        invalidate()
        return true
    }

    fun updateUserBall(aX: Float, aY: Float){

        a_x = aX
        a_y = aY

        val velocity_magnitude = sqrt(v_x*v_x + v_y*v_y)

        v_x -= a_x + friction_coef*v_x/velocity_magnitude
        v_y += a_y - friction_coef*v_y/velocity_magnitude

        if (posX < 50f  || posX > screenMaxX-50f){
            v_x = -0.8f*v_x
            if (posX < 50f){
                posX = 50f
            }
            else {
                posX = screenMaxX-50f
            }
        }

        if (posY < 50f || posY > screenMaxY-100f){
            v_y = -0.8f*v_y

            if (posY < 50f){
                posY = 50f
            }
            else {
                posY = screenMaxY-100f
            }
        }


        posX += 0.05f*v_x
        posY += 0.05f*v_y

    }

    fun loadRandomBullet(n : Int){
        for (i in 1..n){
            val randX = kotlin.math.min(nextFloat() * 1080f + 30f,1050f)
            bulletList.add(Bullet(randX))
        }
    }

}

class Bullet(initX:Float){
    val good = nextFloat()>0.5
    var posY:Float = 0f
    var posX:Float = -9999f
    var velocity: Float = -100f

    init{
        posX = initX
        velocity = nextFloat()
    }
}