package ahm.service.app.ui.dialog

import ahm.parts.ordering.R
import ahm.parts.ordering.ui.widget.progressbar.CircularProgressBar
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager

class CenterLoadingDialog : DialogFragment() {

    private lateinit var progressBar: CircularProgressBar

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val mView = inflater.inflate(R.layout.loading_dialog, container, false)
        progressBar = mView.findViewById(R.id.progressWheel)
        progressBar.visibility = View.VISIBLE
        dialog!!.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog!!.window!!.requestFeature(Window.FEATURE_NO_TITLE)

        isCancelable = false


        return mView
    }

    override fun onDismiss(dialog: DialogInterface) {
        progressBar.visibility = View.GONE
        super.onDismiss(dialog)
    }

    fun show(manager: FragmentManager) {
        show(manager, "")
    }

    override fun show(manager: FragmentManager, tag: String?) {
        try {
            val ft = manager.beginTransaction()
            ft.add(this, tag)
            ft.commit()
        } catch (e: IllegalStateException) {

        }

    }

    override fun dismiss() {
        if (this.dialog != null && !this.isRemoving) {
            if (this.dialog?.isShowing!!) {
                super.dismiss()
            }
        }
    }
}
