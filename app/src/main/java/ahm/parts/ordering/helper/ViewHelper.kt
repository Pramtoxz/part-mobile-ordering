package ahm.parts.ordering.helper

import android.app.Activity
import android.content.Context
import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.*
import android.graphics.drawable.shapes.RoundRectShape
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.graphics.drawable.DrawableCompat
import com.google.android.material.tabs.TabLayout
import java.util.*

open class ViewHelper {

    companion object {
        fun showKeyboard(v: View, activity: Context) {
            val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(v, 0)
        }

        fun showKeyboard(v: View) {
            showKeyboard(v, v.context)
        }

        fun hideKeyboard(view: View) {
            val inputManager =
                view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputManager.hideSoftInputFromWindow(view.windowToken, 0)
        }

        fun hideKeyboard(activity: Activity) {
            val imm = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            var view = activity.currentFocus
            if (view == null) {
                view = View(activity)
            }
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }

        @ColorInt
        fun generateTextColor(background: Int): Int {
            return getContrastColor(background)
        }

        @ColorInt
        private fun getContrastColor(@ColorInt color: Int): Int {
            val a =
                1 - (0.299 * Color.red(color) + 0.587 * Color.green(color) + 0.114 * Color.blue(
                    color
                )) / 255
            return if (a < 0.5) Color.BLACK else Color.WHITE
        }

    }


}