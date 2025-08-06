package ahm.parts.ordering.ui.home.dealer.salesmanvisit.realisasivisit

import ahm.parts.ordering.injection.anotation.ViewModelKey
import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class RealisasiVisitModule {
    @Binds
    @IntoMap
    @ViewModelKey(RealisasiVisitViewModel::class)
    abstract fun bindViewModel(realisasiVisitViewModel: RealisasiVisitViewModel): ViewModel
}