package ahm.parts.ordering.ui.home.home.ordersugestion.ordersugestion

import ahm.parts.ordering.injection.anotation.ViewModelKey
import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class SuggestOrderModule {
    @Binds
    @IntoMap
    @ViewModelKey(SuggestOrderViewModel::class)
    abstract fun bindViewModel(suggestOrderViewModel: SuggestOrderViewModel): ViewModel
}