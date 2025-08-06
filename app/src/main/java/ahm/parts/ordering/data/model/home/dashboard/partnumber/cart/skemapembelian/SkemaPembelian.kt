package ahm.parts.ordering.data.model.home.dashboard.partnumber.cart.skemapembelian


import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class SkemaPembelian(

    @SerializedName("amount_avg")
    @Expose
    var amountAvg: Double = 0.0,

    @SerializedName("flag_campaign")
    @Expose
    var flagCampaign: String = "",

    @SerializedName("amount_back")
    @Expose
    var amountBack: String = "",

    @SerializedName("amount_suggest")
    @Expose
    var amountSuggest: String = "",

    @SerializedName("amount_total")
    @Expose
    var amountTotal: Double = 0.0,

    @SerializedName("available_part")
    @Expose
    var availablePart: String = "",

    @SerializedName("het")
    @Expose
    var het: Double = 0.0,

    @SerializedName("het_promo")
    @Expose
    var hetPromo: Double = 0.0,

    @SerializedName("list")
    @Expose
    var list: List<ListSkemaPembelian> = listOf(),

    @SerializedName("multiple_dus")
    @Expose
    var multipleDus: Int = 0,

    @SerializedName("part_description")
    @Expose
    var partDescription: String = "",

    @SerializedName("part_number")
    @Expose
    var partNumber: String = "",

    @SerializedName("qty_avg")
    @Expose
    var qtyAvg: String = "",

    @SerializedName("qty_back")
    @Expose
    var qtyBack: String = "",

    @SerializedName("qty_suggest")
    @Expose
    var qtySuggest: String = "",

    @SerializedName("qty_total")
    @Expose
    var qtyTotal: String = "",

    @SerializedName("skema_selected")
    @Expose
    var skemaSelected: String = "",

    @SerializedName("type_motor")
    @Expose
    var typeMotor: List<TypeMotor> = listOf()
)


data class TypeMotor(

    @SerializedName("type_motor")
    @Expose
    var typeMotor: String = ""
)

data class ListSkemaPembelian(

    @SerializedName("name_pembelian")
    @Expose
    var namePembelian: String = "",

    @SerializedName("name_price")
    @Expose
    var namePrice: String = "",

    @SerializedName("price")
    @Expose
    var price: Double = 0.0,

    @SerializedName("qty")
    @Expose
    var qty: String = ""
)