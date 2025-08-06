package ahm.parts.ordering.data.model.stock


import com.google.gson.annotations.SerializedName

data class StockApparel(
    @SerializedName("code")
    var code: String = "",
    @SerializedName("id")
    var id: Int = 0,
    @SerializedName("image")
    var image: String = "",
    @SerializedName("material")
    var material: String = "",
    @SerializedName("name")
    var name: String = "",
    @SerializedName("price")
    var price: Long = 0,
    @SerializedName("spesifikasi")
    var spesifikasi: String = "",
    @SerializedName("stock")
    var stock: Int = 0,
    @SerializedName("ukuran")
    var ukuran: String = "",
    @SerializedName("warna")
    var warna: String = ""
)