package ahm.parts.ordering.data.model.home.dashboard


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Dashboard(
    
    @SerializedName("efectifitas_visit")
    @Expose
    var efectifitasVisit: String = "",
    
    @SerializedName("my_rank")
    @Expose
    var myRank: MyRank?,
    
    @SerializedName("pencapaian")
    @Expose
    var pencapaian: Double?,
    
    @SerializedName("pencapaian_campaign")
    @Expose
    var pencapaianCampaign: String = "",
    
    @SerializedName("produktivitas")
    @Expose
    var produktivitas: String = "",
    
    @SerializedName("realisasi_visit")
    @Expose
    var realisasiVisit: String = "",
    
    @SerializedName("sales_man_rank")
    @Expose
    var salesManRank: List<SalesManRank>?,
    
    @SerializedName("target")
    @Expose
    var target: Double?,
    
    @SerializedName("total_omzet")
    @Expose
    var totalOmzet: Double?
)


data class MyRank(

    @SerializedName("id")
    @Expose
    var id: Int = 0,

    @SerializedName("name")
    @Expose
    var name: String = "",

    @SerializedName("peringkat")
    @Expose
    var peringkat: String = "",

    @SerializedName("photo")
    @Expose
    var photo: String = "",

    @SerializedName("rank")
    @Expose
    var rank: String = "",

    @SerializedName("type")
    @Expose
    var type: String = ""
)


data class SalesManRank(

    @SerializedName("id")
    @Expose
    var id: Int?,

    @SerializedName("name")
    @Expose
    var name: String?,

    @SerializedName("peringkat")
    @Expose
    var peringkat: String?,

    @SerializedName("photo")
    @Expose
    var photo: String = "",

    @SerializedName("rank")
    @Expose
    var rank: Int?,

    @SerializedName("type")
    @Expose
    var type: String = ""
)