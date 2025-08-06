package ahm.parts.ordering.data.room.step.produk

import androidx.lifecycle.LiveData
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ProdukRepository(private val dao: ProdukDao) {

    val infoPelanggan: LiveData<Produk?> = dao.getProduk()

    fun insert(produk: Produk) = GlobalScope.launch {
        dao.insert(produk)
    }

    fun deleteProduk() = GlobalScope.launch {
        dao.deleteProduk()
    }
}