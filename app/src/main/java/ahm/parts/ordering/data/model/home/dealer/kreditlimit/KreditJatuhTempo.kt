package ahm.parts.ordering.data.model.home.dealer.kreditlimit


import com.google.gson.annotations.SerializedName

data class KreditJatuhTempo(
    @SerializedName("amount")
    var amount: Double = 0.0,
    @SerializedName("date")
    var date: String = "",
    @SerializedName("date_over")
    var dateOver: String = "",
    @SerializedName("dealer_code")
    var dealerCode: String = "",
    @SerializedName("dealer_name")
    var dealerName: String = "",
    @SerializedName("flag")
    var flag: String = ""
)