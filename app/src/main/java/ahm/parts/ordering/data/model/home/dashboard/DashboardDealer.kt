package ahm.parts.ordering.data.model.home.dashboard


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class DashboardDealer(
    
    @SerializedName("average")
    @Expose
    var average: String = "",
    
    @SerializedName("pencapaian")
    @Expose
    var pencapaian: Double = 0.0,
    
    @SerializedName("pencapaian_campaign")
    @Expose
    var pencapaianCampaign: String = "",
    
    @SerializedName("point_program")
    @Expose
    var pointProgram: String = "",
    
    @SerializedName("target")
    @Expose
    var target: Double = 0.0,
    
    @SerializedName("total_omzet")
    @Expose
    var totalOmzet: Double = 0.0
)