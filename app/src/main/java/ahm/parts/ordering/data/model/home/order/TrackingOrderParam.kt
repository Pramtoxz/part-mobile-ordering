package ahm.parts.ordering.data.model.home.order

data class TrackingOrderParam(
    var partNumber : String = "",
    var partDescription : String = "",
    var month : String = "",
    var noOrder : String = "",
    var statusBo : String = "",
    var statusInvoice : String = "",
    var statusShipping : String = ""
)