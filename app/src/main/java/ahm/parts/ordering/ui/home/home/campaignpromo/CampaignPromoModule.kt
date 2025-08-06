package ahm.parts.ordering.ui.home.home.campaignpromo

import ahm.parts.ordering.injection.anotation.ViewModelKey
import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class CampaignPromoModule {
    @Binds
    @IntoMap
    @ViewModelKey(CampaignPromoViewModel::class)
    abstract fun bindViewModel(homeCampaignPromoViewModel: CampaignPromoViewModel): ViewModel
}