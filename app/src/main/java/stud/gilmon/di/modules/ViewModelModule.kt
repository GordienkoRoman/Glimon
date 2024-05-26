package stud.gilmon.di.modules

import androidx.lifecycle.ViewModel
import com.example.dog_observer.viewModelFactory.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import stud.gilmon.MainViewModel
import stud.gilmon.presentation.ui.feed.FeedItemScreen.FeedItemViewModel
import stud.gilmon.presentation.ui.feed.FeedViewModel
import stud.gilmon.presentation.ui.login.LoginViewModel

@Module
interface ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    fun bindLoginViewModel(viewModel: LoginViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    fun bindMainViewModel(viewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FeedViewModel::class)
    fun bindFeedViewModel(viewModel: FeedViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(FeedItemViewModel::class)
    fun bindFeedItemViewModel(viewModel: FeedItemViewModel): ViewModel


}