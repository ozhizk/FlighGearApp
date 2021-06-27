package com.example.flighgearapp

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PointF
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View


class Joystick @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : View(context, attrs, defStyleAttr) {

    // paint to the main circle in the joystick
    private val paint = Paint().apply {
        style = Paint.Style.FILL
        color = Color.GRAY
        isAntiAlias = true
    }

    // paint to the big circle in the joystick
    private val paintBase = Paint().apply {
        style = Paint.Style.FILL
        color = Color.BLACK
        isAntiAlias = true
    }

    // paint to the background circle in the joystick
    private val paintRing = Paint().apply {
        style = Paint.Style.FILL
        color = Color.WHITE
        isAntiAlias = true
    }

    // radius and center to the main circle in the joystick
    private var radius: Float = 0f
    private var center: PointF = PointF()

    // radius and center to the big circle in the joystick
    private var baseRadius: Float = 0f
    private var baseCenter: PointF = PointF()

    // radius and center to the background circle in the joystick
    private var ringRadius: Float = 0f
    private var ringCenter: PointF = PointF()


    // function that say what the app have to do in changing the place of the joystick
    lateinit var onChange:(Float, Float)->Unit


    // put the sizes of the circles
    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        center = PointF(width.toFloat()/2, height.toFloat()/2)
        radius = width.toFloat()/6
        baseCenter = PointF(width.toFloat()/2, height.toFloat()/2)
        baseRadius = width.toFloat()/3
        ringCenter = PointF(width.toFloat()/2, height.toFloat()/2)
        ringRadius = width.toFloat()/5
    }

    // draw the circles
    override fun onDraw(canvas: Canvas) {
        var rinngCirc = canvas.drawCircle(baseCenter.x, baseCenter.y, baseRadius, paintBase)
        var baseCirc = canvas.drawCircle(ringCenter.x, ringCenter.y, ringRadius, paintRing)
        var circ = canvas.drawCircle(center.x, center.y, radius, paint)

    }


    // what happen in touch
    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        // if don't happen anything
        if (event == null) {
            return true
        }
        // when the user touch the screen
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> touchMove(event.x, event.y)
            MotionEvent.ACTION_MOVE -> touchMove(event.x, event.y)
            MotionEvent.ACTION_UP -> release()

        }
        // make the change
        val xVal = (center.x - width.toFloat()/2)/(baseRadius)
        val yVal = (height.toFloat()/2 - center.y)/(baseRadius)
        onChange(xVal, yVal)
        return true
    }

    // change the circle on the screen
    private fun touchMove(x: Float, y: Float){
        when {
            // limit the move of the circle only in the screen
            x < width.toFloat()/2 - baseRadius -> {
                center.x = width.toFloat()/2 - baseRadius
            }
            x > width.toFloat()/2 + baseRadius -> {
                center.x = width.toFloat()/2 + baseRadius
            }
            else -> {
                center.x = x
            }
        }

        when {
            y < height.toFloat()/2 - baseRadius -> {
                center.y = height.toFloat()/2 - baseRadius
            }
            y > height.toFloat()/2 + baseRadius -> {
                center.y = height.toFloat()/2 + baseRadius
            }
            else -> {
                center.y = y
            }
        }
        invalidate()
    }

    // if the user release the screen back the circle to the center
    private fun release(){
        center = PointF(width.toFloat()/2, height.toFloat()/2)
        invalidate()
    }

}