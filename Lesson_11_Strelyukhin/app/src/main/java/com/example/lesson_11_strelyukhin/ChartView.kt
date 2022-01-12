package com.example.lesson_11_strelyukhin

import android.animation.ValueAnimator
import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.util.TypedValue
import android.view.View
import androidx.core.content.res.ResourcesCompat

class ChartView : View {
    constructor(context: Context) : super(context) {
        init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context, attrs)
    }

    private var date: List<String> = listOf()
    private var values: List<Int> = listOf()
    private var valuesMax: Int = 1

    //private var space = width - paddingStart - paddingEnd
    private var size = 1

    private var chartColor = Color.YELLOW
    private var dateColor = Color.GRAY

    private val valueTextBound = Rect()
    private val dateTextBound = Rect()

    val customTypeface = ResourcesCompat.getFont(context, R.font.roboto)

    private val chartPaint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = chartColor
            style = Paint.Style.FILL
        }
    }

    private val valuePaint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = chartColor
            val spSize = 12
            textSize = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP,
                spSize.toFloat(), resources.displayMetrics
            )
            textAlign = Paint.Align.CENTER
            typeface = customTypeface
        }
    }

    private val datePaint by lazy {
        Paint(Paint.ANTI_ALIAS_FLAG).apply {
            color = dateColor
            val spSize = 12
            textSize = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_SP,
                spSize.toFloat(), resources.displayMetrics
            )
            textAlign = Paint.Align.CENTER
            typeface = customTypeface
        }
    }

    private val chartWidth = context.resources.displayMetrics.density * 4
    private val margin = context.resources.displayMetrics.density * 16
    private val minChartHeight = context.resources.displayMetrics.density * 4

    private var animation: ValueAnimator? = null
    private var valueAnimator = 0F

    private fun init(context: Context, attrs: AttributeSet?) {
        val a = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.ChartView,
            0,
            0
        )
        try {
            setColor(a)
        } finally {
            a.recycle()
        }
    }

    private fun setColor(a: TypedArray) {
        chartColor = a.getColor(R.styleable.ChartView_chartColor, Color.YELLOW)
        dateColor = a.getColor(R.styleable.ChartView_dateColor, Color.GRAY)
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val w = this.measuredWidth
        val h = this.measuredHeight
        setMeasuredDimension(w, h)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        var x = paddingStart.toFloat()
        var y = paddingTop.toFloat()
        var center = paddingStart.toFloat()
        for (i in 0 until size) {
            valuePaint.getTextBounds(values[i].toString(), 0, values[i].toString().length, valueTextBound)
            datePaint.getTextBounds(date[i], 0, date[i].length, dateTextBound)
            val marginChart = margin + dateTextBound.height()
            center += (width - paddingStart - paddingEnd) / (size + 1)
            var chartHeight =
                ((height - marginChart - paddingTop - paddingBottom) * valueAnimator * values[i] / valuesMax) - 2 * valueTextBound.height()
            if (chartHeight < minChartHeight) {
                chartHeight = minChartHeight
            }
            canvas.drawRoundRect(
                center - chartWidth / 2,
                height - chartHeight - paddingBottom - marginChart,
                center + chartWidth / 2,
                height.toFloat() - marginChart - paddingBottom,
                context.resources.displayMetrics.density * 2,
                context.resources.displayMetrics.density * 2,
                chartPaint
            )
            canvas.drawText(
                values[i].toString(),
                center,
                height - paddingBottom - chartHeight - valueTextBound.height() - marginChart,
                valuePaint
            )
            canvas.drawText(date[i], center, height.toFloat() - paddingBottom, datePaint)
        }

    }

    fun toggleMyAnimation() {
        if (animation == null) {
            animation = ValueAnimator.ofFloat(0F, 1F).apply {
                duration = 1000
                addUpdateListener { animation ->
                    valueAnimator = animation.animatedValue as Float
                    invalidate()
                }
                start()
            }
            animation?.addUpdateListener {
                if (animation?.animatedValue == 1F) {
                    animation = null
                }
            }
        }
        return
    }

    fun setData(date: List<String>, values: List<Int>) {
        this.date = date
        this.values = values
        valuesMax = values.maxOrNull() ?: 1
        size = if (date.size <= 9) {
            date.size
        } else {
            9
        }
        //space = (width - paddingStart - paddingEnd) / (size + 1)
    }

}