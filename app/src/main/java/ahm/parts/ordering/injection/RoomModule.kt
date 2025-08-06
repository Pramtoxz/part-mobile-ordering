package ahm.parts.ordering.injection

import ahm.parts.ordering.data.room.AppDatabase
import ahm.parts.ordering.data.room.user.UserRepository
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
abstract class RoomModule {

    @Module
    companion object {

        @JvmStatic
        @Singleton
        @Provides
        fun provideDatabase(context: Context) = AppDatabase.getDatabase(context)

        @JvmStatic
        @Singleton
        @Provides
        fun provideUserRepository(database: AppDatabase) = UserRepository(database.userDao())

    }
}
