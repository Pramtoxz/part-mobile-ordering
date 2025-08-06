package ahm.parts.ordering.data.model.home.order


import com.google.gson.annotations.SerializedName

data class TrackingOrder(
    @SerializedName("bo_quantity")
    var boQuantity: String = "",
    @SerializedName("code")
    var code: String = "",
    @SerializedName("date_order")
    var dateOrder: String = "",
    @SerializedName("id")
    var id: Int = 0,
    @SerializedName("item")
    var item: List<TrackingItem> = listOf(),
    @SerializedName("shipping_quantity")
    var shippingQuantity: String = "",
    @SerializedName("total_item")
    var totalItem: String = "",
    @SerializedName("total_pcs")
    var totalPcs: String = "",
    @SerializedName("total_price")
    var totalPrice: String = ""
)

data class TrackingItem(
    @SerializedName("id")
    var id: Int = 0,
    @SerializedName("item_group")
    var itemGroup: String = "",
    @SerializedName("part_description")
    var partDescription: String = "",
    @SerializedName("part_id")
    var partId: Int = 0,
    @SerializedName("part_number")
    var partNumber: String = "",
    @SerializedName("sub_price")
    var subPrice: Int = 0,
    @SerializedName("total_pcs")
    var totalPcs: String = "",
    @SerializedName("delivered")
    var delivered: String = "",
    @SerializedName("total_price")
    var totalPrice: String = "",
    @SerializedName("type_motor")
    var typeMotor: List<TypeMotor> = listOf()
)


data class TypeMotor(
    @SerializedName("type_motor")
    var typeMotor: String = ""
)