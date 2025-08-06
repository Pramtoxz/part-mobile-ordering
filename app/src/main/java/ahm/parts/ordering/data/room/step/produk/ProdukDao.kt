package ahm.parts.ordering.data.room.step.produk

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ProdukDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(value: Produk)

    @Query("DELETE FROM produk")
    suspend fun deleteProduk()

    @Query("SELECT * from produk WHERE idUser = 1 LIMIT 1")
    fun getProduk(): LiveData<Produk?>
}