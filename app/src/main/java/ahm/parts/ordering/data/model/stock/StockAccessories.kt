package ahm.parts.ordering.data.model.stock


import com.google.gson.annotations.SerializedName

data class StockAccessories(
    @SerializedName("code")
    var code: String = "",
    @SerializedName("id")
    var id: Int = 0,
    @SerializedName("image")
    var image: String = "",
    @SerializedName("name")
    var name: String = "",
    @SerializedName("price")
    var price: Long = 0,
    @SerializedName("stock")
    var stock: Int = 0
)