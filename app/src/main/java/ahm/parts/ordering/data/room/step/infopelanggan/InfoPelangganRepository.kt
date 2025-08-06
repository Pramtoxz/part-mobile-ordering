package ahm.parts.ordering.data.room.step.infopelanggan

import androidx.lifecycle.LiveData
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class InfoPelangganRepository(private val dao: InfoPelangganDao) {

    val infoPelanggan: LiveData<InfoPelanggan?> = dao.getInfoPelanggan()

    fun insert(infoPelanggan: InfoPelanggan) = GlobalScope.launch {
        dao.insert(infoPelanggan)
    }

    fun deleteInfoPelanggan() = GlobalScope.launch {
        dao.deleteInfoPelanggan()
    }
}