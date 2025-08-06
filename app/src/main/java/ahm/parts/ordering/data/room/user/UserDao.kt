package ahm.parts.ordering.data.room.user

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import io.reactivex.Single

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(value: User)

    @Query("DELETE FROM user")
    suspend fun deleteAll()

    @Query("SELECT * from user WHERE idUser = 1 LIMIT 1")
    fun getUser(): LiveData<User?>


    @Query("SELECT COUNT(*) from user WHERE idUser = 1")
    fun getUserCount(): Single<Int>

}