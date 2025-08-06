package ahm.parts.ordering.ui.home.dealer.salesmanvisit.visit

import ahm.parts.ordering.injection.anotation.ViewModelKey
import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class VisitModule {
    @Binds
    @IntoMap
    @ViewModelKey(VisitViewModel::class)
    abstract fun bindViewModel(visitViewModel: VisitViewModel): ViewModel
}