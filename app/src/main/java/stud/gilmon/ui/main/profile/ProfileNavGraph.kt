package stud.gilmon.ui.main.profile

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import stud.gilmon.ui.main.Graph
import stud.gilmon.ui.main.profile.coupons.CouponsProfile
import stud.gilmon.ui.main.profile.reviews.ReviewsProfile
import stud.gilmon.ui.main.profile.settings.SettingsProfile

@Composable
fun ProfileNavGraph(darkTheme:Boolean, navController: NavHostController, paddingValues: PaddingValues,toggleTheme:()-> Unit) {

    NavHost(
        navController = navController,
        route =Graph.PROFILE_GRAPH,
        startDestination = ProfileDestinations.CouponsProfile.route
    )
    {
        composable(route = ProfileDestinations.CouponsProfile.route) {
            CouponsProfile()
        }
        composable(route = ProfileDestinations.ReviewsProfile.route) {
            ReviewsProfile()
        }
        composable(route = ProfileDestinations.SettingsProfile.route) {
            SettingsProfile(darkTheme, toggleTheme = toggleTheme)
        }
    }
}


sealed class ProfileDestinations(val route: String) {
    object CouponsProfile : ProfileDestinations(route = "profile_coupons")
    object SettingsProfile : ProfileDestinations(route = "profile_settings")
    object ReviewsProfile : ProfileDestinations(route = "profile_reviews")
}
