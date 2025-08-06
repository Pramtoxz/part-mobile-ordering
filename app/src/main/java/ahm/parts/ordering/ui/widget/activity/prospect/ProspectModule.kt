package ahm.parts.ordering.ui.widget.activity.prospect

import ahm.parts.ordering.injection.anotation.ViewModelKey
import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ProspectModule {
    @Binds
    @IntoMap
    @ViewModelKey(ProspectViewModel::class)
    abstract fun bindViewModel(prospectViewModel: ProspectViewModel): ViewModel
}