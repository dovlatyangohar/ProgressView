package com.example.progressview


import android.content.Context

import android.graphics.Canvas

import android.graphics.Color

import android.graphics.Paint

import android.graphics.Path

import android.util.AttributeSet

import android.view.MotionEvent

import android.view.View
import android.graphics.Bitmap
import android.opengl.ETC1.getHeight
import android.opengl.ETC1.getWidth
import android.graphics.RectF


class DrawingView @JvmOverloads constructor(

    context: Context,

    attrs: AttributeSet? = null,

    defStyleAttr: Int = 0

) : View(context, attrs, defStyleAttr) {


    private val paint = Paint().apply {

        isAntiAlias = true

        color = DEF_PROGRESS_COLOR

        style = Paint.Style.STROKE

        strokeWidth = DEF_STROKE_WIDTH


    }

    private val path = Path()

    init {

        context.takeIf { attrs != null }?.theme?.obtainStyledAttributes(

            attrs,

            R.styleable.DrawingView,

            defStyleAttr,

            0

        )?.apply {

            try {

                paint.strokeWidth = getDimension(R.styleable.DrawingView_strokeWidth, DEF_STROKE_WIDTH)

                paint.color = getColor(R.styleable.DrawingView_strokeColor, DEF_PROGRESS_COLOR)

            } finally {

                recycle()

            }

        }

    }

    fun reset() {

        path.reset()

        invalidate()

    }

    override fun onDraw(canvas: Canvas?) {

        super.onDraw(canvas)
 

        canvas?.drawPath(path, paint)

    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {

        when (event?.action) {

            MotionEvent.ACTION_DOWN -> {

                path.addRoundRect(
                    height.toFloat(),
                    width.toFloat(),
                    height.toFloat(),
                    width.toFloat(),

                    CORNER_RADIUS,
                    CORNER_RADIUS,
                   Path.Direction.CCW
                )

                return true

            }

            MotionEvent.ACTION_MOVE -> {

                path.lineTo(event.x, event.y)

                invalidate()

            }

        }

        return super.onTouchEvent(event)

    }

    companion object {

        private const val DEF_STROKE_WIDTH = 60f
        private const val DEF_PROGRESS_COLOR = Color.GREEN

         private const val CORNER_RADIUS = 5f


    }



}

