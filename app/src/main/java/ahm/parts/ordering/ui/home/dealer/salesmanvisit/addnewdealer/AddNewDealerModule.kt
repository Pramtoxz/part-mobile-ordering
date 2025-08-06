package ahm.parts.ordering.ui.home.dealer.salesmanvisit.addnewdealer

import ahm.parts.ordering.injection.anotation.ViewModelKey
import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class AddNewDealerModule {
    @Binds
    @IntoMap
    @ViewModelKey(AddNewDealerViewModel::class)
    abstract fun bindViewModel(addNewDealerViewModel: AddNewDealerViewModel): ViewModel
}