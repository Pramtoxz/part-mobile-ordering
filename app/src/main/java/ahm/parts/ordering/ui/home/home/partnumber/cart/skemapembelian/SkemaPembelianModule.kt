package ahm.parts.ordering.ui.home.home.partnumber.cart.skemapembelian

import ahm.parts.ordering.injection.anotation.ViewModelKey
import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class SkemaPembelianModule {
    @Binds
    @IntoMap
    @ViewModelKey(SkemaPembelianViewModel::class)
    abstract fun bindViewModel(skemaPembelianViewModel: SkemaPembelianViewModel): ViewModel
}