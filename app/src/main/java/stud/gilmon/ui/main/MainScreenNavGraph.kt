package stud.gilmon.ui.main

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import stud.gilmon.ui.main.Graph.HOME_GRAPH
import stud.gilmon.ui.main.Graph.PROFILE_GRAPH
import stud.gilmon.ui.main.feed.FeedScreen
import stud.gilmon.ui.main.profile.ProfileScreen
import stud.gilmon.ui.main.support.SupportScreen

object Graph {
    const val HOME_GRAPH = "home"
    const val PROFILE_GRAPH = "profile"
}
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun MainScreenNavGraph(darkTheme:Boolean,navController: NavHostController ,paddingValues: PaddingValues,toggleTheme:()-> Unit){
    NavHost(
        modifier= Modifier.consumeWindowInsets(PaddingValues(50.dp))
            .windowInsetsPadding(
                WindowInsets.safeDrawing.only(
                    WindowInsetsSides.Vertical,
                )),
        navController = navController,
        startDestination = MainScreenDestinations.FeedMain.route,
        route = HOME_GRAPH
    ) {
        composable(route=MainScreenDestinations.FeedMain.route)
        {
            FeedScreen()
        }
        composable(route = MainScreenDestinations.SupportMain.route){
            SupportScreen()
        }
        composable(route = PROFILE_GRAPH){
            ProfileScreen(darkTheme, toggleTheme = toggleTheme)
        }
    }
}
sealed class MainScreenDestinations(val route: String) {
    object FeedMain: MainScreenDestinations(route = "main_profile")
    object SupportMain: MainScreenDestinations(route = "main_support")
}