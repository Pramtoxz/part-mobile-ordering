package ahm.parts.ordering.ui.auth.forgotpassword

import ahm.parts.ordering.injection.anotation.ViewModelKey
import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ForgotPasswordModule {
    @Binds
    @IntoMap
    @ViewModelKey(ForgotPasswordViewModel::class)
    abstract fun bindViewModel(forgotPasswordViewModel: ForgotPasswordViewModel): ViewModel
}