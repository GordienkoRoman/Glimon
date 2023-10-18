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
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import stud.gilmon.MainActivity
import stud.gilmon.MainViewModel
import stud.gilmon.data.remote.network.AuthorizationInterceptor
import stud.gilmon.data.remote.userApi.GithubApi
import stud.gilmon.data.remote.userApi.MailApi
import stud.gilmon.presentation.ui.login.LoginViewModel
import stud.gilmon.presentation.ui.profile.ProfileViewModel
import stud.gilmon.presentation.ui.profile.settings.SettingsViewModel
import timber.log.Timber
import javax.inject.Scope

@Scope
annotation class AppScope

@AppScope
@Component(
    modules = [AppModule::class,
        ViewModelModule::class,
        DataBaseModule::class,
        DataStoreModule::class,
        DispatcherModule::class]
)
interface AppComponent {
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
    fun provideOkhttpClient(context: Context): OkHttpClient {
        return OkHttpClient.Builder()
            .addNetworkInterceptor(
                HttpLoggingInterceptor {
                    Timber.tag("NetworkInit").d(it)
                }
                    .setLevel(HttpLoggingInterceptor.Level.BODY)
            )
            .addNetworkInterceptor(AuthorizationInterceptor())
            .build()
    }

    @AppScope
    @Provides
    fun provideGithubApiFactService(okHttpClient: OkHttpClient): GithubApi {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
        return retrofit.create(GithubApi::class.java)
    }

    @AppScope
    @Provides
    fun provideMailApiFactService(okHttpClient: OkHttpClient): MailApi {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://www.appsmail.ru/platform/api")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
        return retrofit.create(MailApi::class.java)
    }


}

@Module
interface ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(SettingsViewModel::class)
    fun bindSettingsViewModel(viewModel: SettingsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ProfileViewModel::class)
    fun bindProfileViewModel(viewModel: ProfileViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    fun bindLoginViewModel(viewModel: LoginViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    fun bindMainViewModel(viewModel: MainViewModel): ViewModel
}