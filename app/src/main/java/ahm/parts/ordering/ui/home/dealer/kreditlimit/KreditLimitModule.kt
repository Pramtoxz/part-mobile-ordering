package ahm.parts.ordering.ui.home.dealer.kreditlimit

import ahm.parts.ordering.injection.anotation.ViewModelKey
import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class KreditLimitModule {
    @Binds
    @IntoMap
    @ViewModelKey(KreditLimitViewModel::class)
    abstract fun bindViewModel(kreditLimitViewModel: KreditLimitViewModel): ViewModel
}