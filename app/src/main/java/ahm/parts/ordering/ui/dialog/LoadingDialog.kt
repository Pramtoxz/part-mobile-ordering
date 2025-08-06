package ahm.parts.ordering.ui.dialog

import ahm.parts.ordering.R
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.StringRes
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.dialog_bottom_loading.*

/**
 * Created by nuryazid on 4/20/18.
 */

class LoadingDialog(context: Context) {

    private var dialog: BottomSheetDialog? = null

    init {
        val view = LayoutInflater.from(context).inflate(
            R.layout.dialog_bottom_loading,
            null
        )
        dialog = BottomSheetDialog(context, R.style.BottomSheetDialogTheme)
        dialog?.setContentView(view)

        val vp = view.parent as View
        vp.setBackgroundColor(Color.TRANSPARENT)
    }

    fun setMessage(@StringRes message: Int): LoadingDialog {
        return setMessage(string(message))
    }

    fun setMessage(message: CharSequence): LoadingDialog {
        dialog?.tvMessage?.text = message
        dialog?.tvMessage?.visibility = View.VISIBLE
        dialog?.tvTitle?.visibility = View.GONE
        return this
    }

    fun setResponse(@StringRes message: Int): LoadingDialog {
        return setResponse(string(message))
    }

    fun setResponse(message: String): LoadingDialog {
        dialog?.setCancelable(true)
        dialog?.tvMessage?.text = message

        dialog?.tvTitle?.visibility = View.GONE
        dialog?.tvMessage?.visibility = View.VISIBLE
        dialog?.progressWheel?.visibility = View.GONE

        if (dialog?.isShowing == false) {
            dialog?.show()
        }
        return this
    }

    fun setDismissListener(dismiss: () -> Unit): LoadingDialog {
        dialog?.setOnDismissListener { dismiss() }
        return this
    }

    private fun string(@StringRes res: Int): String {
        return dialog?.context?.getString(res) ?: "-"
    }

    fun setCancelable(value: Boolean): LoadingDialog {
        dialog?.setCancelable(value)
        return this
    }

    fun show(@StringRes message: Int): BottomSheetDialog {
        if (dialog?.isShowing == true) return dialog as BottomSheetDialog
        dialog?.progressWheel?.startAnimation()
        dialog?.progressWheel?.visibility = View.VISIBLE
        dialog?.tvMessage?.text = string(message)
        dialog?.tvMessage?.visibility = View.VISIBLE
        dialog?.tvTitle?.visibility = View.GONE
        dialog?.setCancelable(false)
        dialog?.show()
        return dialog as BottomSheetDialog
    }

    fun dismiss(): BottomSheetDialog {
        if (dialog?.isShowing == true) {
            dialog?.dismiss()
        }

        return dialog as BottomSheetDialog
    }
}
