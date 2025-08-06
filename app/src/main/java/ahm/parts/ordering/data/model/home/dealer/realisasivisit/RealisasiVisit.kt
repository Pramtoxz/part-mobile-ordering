package ahm.parts.ordering.data.model.home.dealer.realisasivisit


import com.google.gson.annotations.SerializedName

data class RealisasiVisit(
    @SerializedName("actual")
    var `actual`: String = "",
    @SerializedName("data")
    var `data`: List<Data> = listOf(),
    @SerializedName("realisasi")
    var realisasi: String = "",
    @SerializedName("page")
    var page: Int = 0,
    @SerializedName("target")
    var target: String = ""
)

data class Data(
    @SerializedName("actual")
    var `actual`: String = "",
    @SerializedName("dealer_id")
    var dealerId: Int = 0,
    @SerializedName("detail")
    var detail: List<RealisasiDetail> = listOf(),
    @SerializedName("name")
    var name: String = "",
    @SerializedName("code")
    var code: String = "",
    @SerializedName("address")
    var address: String = "",
    @SerializedName("plan")
    var plan: String = "",
    @SerializedName("realisasi")
    var realisasi: String = ""
)

data class RealisasiDetail(
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
    @SerializedName("percentage")
    var percentage: Int = 0,
    @SerializedName("plan")
    var plan: String = "",
    @SerializedName("realisasi")
    var realisasi: String = "",
    @SerializedName("salesman_id")
    var salesmanId: Int = 0,
    @SerializedName("salesman_name")
    var salesmanName: String = "",
    @SerializedName("visit")
    var visit: List<Visit> = listOf()
)

data class Visit(
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