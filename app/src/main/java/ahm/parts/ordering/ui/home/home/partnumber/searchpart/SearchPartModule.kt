package ahm.parts.ordering.ui.home.home.partnumber.searchpart

import ahm.parts.ordering.injection.anotation.ViewModelKey
import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class SearchPartModule {
    @Binds
    @IntoMap
    @ViewModelKey(SearchPartViewModel::class)
    abstract fun bindViewModel(searchPartViewModel: SearchPartViewModel): ViewModel
}