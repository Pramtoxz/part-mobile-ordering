package ahm.parts.ordering.ui.widget.activity.spk

import ahm.parts.ordering.injection.anotation.ViewModelKey
import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class SPKModule {
    @Binds
    @IntoMap
    @ViewModelKey(SPKViewModel::class)
    abstract fun bindViewModel(spkViewModel: SPKViewModel): ViewModel
}