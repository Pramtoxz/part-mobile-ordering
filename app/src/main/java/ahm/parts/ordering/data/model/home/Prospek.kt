package ahm.parts.ordering.data.model.home


import com.google.gson.annotations.SerializedName

data class Prospek(
    @SerializedName("actual")
    var `actual`: Int = 0,
    @SerializedName("belum_fu")
    var belumFu: Int = 0,
    @SerializedName("prospek")
    var prospek: Int = 0,
    @SerializedName("target")
    var target: Int = 0
)