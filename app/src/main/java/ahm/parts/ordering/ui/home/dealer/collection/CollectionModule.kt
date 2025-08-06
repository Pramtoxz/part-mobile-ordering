package ahm.parts.ordering.ui.home.dealer.collection

import ahm.parts.ordering.injection.anotation.ViewModelKey
import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class CollectionModule {
    @Binds
    @IntoMap
    @ViewModelKey(CollectionViewModel::class)
    abstract fun bindViewModel(collectionViewModel: CollectionViewModel): ViewModel
}