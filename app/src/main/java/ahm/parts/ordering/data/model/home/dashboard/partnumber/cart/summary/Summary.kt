package ahm.parts.ordering.data.model.home.dashboard.partnumber.cart.summary


import com.google.gson.annotations.SerializedName

data class Summary(
    @SerializedName("code_order")
    var codeOrder: String = "",
    var monthDeliver: String = "",
    var totalPembelian: String = "",
    var itemPembelian: String = "",
    var discount: String = ""
)