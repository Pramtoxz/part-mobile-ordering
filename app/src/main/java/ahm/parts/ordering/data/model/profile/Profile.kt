package ahm.parts.ordering.data.model.profile


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Profile(
    
    @SerializedName("email")
    @Expose
    var email: String = "",
    
    @SerializedName("id")
    @Expose
    var id: String = "",
    
    @SerializedName("job")
    @Expose
    var job: String = "",
    
    @SerializedName("name")
    @Expose
    var name: String = "",
    
    @SerializedName("phone_number")
    @Expose
    var phoneNumber: String = "",
    
    @SerializedName("photo")
    @Expose
    var photo: String = ""
)