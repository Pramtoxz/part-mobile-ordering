package ahm.parts.ordering.ui.home.dealer.salesmanvisit.realisasivisit.planactual

import ahm.parts.ordering.injection.anotation.ViewModelKey
import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class PlanActualRealiasiModule {
    @Binds
    @IntoMap
    @ViewModelKey(PlanActualRealiasiViewModel::class)
    abstract fun bindViewModel(planActualRealiasiViewModel: PlanActualRealiasiViewModel): ViewModel
}