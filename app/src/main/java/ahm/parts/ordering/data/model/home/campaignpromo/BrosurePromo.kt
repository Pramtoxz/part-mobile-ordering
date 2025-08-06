package ahm.parts.ordering.data.model.home.campaignpromo


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class BrosurePromo(
    
    @SerializedName("content")
    @Expose
    var content: String = "",
    
    @SerializedName("created_at")
    @Expose
    var createdAt: String = "",
    
    @SerializedName("id")
    @Expose
    var id: Int = 0,

    @SerializedName("code")
    @Expose
    var code: String = "",
    
    @SerializedName("note")
    @Expose
    var note: String = "",
    
    @SerializedName("photo")
    @Expose
    var photo: String = "",
    
    @SerializedName("promo_end")
    @Expose
    var promoEnd: String = "",
    
    @SerializedName("promo_start")
    @Expose
    var promoStart: String = "",
    
    @SerializedName("title")
    @Expose
    var title: String = "",
    
    @SerializedName("updated_at")
    @Expose
    var updatedAt: String = ""
)