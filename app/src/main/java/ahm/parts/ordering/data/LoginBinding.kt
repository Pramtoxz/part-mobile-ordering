package ahm.parts.ordering.data

import ahm.parts.ordering.R
import ahm.parts.ordering.helper.setAdapter
import android.annotation.SuppressLint
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView


class LoginBinding(kodeUser: String = "", password: String = "") {

    companion object {

        @SuppressLint("SetTextI18n")
        @JvmStatic
        @BindingAdapter(value = ["bind:list", "bind:role"], requireAll = true)
        fun loginApi(view: RecyclerView, list: ArrayList<String>,role : String) {

            view.setAdapter(view.context, list, R.layout.item_order_sugestion, {

            }, {

            })

        }

    }

}