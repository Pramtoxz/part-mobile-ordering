package ahm.parts.ordering.helper

import ahm.parts.ordering.api.ApiResponse
import ahm.parts.ordering.api.ApiStatus
import android.content.Context
import android.widget.Toast
import org.json.JSONArray


class ToastUtil constructor(val context: Context) {

    private lateinit var toast: Toast

    fun showAToast(st: String?, duration: Int) {
        try {
            toast.view.isShown
            toast.setText(st)
        } catch (e: Exception) {
            toast = Toast.makeText(context, st, duration)
        }

        toast.show()
    }

    fun showAToast(st: String?) {
        try {
            toast.view.isShown
            toast.setText(st)
        } catch (e: Exception) {
            toast = Toast.makeText(context, st, Toast.LENGTH_SHORT)
        }

        toast.show()
    }

    private fun showToasts(items: JSONArray) {
        for (i in 0 until items.length()) {
            Toast.makeText(context, items[i].toString(), Toast.LENGTH_SHORT).show()
        }
    }

    fun showJsonToasts(item: ApiResponse) {
        if (item.status == ApiStatus.ERROR) {
            if (!item.message.isNull(0)) {
                showToasts(item.message)
            } else {
                showAToast(item.throwableBody)
            }
        } else if (item.status == ApiStatus.WRONG) showToasts(item.message)
    }

    fun cancelToast() {
        if (toast.view.isShown) {
            toast.cancel()
        }
    }

}