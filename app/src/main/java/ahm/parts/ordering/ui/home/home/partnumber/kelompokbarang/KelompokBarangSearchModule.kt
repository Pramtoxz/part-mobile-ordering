package ahm.parts.ordering.ui.home.home.partnumber.kelompokbarang

import ahm.parts.ordering.injection.anotation.ViewModelKey
import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class KelompokBarangSearchModule {
    @Binds
    @IntoMap
    @ViewModelKey(KelompokBarangSearchViewModel::class)
    abstract fun bindViewModel(kelompokBarangViewModel: KelompokBarangSearchViewModel): ViewModel
}