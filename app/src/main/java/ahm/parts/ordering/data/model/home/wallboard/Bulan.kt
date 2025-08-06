package ahm.parts.ordering.data.model.home.wallboard


import com.google.gson.annotations.SerializedName

data class Bulan(
    @SerializedName("full_name")
    var fullName: String = "",
    @SerializedName("hari")
    var hari: List<Hari> = listOf(),
    @SerializedName("id")
    var id: Int = 0,
    @SerializedName("name")
    var name: String = ""
)