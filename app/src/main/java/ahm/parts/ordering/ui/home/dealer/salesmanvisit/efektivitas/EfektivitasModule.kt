package ahm.parts.ordering.ui.home.dealer.salesmanvisit.efektivitas

import ahm.parts.ordering.injection.anotation.ViewModelKey
import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class EfektivitasModule {
    @Binds
    @IntoMap
    @ViewModelKey(EfektivitasViewModel::class)
    abstract fun bindViewModel(efektivitasViewModel: EfektivitasViewModel): ViewModel
}