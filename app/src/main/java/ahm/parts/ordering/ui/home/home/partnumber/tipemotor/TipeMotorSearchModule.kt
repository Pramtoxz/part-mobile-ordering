package ahm.parts.ordering.ui.home.home.partnumber.tipemotor

import ahm.parts.ordering.injection.anotation.ViewModelKey
import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class TipeMotorSearchModule {
    @Binds
    @IntoMap
    @ViewModelKey(TipeMotorSearchViewModel::class)
    abstract fun bindViewModel(tipeMotorSearchViewModelViewModel: TipeMotorSearchViewModel): ViewModel
}