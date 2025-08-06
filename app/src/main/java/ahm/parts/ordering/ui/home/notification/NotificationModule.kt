package ahm.parts.ordering.ui.home.notification

import ahm.parts.ordering.injection.anotation.ViewModelKey
import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class NotificationModule {
    @Binds
    @IntoMap
    @ViewModelKey(NotificationViewModel::class)
    abstract fun bindViewModel(notificationViewModel: NotificationViewModel): ViewModel
}