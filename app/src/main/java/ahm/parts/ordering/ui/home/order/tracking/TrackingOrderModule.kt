package ahm.parts.ordering.ui.home.order.tracking

import ahm.parts.ordering.injection.anotation.ViewModelKey
import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class TrackingOrderModule {
    @Binds
    @IntoMap
    @ViewModelKey(TrackingOrderViewModel::class)
    abstract fun bindViewModel(trackingOrderViewModel: TrackingOrderViewModel): ViewModel
}