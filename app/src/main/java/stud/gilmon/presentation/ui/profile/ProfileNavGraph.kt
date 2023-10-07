package stud.gilmon.presentation.ui.profile

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.dog_observer.viewModelFactory.ViewModelFactory
import stud.gilmon.presentation.ui.main.Graph
import stud.gilmon.presentation.ui.profile.coupons.CouponsProfile
import stud.gilmon.presentation.ui.profile.reviews.ReviewsProfile
import stud.gilmon.presentation.ui.profile.settings.SettingsProfile

@Composable
fun ProfileNavGraph(darkTheme:Boolean,
                    lazyListStateList: List<LazyListState>,
                    navController: NavHostController,
                    paddingValues: PaddingValues,
                    toggleTheme:()-> Unit,
                    viewModelFactory: ViewModelFactory
) {

    NavHost(
        navController = navController,
        route = Graph.PROFILE_GRAPH,
        startDestination = ProfileDestinations.CouponsProfile.route
    )
    {
        composable(route = ProfileDestinations.CouponsProfile.route) {
            CouponsProfile(lazyListStateList[0])
        }
        composable(route = ProfileDestinations.ReviewsProfile.route) {
            ReviewsProfile()
        }
        composable(route = ProfileDestinations.SettingsProfile.route) {
            SettingsProfile(darkTheme, toggleTheme = toggleTheme,viewModelFactory = viewModelFactory)
        }
    }
}


sealed class ProfileDestinations(val route: String) {
    object CouponsProfile : ProfileDestinations(route = "profile_coupons")
    object SettingsProfile : ProfileDestinations(route = "profile_settings")
    object ReviewsProfile : ProfileDestinations(route = "profile_reviews")
}
