package ahm.parts.ordering.ui.home.dealer.competitor

import ahm.parts.ordering.injection.anotation.ViewModelKey
import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class CompetitorModule {
    @Binds
    @IntoMap
    @ViewModelKey(CompetitorViewModel::class)
    abstract fun bindViewModel(competitorViewModel: CompetitorViewModel): ViewModel
}