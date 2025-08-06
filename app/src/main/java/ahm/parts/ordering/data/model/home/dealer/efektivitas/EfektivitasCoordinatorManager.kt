package ahm.parts.ordering.data.model.home.dealer.efektivitas


import com.google.gson.annotations.SerializedName

data class EfektivitasCoordinatorManager(
    @SerializedName("actual")
    var `actual`: String = "",
    @SerializedName("amount_total")
    var amountTotal: Double = 0.0,
    @SerializedName("data")
    var `data`: List<Coordinator> = listOf(),
    @SerializedName("efectivitas")
    var efectivitas: String = "",
    @SerializedName("page")
    var page: Int = 0,
    @SerializedName("realisasi")
    var realisasi: String = "",
    @SerializedName("target")
    var target: String = ""
)

data class Coordinator(
    @SerializedName("actual")
    var `actual`: String = "",
    @SerializedName("coordinator_id")
    var coordinatorId: Int = 0,
    @SerializedName("efectivitas")
    var efectivitas: String = "",
    @SerializedName("name")
    var name: String = "",
    @SerializedName("order")
    var order: Double = 0.0,
    @SerializedName("plan")
    var plan: String = "",
    @SerializedName("realisasi")
    var realisasi: String = "",
    @SerializedName("sales")
    var sales: List<Sales> = listOf()
)

data class Sales(
    @SerializedName("detail")
    var detail: List<SalesDetail> = listOf(),
    @SerializedName("efectivitas")
    var efectivitas: String = "",
    @SerializedName("name")
    var name: String = "",
    @SerializedName("actual")
    var `actual`: String = "",
    @SerializedName("plan")
    var plan: String = "",
    @SerializedName("order")
    var order: Double = 0.0,
    @SerializedName("realisasi")
    var realisasi: String = "",
    @SerializedName("salesman_id")
    var salesmanId: Int = 0
)

data class SalesDetail(
    @SerializedName("actual")
    var `actual`: String = "",
    @SerializedName("dealer_address")
    var dealerAddress: String = "",
    @SerializedName("dealer_code")
    var dealerCode: String = "",
    @SerializedName("dealer_id")
    var dealerId: Int = 0,
    @SerializedName("dealer_name")
    var dealerName: String = "",
    @SerializedName("order")
    var order: Double = 0.0,
    @SerializedName("percentage")
    var percentage: String = "",
    @SerializedName("plan")
    var plan: String = "",
    @SerializedName("realisasi")
    var realisasi: String = "",
    @SerializedName("efectivitas")
    var efectivitas: String = "",
    @SerializedName("salesman_id")
    var salesmanId: Int = 0,
    @SerializedName("salesman_name")
    var salesmanName: String = "",
    @SerializedName("visit")
    var visit: List<VisitEfektivitas> = listOf()
)

data class VisitEfektivitas(
    @SerializedName("actual_visit")
    var actualVisit: String = "",
    @SerializedName("amount")
    var amount: Double = 0.0,
    @SerializedName("efectivitas_persentase")
    var efectivitasPersentase: String = "",
    @SerializedName("id")
    var id: Int = 0,
    @SerializedName("plan_visit")
    var planVisit: String = "",
    @SerializedName("realisasi_persentase")
    var realisasiPersentase: String = ""
)