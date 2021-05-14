package com.example.amazonbooks.ui

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.example.amazonbooks.R
import kotlin.math.cos
import kotlin.math.min
import kotlin.math.sin

private enum class DatabaseLimit(val label: Int) {
    ZERO(R.string.limit_zero),
    TWO(R.string.limit_two),
    FOUR(R.string.limit_four),
    SIX(R.string.limit_six),
    EIGHT(R.string.limit_eight),
    TEN(R.string.limit_ten);

    fun next() = when (this) {
        ZERO -> TWO
        TWO -> FOUR
        FOUR -> SIX
        SIX -> EIGHT
        EIGHT -> TEN
        TEN -> ZERO
    }
}

private const val RADIUS_OFFSET_LABEL = 40  // Offset for limit label
private const val RADIUS_OFFSET_INDICATOR = -60 // Offset for limit indicator

class LimitView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
        textAlign = Paint.Align.CENTER
        textSize = 80.0f
        typeface = Typeface.create("", Typeface.BOLD)
    }

    private var radius = 0.0f
    private var limit = DatabaseLimit.ZERO
    private val pointPosition: PointF = PointF(0.0f, 0.0f)

    init {
        isClickable = true
    }

    // Ensures that the radius is calculated dynamically
    override fun onSizeChanged(width: Int, height: Int, oldwidth: Int, oldheight: Int) {
        radius = (min(width, height) / 2.0 * 0.8).toFloat()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        // Setting view color background based on limit
        paint.color = when (limit) {
            DatabaseLimit.ZERO, DatabaseLimit.TWO, DatabaseLimit.FOUR -> Color.GREEN
            DatabaseLimit.SIX, DatabaseLimit.EIGHT -> Color.YELLOW
            DatabaseLimit.TEN -> Color.RED
        }

        // Drawing limit view first
        canvas.drawCircle((width / 2).toFloat(), (height / 2).toFloat(), radius, paint)

        // Drawing indicator circle
        val indicatorRadius = radius + RADIUS_OFFSET_INDICATOR
        pointPosition.computeXYForLimit(limit, indicatorRadius)
        paint.color = Color.BLACK
        canvas.drawCircle(pointPosition.x, pointPosition.y, radius / 12, paint)

        // Drawing labels
        val labelRadius = radius + RADIUS_OFFSET_LABEL
        DatabaseLimit
            .values()
            .forEach { limit ->
                val label = resources.getString(limit.label)
                pointPosition.computeXYForLimit(limit, labelRadius)
                canvas.drawText(
                    label,
                    pointPosition.x,
                    pointPosition.y,
                    paint
                )
            }
    }

    override fun performClick(): Boolean {
        super.performClick()
        // Rotate indicator to next position
        limit = limit.next()
        // Redraw the view
        invalidate()
        return true
    }

    /**
     * Determines the indicator's coordinates
     */
    private fun PointF.computeXYForLimit(pos: DatabaseLimit, radius: Float) {
        // Angles are in radians (PI*radians = 180 degrees)
        // Y axis is inverted in android, so positive angles are clockwise
        val angle = Math.PI + pos.ordinal * (Math.PI / 5)
        x = (radius * cos(angle)).toFloat() + width / 2
        y = (radius * sin(angle)).toFloat() + height / 2
    }
}
