package com.example.virusgame.customview

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.animation.AnimationUtils
import androidx.appcompat.widget.AppCompatTextView
import com.example.virusgame.R

class OutlineTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) :
    AppCompatTextView(context, attrs, defStyleAttr) {

    private var stroke = false
    private var strokeWidth = 0.0f
    private var strokeColor = 0
    private var valueAnimator: ValueAnimator? = null

    init {
        val typedArray = context.obtainStyledAttributes(attrs!!, R.styleable.OutlineTextView)

        stroke = typedArray.getBoolean(R.styleable.OutlineTextView_textStroke, false)
        strokeWidth = typedArray.getDimension(R.styleable.OutlineTextView_textStrokeWidth, 0f)
        strokeColor = typedArray.getColor(R.styleable.OutlineTextView_textStrokeColor, 0)

        typedArray.recycle()
    }

    override fun onDraw(canvas: Canvas?) {
        if (stroke) {
            val states = textColors
            paint.style = Paint.Style.STROKE
            paint.strokeWidth = strokeWidth
            setTextColor(strokeColor)
            super.onDraw(canvas)
            paint.style = Paint.Style.FILL
            setTextColor(states)
        }

        super.onDraw(canvas)
    }

    fun startAnimation() {
        valueAnimator =
            ValueAnimator.ofObject(ArgbEvaluator(), Color.rgb(255, 255, 255), Color.rgb(255, 0, 0))
        valueAnimator!!.duration = 500
        valueAnimator!!.addUpdateListener(updateListener)
        valueAnimator!!.repeatCount = ValueAnimator.INFINITE
        valueAnimator!!.start()
    }

    fun stopAnimation() {
        Log.e("stip", "animatino")
//        valueAnimator!!.pause()
        valueAnimator!!.removeUpdateListener(updateListener)
        valueAnimator!!.end()
        valueAnimator!!.cancel()
        valueAnimator = null
    }

    private val updateListener = ValueAnimator.AnimatorUpdateListener {
        setTextColor(it.animatedValue as Int)
    }
}