package ahm.parts.ordering.data.model.leaderboard


import com.google.gson.annotations.SerializedName

data class Leaderboard(
    @SerializedName("id")
    var id: Int = 0,
    @SerializedName("is_your")
    var isYour: Boolean = false,
    @SerializedName("name")
    var name: String = "",
    @SerializedName("point")
    var point: Int = 0,
    @SerializedName("position_info")
    var positionInfo: String = "",
    @SerializedName("position_number")
    var positionNumber: Int = 0,
    @SerializedName("rank")
    var rank: Int = 0,
    @SerializedName("image")
    var image: String = ""
){
    companion object{
        const val RANGKING_UP = "naik"
        const val RANGKING_DOWN = "turun"
    }
}