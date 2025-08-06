package ahm.parts.ordering.data.model.home


import com.google.gson.annotations.SerializedName

data class Aktifitas(
    @SerializedName("categories")
    var categories: String = "",
    @SerializedName("info")
    var info: String = "",
    @SerializedName("name")
    var name: String = "",
    @SerializedName("time")
    var time: String = ""
){
    companion object{
        const val STATUS_PROSPEK = "Prospek"
        const val STATUS_SPK = "SPK"
    }
}