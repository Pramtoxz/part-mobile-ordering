package ahm.parts.ordering.data.room.step.infoprospek

import ahm.parts.ordering.data.room.step.produk.ProdukDao
import ahm.parts.ordering.data.room.step.produk.Produk
import androidx.lifecycle.LiveData
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class InfoProspekRepository(private val dao: ProdukDao) {

    val infoPelanggan: LiveData<Produk?> = dao.getProduk()

    fun insert(infoProspek: Produk) = GlobalScope.launch {
        dao.insert(infoProspek)
    }

    fun deleteInfoProspekn() = GlobalScope.launch {
        dao.deleteProduk()
    }
}