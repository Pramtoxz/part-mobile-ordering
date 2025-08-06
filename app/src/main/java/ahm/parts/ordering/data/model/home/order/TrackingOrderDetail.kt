package ahm.parts.ordering.data.model.home.order


import com.google.gson.annotations.SerializedName

data class TrackingOrderDetail(
    @SerializedName("id")
    var id: Int = 0,
    @SerializedName("invoice")
    var invoice: List<Invoice> = listOf(),
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
    var totalPcs: Int = 0,
    @SerializedName("total_price")
    var totalPrice: Int = 0,
    @SerializedName("type_motor")
    var typeMotor: List<TypeMotor> = listOf()
)
data class Invoice(
    @SerializedName("date")
    var date: String = "",
    @SerializedName("delivery_number")
    var deliveryNumber: String = "",
    @SerializedName("delivery_order")
    var deliveryOrder: String = "",
    @SerializedName("expedition")
    var expedition: String = "",
    @SerializedName("id")
    var id: Int = 0,
    @SerializedName("invoice_no")
    var invoiceNo: String = "",
    @SerializedName("total_pcs")
    var totalPcs: String = "",
    @SerializedName("total_price")
    var totalPrice: Double = 0.0
)