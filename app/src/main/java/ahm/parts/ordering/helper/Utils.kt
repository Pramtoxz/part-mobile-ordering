package ahm.parts.ordering.helper

import android.content.Context

open class Utils {

    fun dpToInt(context: Context, dp: Int): Int {
        val density = context.resources.displayMetrics.density
        val paddingPixel = (dp * density)

        return paddingPixel.toInt()
    }

    companion object{

        fun dpToInt(context: Context, dp: Int): Int {
            val density = context.resources.displayMetrics.density
            val paddingPixel = (dp * density)

            return paddingPixel.toInt()
        }
    }
}