package ahm.parts.ordering.data.model.home.dashboard.partnumber.cart.suggestorder


import com.google.gson.annotations.SerializedName

data class SuggestOrder(
    @SerializedName("amount_total")
    var amountTotal: Double = 0.0,
    @SerializedName("available_part")
    var availablePart: String = "",
    @SerializedName("created_at")
    var createdAt: String = "",
    @SerializedName("dus")
    var dus: String = "",
    @SerializedName("flag_numbering")
    var flagNumbering: String = "",
    @SerializedName("het")
    var het: Int = 0,
    @SerializedName("hgp_acc_tl")
    var hgpAccTl: String = "",
    @SerializedName("hotline_flag")
    var hotlineFlag: String = "",
    @SerializedName("hotline_max_qty")
    var hotlineMaxQty: String = "",
    @SerializedName("id")
    var id: Int = 0,
    @SerializedName("is_love")
    var isLove: Int = 0,
    @SerializedName("is_promo")
    var isPromo: Int = 0,
    @SerializedName("item_group")
    var itemGroup: String = "",
    @SerializedName("kit_flag")
    var kitFlag: String = "",
    @SerializedName("ms_dealer_id")
    var msDealerId: Int = 0,
    @SerializedName("part_description")
    var partDescription: String = "",
    @SerializedName("part_number")
    var partNumber: String = "",
    @SerializedName("qty_total")
    var qtyTotal: Int = 0,
    @SerializedName("qty_back")
    var qtyBack: String = "",
    @SerializedName("skema_selected")
    var skemaSelected: String = "",
    @SerializedName("status_delivery")
    var statusDelivery: String = "",
    @SerializedName("total_part")
    var totalPart: Int = 0,
    @SerializedName("total_price")
    var totalPrice: Int = 0,
    @SerializedName("total_qty")
    var totalQty: Int = 0,
    @SerializedName("type_motor")
    var typeMotor: List<TypeMotor> = listOf(),
    @SerializedName("updated_at")
    var updatedAt: String = "",
    var isChecked : Boolean = false

)

data class TypeMotor(
    @SerializedName("type_motor")
    var typeMotor: String = ""
)