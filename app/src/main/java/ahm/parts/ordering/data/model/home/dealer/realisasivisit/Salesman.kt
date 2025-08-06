package ahm.parts.ordering.data.model.home.dealer.realisasivisit


import com.google.gson.annotations.SerializedName

data class Salesman(
    @SerializedName("created_at")
    var createdAt: String = "",
    @SerializedName("id")
    var id: String = "",
    @SerializedName("name")
    var name: String = "",
    @SerializedName("sales_code")
    var salesCode: String = "",
    @SerializedName("updated_at")
    var updatedAt: String = "",
    var selectedSales : Boolean = false
)