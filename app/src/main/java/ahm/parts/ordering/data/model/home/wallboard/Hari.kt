package ahm.parts.ordering.data.model.home.wallboard


import com.google.gson.annotations.SerializedName

data class Hari(
    @SerializedName("date")
    var date: String = "",
    @SerializedName("full_name")
    var fullName: String = "",
    @SerializedName("id")
    var id: Int = 0,
    @SerializedName("name")
    var name: String = "",
    @SerializedName("tgl")
    var tgl: Int = 0
)