package ahm.parts.ordering.data.room.step.infoprospek

import ahm.parts.ordering.data.room.step.produk.Produk
import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface InfoProspekDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(value: Produk)

    @Query("DELETE FROM info_prospek")
    suspend fun deleteInfoProspek()

    @Query("SELECT * from info_prospek WHERE idUser = 1 LIMIT 1")
    fun getInfoProspek(): LiveData<Produk?>
}