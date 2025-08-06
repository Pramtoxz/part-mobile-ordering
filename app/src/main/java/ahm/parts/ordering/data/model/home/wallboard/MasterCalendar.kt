package ahm.parts.ordering.data.model.home.wallboard


import com.google.gson.annotations.SerializedName

data class MasterCalendar(
    @SerializedName("bulan")
    var bulan: List<Bulan> = listOf(),
    @SerializedName("default_month")
    var defaultMonth: String = "",
    @SerializedName("default_year")
    var defaultYear: String = ""
)