package ahm.parts.ordering.data.model.notification


import ahm.parts.ordering.data.model.home.campaignpromo.PartPromo
import ahm.parts.ordering.data.model.home.dealer.kreditlimit.KreditJatuhTempo
import com.google.gson.annotations.SerializedName

data class Notification(
    @SerializedName("id")
    var id: Int = 0,
    @SerializedName("kredit_limit")
    var kreditLimit: KreditJatuhTempo = KreditJatuhTempo(),
    @SerializedName("new_promo")
    var newPromo: PartPromo = PartPromo(),
    @SerializedName("information")
    var information: Information = Information(),
    @SerializedName("type_notice")
    var typeNotice: String = ""
)


data class Information(
    @SerializedName("description")
    var description: String = "",
    @SerializedName("name")
    var name: String = "",
    @SerializedName("small_info")
    var smallInfo: String = ""
)