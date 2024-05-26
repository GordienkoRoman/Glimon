package stud.gilmon.di.modules

import androidx.lifecycle.ViewModel
import com.example.dog_observer.viewModelFactory.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import stud.gilmon.presentation.ui.profile.ProfileViewModel
import stud.gilmon.presentation.ui.profile.coupons.CouponsViewModel
import stud.gilmon.presentation.ui.profile.reviews.ReviewsViewModel
import stud.gilmon.presentation.ui.profile.settings.SettingsViewModel

@Module
interface ProfileViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(CouponsViewModel::class)
    fun bindCouponsViewModel(viewModel: CouponsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ReviewsViewModel::class)
    fun bindReviewsViewModel(viewModel: ReviewsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ProfileViewModel::class)
    fun bindProfileViewModel(viewModel: ProfileViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SettingsViewModel::class)
    fun bindSettingsViewModel(viewModel: SettingsViewModel): ViewModel
}