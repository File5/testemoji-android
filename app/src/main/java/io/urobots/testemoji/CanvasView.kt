package io.urobots.testemoji

import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View

/**
 * TODO: document your custom view class.
 */
class CanvasView : View {

    private var _exampleString: String? = null // TODO: use a default from R.string...
    private var _exampleColor: Int = Color.RED // TODO: use a default from R.color...
    private var _exampleDimension: Float = 0f // TODO: use a default from R.dimen...

    private lateinit var textPaint: TextPaint
    private var textWidth: Float = 0f
    private var textHeight: Float = 0f

    private val bitmap = Bitmap.createBitmap(640, 480, Bitmap.Config.ARGB_8888)

    /**
     * The text to draw
     */
    var exampleString: String?
        get() = _exampleString
        set(value) {
            _exampleString = value
            invalidateTextPaintAndMeasurements()
        }

    /**
     * The font color
     */
    var exampleColor: Int
        get() = _exampleColor
        set(value) {
            _exampleColor = value
            invalidateTextPaintAndMeasurements()
        }

    /**
     * In the example view, this dimension is the font size.
     */
    var exampleDimension: Float
        get() = _exampleDimension
        set(value) {
            _exampleDimension = value
            invalidateTextPaintAndMeasurements()
        }

    /**
     * In the example view, this drawable is drawn above the text.
     */
    var exampleDrawable: Drawable? = null

    constructor(context: Context) : super(context) {
        init(null, 0)
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init(attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(
        context,
        attrs,
        defStyle
    ) {
        init(attrs, defStyle)
    }

    private fun init(attrs: AttributeSet?, defStyle: Int) {
        // Load attributes
        val a = context.obtainStyledAttributes(
            attrs, R.styleable.CanvasView, defStyle, 0
        )

        _exampleString = a.getString(
            R.styleable.CanvasView_exampleString
        )
        _exampleColor = a.getColor(
            R.styleable.CanvasView_exampleColor,
            exampleColor
        )
        // Use getDimensionPixelSize or getDimensionPixelOffset when dealing with
        // values that should fall on pixel boundaries.
        _exampleDimension = a.getDimension(
            R.styleable.CanvasView_exampleDimension,
            exampleDimension
        )

        if (a.hasValue(R.styleable.CanvasView_exampleDrawable)) {
            exampleDrawable = a.getDrawable(
                R.styleable.CanvasView_exampleDrawable
            )
            exampleDrawable?.callback = this
        }

        a.recycle()

        // Set up a default TextPaint object
        textPaint = TextPaint().apply {
            flags = Paint.ANTI_ALIAS_FLAG
            textAlign = Paint.Align.LEFT
            color = Color.BLACK
            textSize = 64.0f
        }

        // Update TextPaint and text measurements from attributes
        //invalidateTextPaintAndMeasurements()
        Canvas(bitmap).apply {
            drawPaint(Paint().apply { color = Color.WHITE })
            drawText("Bitmap: ☺️\uD83C\uDFF4\uD83C\uDDF7\uD83C\uDDFA\uD83C\uDDE9\uD83C\uDDEA", 0.0f, 60.0f, textPaint)
        }
    }

    private fun invalidateTextPaintAndMeasurements() {
        textPaint.let {
            it.textSize = exampleDimension
            it.color = exampleColor
            textWidth = it.measureText(exampleString)
            textHeight = it.fontMetrics.bottom
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.drawText("Hello, Canvas!", 48.0f, 60.0f, textPaint)
        canvas.drawText("Emojis: ☺️\uD83C\uDFF4\uD83C\uDDF7\uD83C\uDDFA\uD83C\uDDE9\uD83C\uDDEA", 48.0f, 2 * 60.0f, textPaint)
        canvas.drawBitmap(bitmap, 48.0f, 4 * 60.0f, null)
    }
}