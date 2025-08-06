package ahm.parts.ordering.ui.dialog

import ahm.parts.ordering.R
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.StringRes
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.dialog_bottom_alert.*

/**
 * Created by nuryazid on 4/20/18.
 */

class AlertDialog(context: Context) {

    private var dialog: BottomSheetDialog? = null

    init {
        val view = LayoutInflater.from(context).inflate(
            R.layout.dialog_bottom_alert,
            null
        )
        dialog = BottomSheetDialog(context, R.style.BottomSheetDialogTheme)
        dialog?.setContentView(view)

        dialog?.btnCancel?.setOnClickListener { dialog?.dismiss() }

        val vp = view.parent as View
        vp.setBackgroundColor(Color.TRANSPARENT)
    }

    fun onPositiveListener(onClick: (view: View, dialog: AlertDialog) -> Unit) : AlertDialog {
        dialog?.btnOk?.setOnClickListener { onClick(it, this) }
        return this
    }

    fun setMessage(@StringRes message: Int): AlertDialog {
        return setMessage(string(message))
    }

    fun setMessage(message: CharSequence): AlertDialog {
        dialog?.tvMessage?.text = message
        dialog?.tvMessage?.visibility = View.VISIBLE
        dialog?.tvTitle?.visibility = View.GONE
        return this
    }

    fun setDismissListener(dismiss: () -> Unit): AlertDialog {
        dialog?.setOnDismissListener { dismiss() }
        return this
    }

    private fun string(@StringRes res: Int): String {
        return dialog?.context?.getString(res) ?: "-"
    }

    fun setCancelable(value: Boolean): AlertDialog {
        dialog?.setCancelable(value)
        return this
    }

    fun show(@StringRes title: Int, @StringRes message: Int): BottomSheetDialog {
        if (dialog?.isShowing == true) return dialog as BottomSheetDialog
        dialog?.tvTitle?.text = string(title)
        dialog?.tvMessage?.text = string(message)

        dialog?.tvTitle?.visibility = View.VISIBLE
        dialog?.tvMessage?.visibility = View.VISIBLE

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
