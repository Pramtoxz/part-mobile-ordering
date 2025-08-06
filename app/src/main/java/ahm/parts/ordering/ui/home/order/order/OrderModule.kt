package ahm.parts.ordering.ui.home.order.order

import ahm.parts.ordering.injection.anotation.ViewModelKey
import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class OrderModule {
    @Binds
    @IntoMap
    @ViewModelKey(OrderViewModel::class)
    abstract fun bindViewModel(trackingOrderViewModel: OrderViewModel): ViewModel
}