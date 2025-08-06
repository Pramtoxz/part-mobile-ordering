package ahm.parts.ordering.data.room.user

import androidx.lifecycle.LiveData
import io.reactivex.Single
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

// Declares the DAO as a private property in the constructor. Pass in the DAO
// instead of the whole database, because you only need access to the DAO
class UserRepository(private val userDao: UserDao) {

    // Room executes all queries on a separate thread.
    // Observed LiveData will notify the observer when the data has changed.
    val user: LiveData<User?> = userDao.getUser()

    fun getUserCount(): Single<Int> {
        return userDao.getUserCount()
    }

    // The suspend modifier tells the compiler that this must be called from a
    // coroutine or another suspend function.
    fun insert(user: User) = GlobalScope.launch {
        userDao.insert(user)
    }

    fun deleteAll() = GlobalScope.launch {
        userDao.deleteAll()
    }

}