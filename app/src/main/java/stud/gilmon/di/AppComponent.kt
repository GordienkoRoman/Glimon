package stud.gilmon.di

import android.content.Context
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.lifecycle.ViewModel
import com.example.dog_observer.viewModelFactory.ViewModelKey
import dagger.Binds
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import net.openid.appauth.AuthorizationService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import stud.gilmon.BaseApplication
import stud.gilmon.MainActivity
import stud.gilmon.data.github.GithubApi
import stud.gilmon.data.network.AuthorizationFailedInterceptor
import stud.gilmon.data.network.AuthorizationInterceptor
import stud.gilmon.data.network.Network
import stud.gilmon.data.oauth.TokenStorage
import stud.gilmon.presentation.ui.profile.settings.SettingsViewModel
import timber.log.Timber
import javax.inject.Scope
import javax.inject.Singleton

@Scope
annotation class AppScope

@AppScope
@Component(modules = [AppModule::class,
    ViewModelModule::class])
interface AppComponent  {
    @OptIn(ExperimentalMaterial3Api::class)
    fun inject(mainActivity: MainActivity)
    @Component.Factory
    interface Factory {

        fun create(
            @BindsInstance context: Context
        ): AppComponent
    }
}

@Module
class AppModule {

    @AppScope
    @Provides
    fun provideOkhttpClient(context:Context): OkHttpClient {
        return  OkHttpClient.Builder()
            .addNetworkInterceptor(
                HttpLoggingInterceptor {
                    Timber.tag("NetworkInit").d(it)
                }
                    .setLevel(HttpLoggingInterceptor.Level.BODY)
            )
            .addNetworkInterceptor(AuthorizationInterceptor())
            .addNetworkInterceptor(AuthorizationFailedInterceptor(AuthorizationService(context), TokenStorage))
            .build()
    }
    @AppScope
    @Provides
    fun provideDogApiFactService(okHttpClient: OkHttpClient): GithubApi {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
        return retrofit.create(GithubApi::class.java)
    }


}

@Module
interface ViewModelModule{
    @Binds
    @IntoMap
    @ViewModelKey(SettingsViewModel::class)
    fun bindSettingsViewModel(viewModel: SettingsViewModel):ViewModel
}