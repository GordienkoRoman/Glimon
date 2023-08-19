package stud.gilmon.di

import android.app.Application
import android.content.Context
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides
import stud.gilmon.BaseApplication
import stud.gilmon.MainActivity
import javax.inject.Scope
import javax.inject.Singleton

@Scope
annotation class AppScope

@[AppScope Component(modules = [AppModule::class])]
interface AppComponent  {
    //fun inject(application: Application)

    @Component.Factory
    interface Factory {

        fun create(
            @BindsInstance context: Context
        ): AppComponent
    }
}

@Module
object AppModule {

    @Singleton
    @Provides
    fun provideApplication(app: Context): BaseApplication {
        return app as BaseApplication
    }

}