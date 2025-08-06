package ahm.parts.ordering.data.model.home.dealer.realisasivisit


import com.google.gson.annotations.SerializedName

data class RealisasiCoordinatorManager(
    @SerializedName("actual")
    var `actual`: String = "",
    @SerializedName("data")
    var `data`: List<Coordinator> = listOf(),
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
    @SerializedName("name")
    var name: String = "",
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
    @SerializedName("name")
    var name: String = "",
    @SerializedName("plan")
    var plan: String = "",
    @SerializedName("actual")
    var actual: String = "",
    @SerializedName("realisasi")
    var realisasi: String = "",
    @SerializedName("salesman_id")
    var salesmanId: Int = 0
)

data class SalesDetail(
    @SerializedName("dealer_address")
    var dealerAddress: String = "",
    @SerializedName("dealer_code")
    var dealerCode: String = "",
    @SerializedName("dealer_id")
    var dealerId: Int = 0,
    @SerializedName("dealer_name")
    var dealerName: String = "",
    @SerializedName("percentage")
    var percentage: Int = 0,
    @SerializedName("salesman_id")
    var salesmanId: Int = 0,
    @SerializedName("salesman_name")
    var salesmanName: String = "",
    @SerializedName("plan")
    var plan: String = "",
    @SerializedName("actual")
    var actual: String = "",
    @SerializedName("realisasi")
    var realisasi: String = "",
    @SerializedName("visit")
    var visit: List<VisitSalesman> = listOf()
)

data class VisitSalesman(
    @SerializedName("actual_visit")
    var actualVisit: String = "",
    @SerializedName("efectivitas_persentase")
    var efectivitasPersentase: String = "",
    @SerializedName("id")
    var id: Int = 0,
    @SerializedName("plan_visit")
    var planVisit: String = "",
    @SerializedName("realisasi_persentase")
    var realisasiPersentase: String = ""
)