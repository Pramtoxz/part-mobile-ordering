package ahm.parts.ordering.data.model.home.dealer.salemanvisit


import com.google.gson.annotations.SerializedName

data class RencanaVisit(
    @SerializedName("created_at")
    var createdAt: String = "",
    @SerializedName("date")
    var date: String = "",
    @SerializedName("id")
    var id: Int = 0,
    @SerializedName("ms_dealer_id")
    var msDealerId: Int = 0,
    @SerializedName("updated_at")
    var updatedAt: Any? = Any()
)