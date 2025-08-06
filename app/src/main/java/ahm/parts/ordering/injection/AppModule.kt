package ahm.parts.ordering.injection


import ahm.parts.ordering.api.ApiResponse
import ahm.parts.ordering.data.Session
import ahm.parts.ordering.helper.ToastUtil
import ahm.parts.ordering.ui.base.ViewModelFactory
import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
abstract class AppModule {

    @Binds
    abstract fun provideContext(application: Application): Context

    @Binds
    abstract fun bindViewModelFactory(viewModelFactory: ViewModelFactory): ViewModelProvider.Factory


    @Module
    companion object {
        @JvmStatic
        @Provides
        @Singleton
        fun provideSession(context: Context) = Session(context)

        @JvmStatic
        @Provides
        fun provideToastUtil(context: Context): ToastUtil {
            return ToastUtil(context)
        }

        @JvmStatic
        @Provides
        fun provideApiResponse() = ApiResponse()

    }
}
