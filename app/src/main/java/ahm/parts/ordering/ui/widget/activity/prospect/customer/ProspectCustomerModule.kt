package ahm.parts.ordering.ui.widget.activity.prospect.customer

import ahm.parts.ordering.injection.anotation.ViewModelKey
import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ProspectCustomerModule {
    @Binds
    @IntoMap
    @ViewModelKey(ProspectCustomerViewModel::class)
    abstract fun bindViewModel(prospectCustomerViewModel: ProspectCustomerViewModel): ViewModel
}