package ahm.parts.ordering.data.model.home.dashboard


import com.google.gson.annotations.SerializedName

data class CheckoutDashboard(
    @SerializedName("checkin")
    var checkin: String = "",
    @SerializedName("code_visit")
    var codeVisit: String = "",
    @SerializedName("date_must_checkin")
    var dateMustCheckin: String = "",
    @SerializedName("dealer_name")
    var dealerName: String = "",
    @SerializedName("code_dealer")
    var codeDealer: String = "",
    @SerializedName("distance")
    var distance: Int = 0
)