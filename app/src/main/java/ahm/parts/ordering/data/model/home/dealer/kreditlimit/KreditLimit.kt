package ahm.parts.ordering.data.model.home.dealer.kreditlimit


import com.google.gson.annotations.SerializedName

data class KreditLimit(
    @SerializedName("date")
    var date: String = "",
    @SerializedName("dealer_code")
    var dealerCode: String = "",
    @SerializedName("dealer_name")
    var dealerName: String = "",
    @SerializedName("maximum_open")
    var maximumOpen: Double = 0.0,
    @SerializedName("plafon_kredit_limit")
    var plafonKreditLimit: Double = 0.0
)