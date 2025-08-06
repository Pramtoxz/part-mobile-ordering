package ahm.parts.ordering.data.model.home.dealer.efektivitas


import com.google.gson.annotations.SerializedName

data class EfektivitasVisit(
    @SerializedName("actual")
    var `actual`: String = "",
    @SerializedName("data")
    var `data`: List<EfData> = listOf(),
    @SerializedName("efectivitas")
    var efectivitas: String = "",
    @SerializedName("amount_total")
    var amountTotal: Double = 0.0,
    @SerializedName("page")
    var page: Int = 0,
    @SerializedName("realisasi")
    var realisasi: String = "",
    @SerializedName("target")
    var target: String = ""
)

data class EfData(
    @SerializedName("actual")
    var `actual`: String = "",
    @SerializedName("dealer_id")
    var dealerId: Int = 0,
    @SerializedName("detail")
    var detail: List<EfDetail> = listOf(),
    @SerializedName("efectivitas")
    var efectivitas: String = "",
    @SerializedName("name")
    var name: String = "",
    @SerializedName("code")
    var code: String = "",
    @SerializedName("address")
    var address: String = "",
    @SerializedName("order")
    var order: Double = 0.0,
    @SerializedName("plan")
    var plan: String = "",
    @SerializedName("realisasi")
    var realisasi: String = ""
)

data class EfDetail(
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
    var visit: List<EfVisit> = listOf()
)

data class EfVisit(
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