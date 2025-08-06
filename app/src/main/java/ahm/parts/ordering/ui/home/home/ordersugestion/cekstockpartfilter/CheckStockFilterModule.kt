package ahm.parts.ordering.ui.home.home.ordersugestion.cekstockpartfilter

import ahm.parts.ordering.injection.anotation.ViewModelKey
import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class CheckStockFilterModule {
    @Binds
    @IntoMap
    @ViewModelKey(CheckStockViewModel::class)
    abstract fun bindViewModel(checkStockViewModel: CheckStockViewModel): ViewModel
}