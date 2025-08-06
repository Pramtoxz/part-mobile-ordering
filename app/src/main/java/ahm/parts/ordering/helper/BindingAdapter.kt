package ahm.parts.ordering.helper

import android.os.Build
import android.text.Html
import android.util.Log
import android.widget.TextView
import androidx.databinding.BindingAdapter

open class BindingAdapter {

    companion object {

        @BindingAdapter("tagHtml")
        @JvmStatic
        fun setTextTagHtml(textView: TextView, tagHtml: String?) {

            Log.e("binding", tagHtml)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                textView.text = Html.fromHtml(tagHtml, Html.FROM_HTML_MODE_COMPACT)
            } else {
                textView.text = Html.fromHtml(tagHtml)
            }

        }
    }
}