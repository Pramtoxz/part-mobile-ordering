package ahm.parts.ordering.data.model.home


import com.google.gson.annotations.SerializedName

data class TugasTertunda(
    @SerializedName("prospek")
    var prospek: Int = 0,
    @SerializedName("sales")
    var sales: Int = 0,
    @SerializedName("spk")
    var spk: Int = 0
)