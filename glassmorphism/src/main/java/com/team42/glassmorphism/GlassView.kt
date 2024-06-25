package com.team42.glassmorphism

import android.content.Context
import android.graphics.BlurMaskFilter
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.drawable.GradientDrawable
import android.os.Parcelable
import android.util.AttributeSet
import android.widget.FrameLayout
import androidx.appcompat.content.res.AppCompatResources
import com.team42.glassmorphism.utill_classes.SavedState

class GlassView : FrameLayout {

    companion object {
        const val DEFAULT_CORNER_RADIUS = 25f
        const val DEFAULT_PADDING = 24f
        const val DEFAULT_SHADOW_OPACITY = 0.19f
        const val DEFAULT_SURFACE_OPACITY = 0.15f
        const val DEFAULT_BORDER_OPACITY = 0.28f
        const val DEFAULT_BORDER_WIDTH = 3f
    }

    private var cornerRadius: Float = DEFAULT_CORNER_RADIUS
        set(value) {
            field = value
            invalidate()
        }
    private var padding: Float = DEFAULT_PADDING
        set(value) {
            field = value
            invalidate()
        }
    private var shadowOpacity: Float = DEFAULT_SHADOW_OPACITY
        set(value) {
            field = value
            invalidate()
        }
    private var surfaceOpacity: Float = DEFAULT_SURFACE_OPACITY
        set(value) {
            field = value
            invalidate()
        }
    private var borderOpacity: Float = DEFAULT_BORDER_OPACITY
        set(value) {
            field = value
            invalidate()
        }
    private var borderWidth: Float = DEFAULT_BORDER_WIDTH
        set(value) {
            field = value
            invalidate()
        }

    private val surfacePaint = Paint().apply {
        style = Paint.Style.FILL
        color = Color.WHITE
    }

    private val borderPaint = Paint().apply {
        style = Paint.Style.STROKE
        color = Color.WHITE
    }

    private val shadowPaint = Paint().apply {
        style = Paint.Style.FILL
        color = Color.BLACK
        xfermode = PorterDuffXfermode(PorterDuff.Mode.DST_OVER)
    }

    constructor(context: Context) : super(context) {
        setupStyleable(context, null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        setupStyleable(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        setupStyleable(context, attrs)
    }

    private fun setupStyleable(context: Context, attrs: AttributeSet?) {

        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.GlassView)
        this.cornerRadius = typedArray.getDimension(
            R.styleable.GlassView_glassView_cornerRadius,
            DEFAULT_CORNER_RADIUS
        )
        this.padding =
            typedArray.getDimension(R.styleable.GlassView_glassView_elevation, DEFAULT_PADDING)
        this.surfaceOpacity = typedArray.getFloat(
            R.styleable.GlassView_glassView_surfaceOpacity,
            DEFAULT_SURFACE_OPACITY
        )
        this.borderOpacity =
            typedArray.getFloat(R.styleable.GlassView_glassView_borderWidth, DEFAULT_BORDER_OPACITY)
        this.shadowOpacity = typedArray.getFloat(
            R.styleable.GlassView_glassView_shadowOpacity,
            DEFAULT_SHADOW_OPACITY
        )
        this.borderWidth = typedArray.getDimension(
            R.styleable.GlassView_glassView_borderWidth,
            DEFAULT_BORDER_WIDTH
        )
        typedArray.recycle()

        elevation = this.padding
        surfacePaint.alpha = (surfaceOpacity * 255).toInt()
        borderPaint.strokeWidth = borderWidth
        borderPaint.alpha = (borderOpacity * 255).toInt()
        shadowPaint.alpha = (shadowOpacity * 255).toInt()
        shadowPaint.maskFilter = BlurMaskFilter(
            padding,
            BlurMaskFilter.Blur.OUTER
        )

        background = getTransparentBackground()


    }

    private fun getTransparentBackground(): GradientDrawable {
        val transparentBackground = GradientDrawable()
        transparentBackground.setShape(GradientDrawable.RECTANGLE)
        transparentBackground.color =
            AppCompatResources.getColorStateList(context, R.color.transparent)
        return transparentBackground
    }

    override fun onSaveInstanceState(): Parcelable? {
        val superState: Parcelable? = super.onSaveInstanceState()
        superState?.let {
            val state = SavedState(superState)
            state.cornerRadius = this.cornerRadius
            state.padding = this.padding
            state.surfaceOpacity = this.surfaceOpacity
            state.borderOpacity = this.borderOpacity
            state.shadowOpacity = this.shadowOpacity
            state.borderWidth = this.borderWidth
            return state
        } ?: run {
            return superState
        }
    }

    override fun onRestoreInstanceState(state: Parcelable?) {
        when (state) {
            is SavedState -> {
                super.onRestoreInstanceState(state.superState)
                this.cornerRadius = state.cornerRadius
                this.padding = state.padding
                this.surfaceOpacity = state.surfaceOpacity
                this.borderOpacity = state.borderOpacity
                this.shadowOpacity = state.shadowOpacity
                this.borderWidth = state.borderWidth
                elevation = this.padding
            }

            else -> {
                super.onRestoreInstanceState(state)
            }
        }
    }

    override fun onDraw(canvas: Canvas) {
        with(canvas) {

            val left: Float = (borderWidth / 2f) + padding
            val top: Float = (borderWidth / 2f) + padding
            val right: Float = (width - (borderWidth / 2f)) - padding
            val bottom: Float = (height - (borderWidth / 2f)) - padding


            // Draw shadow
            drawRoundRect(
                left,
                top,
                right,
                bottom,
                cornerRadius,
                cornerRadius,
                shadowPaint
            )

            // Draw surface
            drawRoundRect(
                left,
                top,
                right,
                bottom,
                cornerRadius,
                cornerRadius,
                surfacePaint
            )

            // Draw border
            drawRoundRect(
                left,
                top,
                right,
                bottom,
                cornerRadius,
                cornerRadius,
                borderPaint
            )
        }
    }

}