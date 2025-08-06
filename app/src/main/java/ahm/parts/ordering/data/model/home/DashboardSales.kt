package ahm.parts.ordering.data.model.home


import com.google.gson.annotations.SerializedName

data class DashboardSales(
    @SerializedName("actual")
    var `actual`: Int = 0,
    @SerializedName("kredit")
    var kredit: Int = 0,
    @SerializedName("target")
    var target: Int = 0,
    @SerializedName("tunai")
    var tunai: Int = 0
)