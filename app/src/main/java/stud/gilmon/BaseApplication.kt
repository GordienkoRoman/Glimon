package stud.gilmon

import android.app.Application
import stud.gilmon.data.network.Network
import timber.log.Timber


class BaseApplication  : Application() {
    override fun onCreate() {
        super.onCreate()
        Network.init(this)
        Timber.plant(Timber.DebugTree())
    }
}
