package ahm.parts.ordering.data.model.home.dashboard.partnumber


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class KelompokBarang(
    
    @SerializedName("favorit")
    @Expose
    var favorit: List<AllKelompokBarang> = listOf(),
    
    @SerializedName("list")
    @Expose
    var list: List<AllKelompokBarang> = listOf()
)

data class AllKelompokBarang(

    @SerializedName("id")
    @Expose
    var id: String = "",

    @SerializedName("name")
    @Expose
    var name: String = "" ,

    @SerializedName("code")
    @Expose
    var code: String = ""
)

