package ahm.parts.ordering.ui.home.home.partnumber.cart

import ahm.parts.ordering.injection.anotation.ViewModelKey
import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class CartModule {
    @Binds
    @IntoMap
    @ViewModelKey(CartViewModel::class)
    abstract fun bindViewModel(cartViewModel: CartViewModel): ViewModel
}