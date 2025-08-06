package ahm.parts.ordering.data.model.home


import com.google.gson.annotations.SerializedName

data class SPK(
    @SerializedName("pending")
    var pending: Int = 0,
    @SerializedName("program")
    var program: Int = 0
)