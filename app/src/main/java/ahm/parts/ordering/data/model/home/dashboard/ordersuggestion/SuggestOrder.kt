package ahm.parts.ordering.data.model.home.dashboard.ordersuggestion


import com.google.gson.annotations.SerializedName

data class SuggestOrder(
    @SerializedName("amount_avg")
    var amountAvg: Double = 0.0,
    @SerializedName("flag_campaign")
    var flagCampaign: String = "",
    @SerializedName("amount_back")
    var amountBack: Int = 0,
    @SerializedName("amount_suggest")
    var amountSuggest: Double = 0.0,
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
    var het: Double = 0.0,
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
    @SerializedName("list")
    var list: List<SkemaPembelian> = listOf(),
    @SerializedName("ms_dealer_id")
    var msDealerId: Int = 0,
    @SerializedName("multiple_dus")
    var multipleDus: String = "",
    @SerializedName("part_description")
    var partDescription: String = "",
    @SerializedName("part_number")
    var partNumber: String = "",
    @SerializedName("qty_avg")
    var qtyAvg: String = "",
    @SerializedName("qty_back")
    var qtyBack: String = "",
    @SerializedName("qty_suggest")
    var qtySuggest: String = "",
    @SerializedName("qty_total")
    var qtyTotal: Int = 0,
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

    var isChecked : Boolean = false,

    @SerializedName("type_motor")
    var typeMotor: List<TypeMotor> = listOf(),
    @SerializedName("updated_at")
    var updatedAt: String = ""
)

data class SkemaPembelian(
    @SerializedName("name_pembelian")
    var namePembelian: String = "",
    @SerializedName("name_price")
    var namePrice: String = "",
    @SerializedName("price")
    var price: Double = 0.0,
    @SerializedName("qty")
    var qty: String = ""
)

data class TypeMotor(
    @SerializedName("type_motor")
    var typeMotor: String = ""
)