package ahm.parts.ordering.ui.dialog


import ahm.parts.ordering.R
import android.app.Dialog
import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.annotation.ColorInt
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView

class BasicRenewTokenDialog : View.OnClickListener {

    private lateinit var dialog: Dialog
    private lateinit var dialogView: View

    private lateinit var titleView: AppCompatTextView
    private lateinit var messageView: AppCompatTextView
    private lateinit var btnPositive: AppCompatButton
    private lateinit var btnNegative: AppCompatButton
    private lateinit var ivState: ImageView
    private lateinit var etPassword: EditText

    private lateinit var buttonPositiveClickListener: ButtonPositiveClickListener
    private lateinit var buttonNegativeClickListener: ButtonNegativeClickListener

    internal val isShowing: Boolean
        get() = dialog.isShowing

    constructor(context: Context) {
        init(context, AlertDialog.Builder(context))
    }

    constructor(context: Context, theme: Int) {
        init(context, AlertDialog.Builder(context, theme))
    }

    private fun init(context: Context, dialogBuilder: AlertDialog.Builder) {
        dialogView = LayoutInflater.from(dialogBuilder.context)
            .inflate(R.layout.dialog_basic_renew_token, null)
        dialog = dialogBuilder.setView(dialogView).create()

        titleView = dialogView.findViewById(R.id.tv_title)
        messageView = dialogView.findViewById(R.id.tv_message)
        btnPositive = dialogView.findViewById(R.id.btn_positive)
        btnNegative = dialogView.findViewById(R.id.btn_negative)
        ivState = dialogView.findViewById(R.id.iv_state)
        etPassword = dialogView.findViewById(R.id.etPassword)
        etPassword.setText(context.getString(R.string.token_expired_lbl_password))

        btnPositive.setOnClickListener(this)
        btnNegative.setOnClickListener(this)
        dialog.setCancelable(false)
    }

    fun setAlert(@StringRes message: Int, @StringRes btnText: Int, @StringRes btnText2: Int): BasicRenewTokenDialog {
        return setAlert(string(message), string(btnText), string(btnText2))
    }

    fun setAlert(
        message: CharSequence,
        btnText: CharSequence,
        btnText2: CharSequence
    ): BasicRenewTokenDialog {
        messageView.visibility = View.VISIBLE
        messageView.text = message

        btnPositive.visibility = View.VISIBLE
        btnPositive.text = btnText

        btnNegative.visibility = View.VISIBLE
        btnNegative.text = btnText2

        return this
    }

    fun setButtonPositiveClickListener(listener: ButtonPositiveClickListener): BasicRenewTokenDialog {
        this.buttonPositiveClickListener = listener
        return this
    }

    fun setButtonNegativeClickListener(listener: ButtonNegativeClickListener): BasicRenewTokenDialog {
        this.buttonNegativeClickListener = listener
        return this
    }

    fun setMessage(@StringRes message: Int): BasicRenewTokenDialog {
        return setMessage(string(message))
    }

    fun setMessage(message: CharSequence): BasicRenewTokenDialog {
        messageView.visibility = View.VISIBLE
        messageView.text = message
        return this
    }

    fun setTitle(@StringRes message: Int): BasicRenewTokenDialog {
        return setTitle(string(message))
    }

    fun setTitle(message: CharSequence): BasicRenewTokenDialog {
        titleView.visibility = View.VISIBLE
        titleView.text = message
        return this
    }

    fun setTitleVisibility(isVisible: Boolean): BasicRenewTokenDialog {
        titleView!!.visibility = if (isVisible) View.VISIBLE else View.GONE
        return this
    }

    fun setSingleButton(): BasicRenewTokenDialog {
        btnNegative!!.visibility = View.GONE
        return this
    }

    fun setOnlyText(): BasicRenewTokenDialog {
        ivState!!.visibility = View.GONE
        return this
    }

    fun setIvState(resourceId: Int?): BasicRenewTokenDialog {
        ivState!!.setImageResource(resourceId!!)
        return this
    }

    fun setPositiveButton(@StringRes message: Int): BasicRenewTokenDialog {
        return setPositiveButton(string(message))
    }

    fun setPositiveButton(message: CharSequence): BasicRenewTokenDialog {
        btnPositive!!.visibility = View.VISIBLE
        btnPositive!!.text = message
        return this
    }

    fun setPositiveButtonBackground(drawable: Drawable): BasicRenewTokenDialog {
        btnPositive!!.background = drawable
        return this
    }

    fun setPositiveButtonColor(@ColorInt color: Int): BasicRenewTokenDialog {
        btnPositive!!.setTextColor(color)
        return this
    }

    fun setNegativeButton(@StringRes message: Int): BasicRenewTokenDialog {
        return setNegativeButton(string(message))
    }

    fun setNegativeButton(message: CharSequence): BasicRenewTokenDialog {
        btnNegative.visibility = View.VISIBLE
        btnNegative.text = message
        return this
    }

    fun setNegativeButtonBackground(drawable: Drawable): BasicRenewTokenDialog {
        btnNegative.background = drawable
        return this
    }

    fun setNegativeButtonColor(@ColorInt color: Int): BasicRenewTokenDialog {
        btnNegative.setTextColor(color)
        return this
    }

    fun setMessageGravity(gravity: Int): BasicRenewTokenDialog {
        messageView!!.gravity = gravity
        return this
    }

    fun setCancelable(cancelable: Boolean): BasicRenewTokenDialog {
        dialog!!.setCancelable(cancelable)
        return this
    }


    fun setPasswordText(message: String): BasicRenewTokenDialog {
        etPassword.setText(message)
        return this
    }

    protected fun string(@StringRes res: Int): String {
        return dialogView!!.context.getString(res)
    }

    protected fun <ViewClass : View> findView(id: Int): ViewClass {
        return dialogView.findViewById<View>(id) as ViewClass
    }

    fun dismiss() {
        dialog.dismiss()
    }

    fun show(): Dialog {
        if (!dialog.isShowing) {
            dialog.show()
        }
        return dialog
    }

    override fun onClick(view: View) {
        when (view.id) {
            R.id.btn_positive -> buttonPositiveClickListener.clicked(
                etPassword.text.toString().trim(),
                this
            )
            R.id.btn_negative -> buttonNegativeClickListener.clicked(this)
        }
    }

    interface ButtonPositiveClickListener {
        fun clicked(value: String, dialog: BasicRenewTokenDialog)
    }

    interface ButtonNegativeClickListener {
        fun clicked(dialog: BasicRenewTokenDialog)
    }
}
