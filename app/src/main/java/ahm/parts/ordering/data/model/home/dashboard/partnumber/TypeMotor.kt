package ahm.parts.ordering.data.model.home.dashboard.partnumber


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class TypeMotor(
    
    @SerializedName("favorit")
    @Expose
    var favorit: List<AllTypeMotor> = listOf(),
    
    @SerializedName("list")
    @Expose
    var list: List<AllTypeMotor> = listOf()
)


data class FavoritMotor(

    @SerializedName("id")
    @Expose
    var id: Int = 0,

    @SerializedName("name")
    @Expose
    var name: String = ""
)

data class AllTypeMotor(

    @SerializedName("id")
    @Expose
    var id: String = "",

    @SerializedName("name")
    @Expose
    var name: String = ""
)

