package stud.gilmon

import android.app.Application
import stud.gilmon.di.components.AppComponent
import stud.gilmon.di.components.DaggerAppComponent
import timber.log.Timber


class BaseApplication  : Application() {
    val component: AppComponent by lazy {
        DaggerAppComponent.factory()
            .create(this)
    }

    override fun onCreate() {
        super.onCreate()
      //  Network.init(this)
        Timber.plant(Timber.DebugTree())

    }

}
