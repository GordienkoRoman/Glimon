package stud.gilmon.presentation.ui.main

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.dog_observer.viewModelFactory.ViewModelFactory
import com.google.gson.Gson
import stud.gilmon.data.local.entities.UsersEntity
import stud.gilmon.presentation.ui.Screen
import stud.gilmon.presentation.ui.Screen.Companion.KEY_AUTH_USER
import stud.gilmon.presentation.ui.feed.FeedScreen
import stud.gilmon.presentation.ui.profile.ProfileScreen
import stud.gilmon.presentation.ui.support.ContactSupportScreen
import stud.gilmon.presentation.ui.support.SupportScreen

@Composable
fun MainScreenNavGraph(
    darkTheme: Boolean,
    navController: NavHostController,
    paddingValues: PaddingValues,
    user: MutableState<UsersEntity>,
    toggleTheme: () -> Unit,
    viewModelFactory: ViewModelFactory
) {
    NavHost(
        modifier = Modifier
            .consumeWindowInsets(PaddingValues(50.dp))
            .windowInsetsPadding(
                WindowInsets.safeDrawing.only(
                    WindowInsetsSides.Vertical,
                )
            ),
        navController = navController,
        startDestination = Screen.FeedMain.route,
        route = Screen.Home.route
    ) {
        composable(route = Screen.FeedMain.route)
        {
            FeedScreen()
        }
        navigation(route = "Support",
        startDestination = Screen.SupportMain.route){
            composable(route = Screen.SupportMain.route) {
                SupportScreen() {navController.navigate(Screen.ContactSupport.route) }
            }
            composable(route = Screen.ContactSupport.route) {
                ContactSupportScreen(user.value) { navController.navigate(Screen.SupportMain.route) }
            }
        }

        composable(route = "${Screen.Profile.route}/{$KEY_AUTH_USER}") {
            val userJson = it.arguments?.getString(KEY_AUTH_USER) ?: "404"
            user.value = Gson().fromJson(userJson, UsersEntity::class.java)
            //val user = remember { mutableStateOf(Gson().fromJson(userJson, UsersEntity::class.java)) }
            ProfileScreen(
                darkTheme, user = user,
                toggleTheme = toggleTheme,
                viewModelFactory = viewModelFactory
            ) { navController.navigate(Screen.FeedMain.route) }
        }
    }
}

