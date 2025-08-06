package ahm.parts.ordering.ui.widget.line

import ahm.parts.ordering.R
import android.annotation.TargetApi
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PathDashPathEffect
import android.os.Build.VERSION
import android.os.Build.VERSION_CODES
import android.util.AttributeSet
import android.view.View


class DottedLine : View {

    private var mPaint: Paint? = null

    constructor(context: Context) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        init()
    }

    @TargetApi(VERSION_CODES.LOLLIPOP)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(
        context,
        attrs,
        defStyleAttr,
        defStyleRes
    ) {
        init()
    }

    private fun init() {
        val res = resources
        mPaint = Paint()

        mPaint!!.color = res.getColor(R.color.divider)
        val size = res.getDimensionPixelSize(R.dimen.default_background_stroke_width)
        val gap = res.getDimensionPixelSize(R.dimen.gap_dotted)
        mPaint!!.style = Paint.Style.FILL

        // To get actually round dots, we define a circle...
        val path = Path()
        path.addCircle(0F, 0F, size.toFloat(), Path.Direction.CW)
        // ...and use the path with the circle as our path effect
        mPaint!!.pathEffect = PathDashPathEffect(
            path,
            gap.toFloat(),
            0f,
            PathDashPathEffect.Style.ROTATE
        )

        // If we don't render in software mode, the dotted line becomes a solid line.
        if (VERSION.SDK_INT >= VERSION_CODES.HONEYCOMB) {
            setLayerType(View.LAYER_TYPE_SOFTWARE, null)
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        mPaint?.let {
            canvas.drawLine((width / 2).toFloat(), 0F, (width / 2).toFloat(), height.toFloat(),
                it
            )
        }
    }
}