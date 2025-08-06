package ahm.parts.ordering.ui.widget.activity.sales

import ahm.parts.ordering.injection.anotation.ViewModelKey
import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ActivitySalesModule {
    @Binds
    @IntoMap
    @ViewModelKey(ActivitySalesViewModel::class)
    abstract fun bindViewModel(activitySalesViewModel: ActivitySalesViewModel): ViewModel
}