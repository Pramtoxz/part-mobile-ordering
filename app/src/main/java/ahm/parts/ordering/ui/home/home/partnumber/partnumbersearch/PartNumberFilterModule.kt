package ahm.parts.ordering.ui.home.home.partnumber.partnumbersearch

import ahm.parts.ordering.injection.anotation.ViewModelKey
import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class PartNumberFilterModule {
    @Binds
    @IntoMap
    @ViewModelKey(PartNumberFilterViewModel::class)
    abstract fun bindViewModel(kelompokBarangViewModel: PartNumberFilterViewModel): ViewModel
}