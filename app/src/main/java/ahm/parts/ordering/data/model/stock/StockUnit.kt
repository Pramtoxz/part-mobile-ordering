package ahm.parts.ordering.data.model.stock


import com.google.gson.annotations.SerializedName

data class StockUnit(
    @SerializedName("id")
    var id: Int = 0,
    @SerializedName("code")
    var code: String = "",
    @SerializedName("image")
    var image: String = "",
    @SerializedName("name")
    var name: String = "",
    @SerializedName("price")
    var price: Long = 0,
    @SerializedName("total_accessories")
    var totalAccessories: Int = 0
)