package ahm.parts.ordering

import ahm.parts.ordering.injection.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

class Core : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().create(this).build()
    }

}