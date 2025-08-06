package ahm.parts.ordering.data.model.home.campaignpromo


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class PartPromo(
    
    @SerializedName("available_part")
    @Expose
    var availablePart: String = "",
    
    @SerializedName("begin_effdate")
    @Expose
    var beginEffdate: String = "",
    
    @SerializedName("code")
    @Expose
    var code: String = "",
    
    @SerializedName("description")
    @Expose
    var description: String = "",
    
    @SerializedName("discount")
    @Expose
    var discount: String = "",
    
    @SerializedName("dus")
    @Expose
    var dus: Int = 0,
    
    @SerializedName("end_effdate")
    @Expose
    var endEffdate: String = "",
    
    @SerializedName("flag_numbering")
    @Expose
    var flagNumbering: String = "",
    
    @SerializedName("het")
    @Expose
    var het: String = "",
    
    @SerializedName("het_promo")
    @Expose
    var hetPromo: Int = 0,
    
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
    
    @SerializedName("name")
    @Expose
    var name: String = "",
    
    @SerializedName("note")
    @Expose
    var note: String = "",
    
    @SerializedName("part_description")
    @Expose
    var partDescription: String = "",
    
    @SerializedName("part_number")
    @Expose
    var partNumber: String = "",
    
    @SerializedName("type_motor")
    @Expose
    var typeMotor: List<TypeMotor> = listOf()
)

data class TypeMotor(

    @SerializedName("type_motor")
    @Expose
    var typeMotor: String = ""
)