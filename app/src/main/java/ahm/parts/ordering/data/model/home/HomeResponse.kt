package ahm.parts.ordering.data.model.home


import com.google.gson.annotations.SerializedName

data class HomeResponse(
    @SerializedName("activity")
    var aktifitas: ArrayList<Aktifitas> = ArrayList(),
    @SerializedName("inbox_badge")
    var inboxBadge: Boolean = false,
    @SerializedName("info")
    var info: String = "",
    @SerializedName("prospek")
    var prospek: Prospek = Prospek(),
    @SerializedName("rank")
    var rank: String = "",
    @SerializedName("sales")
    var sales: DashboardSales = DashboardSales(),
    @SerializedName("spk")
    var spk: SPK = SPK(),
    @SerializedName("tugas_terunda")
    var pendingActivity: TugasTertunda = TugasTertunda()
)