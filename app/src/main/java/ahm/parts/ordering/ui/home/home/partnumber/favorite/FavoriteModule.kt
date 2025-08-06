package ahm.parts.ordering.ui.home.home.partnumber.favorite

import ahm.parts.ordering.injection.anotation.ViewModelKey
import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class FavoriteModule {
    @Binds
    @IntoMap
    @ViewModelKey(FavoriteViewModel::class)
    abstract fun bindViewModel(favoriteViewModel: FavoriteViewModel): ViewModel
}