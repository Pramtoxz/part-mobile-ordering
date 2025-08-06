package ahm.parts.ordering.ui.home.profile

import ahm.parts.ordering.injection.anotation.ViewModelKey
import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ProfileModule {
    @Binds
    @IntoMap
    @ViewModelKey(ProfileViewModel::class)
    abstract fun bindViewModel(profileViewModel: ProfileViewModel): ViewModel
}