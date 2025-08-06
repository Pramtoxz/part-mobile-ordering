package ahm.parts.ordering.data.model.home.dealer.collection


import com.google.gson.annotations.Expose
import ahm.parts.ordering.data.model.home.dealer.competitor.Photo
import ahm.parts.ordering.data.model.home.dealer.efektivitas.Coordinator
import com.google.gson.annotations.SerializedName

data class Collection1(
    @SerializedName("tgltransaksi")
    var tglTransaksi: String = "",

    @SerializedName("notransaksi")
    val noTransaksi: String? = null,

    @SerializedName("namatoko")
    var namaToko: String = "",

    @SerializedName("jumlahitem")
    var jumlahItem: String = "",

    @SerializedName("jumlahpcs")
    var jumlahPcs: String = "",

    @SerializedName("totalbayar")
    var totalBayar: Double = 0.0,

    @SerializedName("totalPiutang")
    val totalHutang: Double = 0.0,

    @SerializedName("Bayar")
    val bayar: Double = 0.0,

    @SerializedName("codedealer")
    var codeDealer: String = "",

    @SerializedName("id_role")
    var idRole: String = "",

    @SerializedName("id_user")
    var idUser: Int = 0,

    @SerializedName("photo")
    var photo: List<Photo> = listOf(),

    var isSelected: Boolean = false
)

data class CollectionTotal(
    @SerializedName("totalPiutang1")
    val totalHutang1: Double = 0.0
)

data class Photo(
    @SerializedName("photo")
    var photo: String = ""
)

fun getCollectionSelected(it: List<String>?): ArrayList<Collection1> {
    val items = ArrayList<Collection1>()
    it?.forEach {
        items.add(Collection1(noTransaksi = it, isSelected = true))
    }
    return items
}