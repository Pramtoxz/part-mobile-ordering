package ahm.parts.ordering.data.model.home.dashboard.partnumber.cart


import com.google.gson.annotations.SerializedName

data class Cart(
    @SerializedName("created_at")
    var createdAt: String = "",
    @SerializedName("detail")
    var detail: List<CartList> = listOf(),
    @SerializedName("discount")
    var discount: String = "",
    @SerializedName("id")
    var id: Int = 0,
    @SerializedName("month_delivery")
    var monthDelivery: String = "",
    @SerializedName("no_order")
    var noOrder: String = "",
    @SerializedName("status")
    var status: String = "",
    @SerializedName("sub_price")
    var subPrice: String = "",
    @SerializedName("total_price")
    var totalPrice: String = "",
    @SerializedName("updated_at")
    var updatedAt: String = "",
    @SerializedName("users_id")
    var usersId: Int = 0
)

data class CartList(
    @SerializedName("amount_total")
    var amountTotal: Int = 0,
    @SerializedName("available_part")
    var availablePart: String = "",
    @SerializedName("created_at")
    var createdAt: String = "",
    @SerializedName("dus")
    var dus: String = "",
    @SerializedName("flag_numbering")
    var flagNumbering: String = "",
    @SerializedName("het")
    var het: String = "",
    @SerializedName("hgp_acc_tl")
    var hgpAccTl: String = "",
    @SerializedName("hotline_flag")
    var hotlineFlag: String = "",
    @SerializedName("hotline_max_qty")
    var hotlineMaxQty: String = "",
    @SerializedName("id")
    var id: Int = 0,
    @SerializedName("id_part")
    var idPart: Int = 0,
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
    @SerializedName("qty")
    var qty: String = "",
    @SerializedName("sub_price")
    var subPrice: Double = 0.0,
    @SerializedName("total_part")
    var totalPart: Int = 0,
    @SerializedName("type_motor")
    var typeMotor: List<TypeMotor> = listOf(),
    @SerializedName("updated_at")
    var updatedAt: String = "",
    var discount : String = ""
)

data class TypeMotor(
    @SerializedName("type_motor")
    var typeMotor: String = ""
)
