package ahm.parts.ordering.ui.dialog

import ahm.parts.ordering.R
import android.app.Dialog
import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import androidx.annotation.ColorInt
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView

class BasicDialog : View.OnClickListener {

    private lateinit var dialog: Dialog
    private lateinit var dialogView: View

    private lateinit var titleView: AppCompatTextView
    private lateinit var messageView: AppCompatTextView
    private lateinit var btnPositive: AppCompatButton
    private lateinit var btnNegative: AppCompatButton
    private lateinit var ivState: ImageView

    private lateinit var buttonPositiveClickListener: ButtonPositiveClickListener
    private lateinit var buttonNegativeClickListener: ButtonNegativeClickListener

    internal val isShowing: Boolean
        get() = dialog.isShowing

    constructor(context: Context) {
        init(AlertDialog.Builder(context))
    }

    constructor(context: Context, theme: Int) {
        init(AlertDialog.Builder(context, theme))
    }

    private fun init(dialogBuilder: AlertDialog.Builder) {
        dialogView = LayoutInflater.from(dialogBuilder.context)
            .inflate(R.layout.dialog_basic_title, null)
        dialog = dialogBuilder.setView(dialogView).create()

        titleView = dialogView.findViewById(R.id.tv_title)
        messageView = dialogView.findViewById(R.id.tv_message)
        btnPositive = dialogView.findViewById(R.id.btn_positive)
        btnNegative = dialogView.findViewById(R.id.btn_negative)
        ivState = dialogView.findViewById(R.id.iv_state)

        btnPositive.setOnClickListener(this)
        btnNegative.setOnClickListener(this)
        dialog.setCancelable(false)
    }

    fun setAlert(@StringRes message: Int, @StringRes btnText: Int, @StringRes btnText2: Int): BasicDialog {
        return setAlert(string(message), string(btnText), string(btnText2))
    }

    fun setAlert(
        message: CharSequence,
        btnText: CharSequence,
        btnText2: CharSequence
    ): BasicDialog {
        messageView.visibility = View.VISIBLE
        messageView.text = message

        btnPositive.visibility = View.VISIBLE
        btnPositive.text = btnText

        btnNegative.visibility = View.VISIBLE
        btnNegative.text = btnText2

        return this
    }

    fun setButtonPositiveClickListener(listener: ButtonPositiveClickListener): BasicDialog {
        this.buttonPositiveClickListener = listener
        return this
    }

    fun setButtonNegativeClickListener(listener: ButtonNegativeClickListener): BasicDialog {
        this.buttonNegativeClickListener = listener
        return this
    }

    fun setMessage(@StringRes message: Int): BasicDialog {
        return setMessage(string(message))
    }

    fun setMessage(message: CharSequence): BasicDialog {
        messageView.visibility = View.VISIBLE
        messageView.text = message
        return this
    }

    fun setTitle(@StringRes message: Int): BasicDialog {
        return setTitle(string(message))
    }

    fun setTitle(message: CharSequence): BasicDialog {
        titleView.visibility = View.VISIBLE
        titleView.text = message
        return this
    }

    fun setTitleVisibility(isVisible: Boolean): BasicDialog {
        titleView!!.visibility = if (isVisible) View.VISIBLE else View.GONE
        return this
    }

    fun setSingleButton(): BasicDialog {
        btnNegative!!.visibility = View.GONE
        return this
    }

    fun setOnlyText(): BasicDialog {
        ivState!!.visibility = View.GONE
        return this
    }

    fun setIvState(resourceId: Int?): BasicDialog {
        ivState!!.setImageResource(resourceId!!)
        return this
    }

    fun setPositiveButton(@StringRes message: Int): BasicDialog {
        return setPositiveButton(string(message))
    }

    fun setPositiveButton(message: CharSequence): BasicDialog {
        btnPositive!!.visibility = View.VISIBLE
        btnPositive!!.text = message
        return this
    }

    fun setPositiveButtonBackground(drawable: Drawable): BasicDialog {
        btnPositive!!.background = drawable
        return this
    }

    fun setPositiveButtonColor(@ColorInt color: Int): BasicDialog {
        btnPositive!!.setTextColor(color)
        return this
    }

    fun setNegativeButton(@StringRes message: Int): BasicDialog {
        return setNegativeButton(string(message))
    }

    fun setNegativeButton(message: CharSequence): BasicDialog {
        btnNegative.visibility = View.VISIBLE
        btnNegative.text = message
        return this
    }

    fun setNegativeButtonBackground(drawable: Drawable): BasicDialog {
        btnNegative.background = drawable
        return this
    }

    fun setNegativeButtonColor(@ColorInt color: Int): BasicDialog {
        btnNegative.setTextColor(color)
        return this
    }

    fun setMessageGravity(gravity: Int): BasicDialog {
        messageView!!.gravity = gravity
        return this
    }

    fun setCancelable(cancelable: Boolean): BasicDialog {
        dialog!!.setCancelable(cancelable)
        return this
    }

    protected fun string(@StringRes res: Int): String {
        return dialogView!!.context.getString(res)
    }

    protected fun <ViewClass : View> findView(id: Int): ViewClass {
        return dialogView!!.findViewById<View>(id) as ViewClass
    }

    fun dismiss() {
        dialog!!.dismiss()
    }

    fun show(): Dialog {
        dialog!!.show()
        return dialog
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.btn_positive -> buttonPositiveClickListener.clicked(this)
            R.id.btn_negative -> buttonNegativeClickListener.clicked(this)
        }
    }

    interface ButtonPositiveClickListener {
        fun clicked(dialog: BasicDialog)
    }

    interface ButtonNegativeClickListener {
        fun clicked(dialog: BasicDialog)
    }
}
