package ahm.parts.ordering.data.room.step.infopelanggan

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface InfoPelangganDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(value: InfoPelanggan)

    @Query("DELETE FROM info_pelanggan")
    suspend fun deleteInfoPelanggan()

    @Query("SELECT * from info_pelanggan WHERE idUser = 1 LIMIT 1")
    fun getInfoPelanggan(): LiveData<InfoPelanggan?>
}