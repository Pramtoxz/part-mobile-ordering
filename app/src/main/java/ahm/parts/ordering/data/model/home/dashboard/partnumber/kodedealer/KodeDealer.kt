package ahm.parts.ordering.data.model.home.dashboard.partnumber.kodedealer


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class KodeDealer(
    
    @SerializedName("favorit")
    @Expose
    var favorit: List<AllDealer> = listOf(),
    
    @SerializedName("list")
    @Expose
    var list: List<AllDealer> = listOf()
)


data class Favorit(

    @SerializedName("code")
    @Expose
    var code: String = "",

    @SerializedName("id")
    @Expose
    var id: Int = 0,

    @SerializedName("latitude")
    @Expose
    var latitude: String = "",

    @SerializedName("longitude")
    @Expose
    var longitude: String = "",

    @SerializedName("name")
    @Expose
    var name: String = ""
)

data class AllDealer(

    @SerializedName("code")
    @Expose
    var code: String = "",

    @SerializedName("id")
    @Expose
    var id: String = "", 

    @SerializedName("latitude")
    @Expose
    var latitude: String = "",

    @SerializedName("longitude")
    @Expose
    var longitude: String = "",

    @SerializedName("name")
    @Expose
    var name: String = "",

    @SerializedName("address")
    @Expose
    var address: String = "",

    var selectedDealer :Boolean = false

)