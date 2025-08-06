package ahm.parts.ordering.data.model.home.dashboard.partnumber


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class TypeMotorX(
    
    @SerializedName("type_motor")
    @Expose
    var typeMotor: String = ""
)