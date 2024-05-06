package stud.gilmon.di.components

import android.content.Context
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.paging.PagingSourceFactory
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import stud.gilmon.MainActivity
import stud.gilmon.data.remote.UnsplashApi
import stud.gilmon.data.remote.network.AuthorizationInterceptor
import stud.gilmon.data.remote.paging.UnsplashPhotosPagingSource
import stud.gilmon.data.remote.userApi.GithubApi
import stud.gilmon.data.remote.userApi.MailApi
import stud.gilmon.di.modules.DataBaseModule
import stud.gilmon.di.modules.DataStoreModule
import stud.gilmon.di.modules.DispatcherModule
import stud.gilmon.di.modules.ViewModelModule
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

    fun profileScreenComponentFactory():ProfileScreenComponent.Factory
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
                    Timber.tag("Network").d(it)
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
            .baseUrl("http://www.appsmail.ru/platform/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
        return retrofit.create(MailApi::class.java)
    }
    @AppScope
    @Provides
    fun provideUnsplashImagesService(okHttpClient: OkHttpClient): UnsplashApi {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.unsplash.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient)
            .build()
        return retrofit.create(UnsplashApi::class.java)
    }


}

