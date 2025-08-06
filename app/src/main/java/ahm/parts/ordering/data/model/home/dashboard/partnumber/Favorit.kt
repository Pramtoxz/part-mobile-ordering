package ahm.parts.ordering.data.model.home.dashboard.partnumber


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class Favorit(
    
    @SerializedName("dus")
    @Expose
    var dus: Int = 0,
    
    @SerializedName("flag_numbering")
    @Expose
    var flagNumbering: String = "",
    
    @SerializedName("HET")
    @Expose
    var hET: String = "",
    
    @SerializedName("hgp_acc_tl")
    @Expose
    var hgpAccTl: String = "",
    
    @SerializedName("hotline_flag")
    @Expose
    var hotlineFlag: String = "",
    
    @SerializedName("hotline_max_qty")
    @Expose
    var hotlineMaxQty: Int = 0,
    
    @SerializedName("id")
    @Expose
    var id: Int = 0,
    
    @SerializedName("is_love")
    @Expose
    var isLove: Int = 0,
    
    @SerializedName("is_promo")
    @Expose
    var isPromo: Int = 0,
    
    @SerializedName("item_group")
    @Expose
    var itemGroup: String = "",
    
    @SerializedName("kit_flag")
    @Expose
    var kitFlag: String = "",
    
    @SerializedName("part_description")
    @Expose
    var partDescription: String = "",
    
    @SerializedName("part_number")
    @Expose
    var partNumber: String = "",
    
    @SerializedName("type_motor")
    @Expose
    var typeMotor: List<TypeMotorX> = listOf()
)
