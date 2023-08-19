package stud.gilmon.ui.main

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.add
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import stud.gilmon.R
import stud.gilmon.ui.components.CustomDragHandle
import stud.gilmon.ui.components.CustomNavigationBar
import stud.gilmon.ui.main.login.LoginScreen
import stud.gilmon.ui.main.profile.ProfileDestinations
import stud.gilmon.ui.main.profile.ProfileTab

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(darkTheme:Boolean,navController: NavHostController = rememberNavController(),toggleTheme:()-> Unit){
    BottomSheetScaffold(
        sheetDragHandle = {CustomDragHandle()},
        sheetContent = {
            LoginScreen()
        },
    ) {
        Scaffold(bottomBar = { MainBottomAppBar(navController = navController) },
            contentWindowInsets = WindowInsets.systemBars.only(WindowInsetsSides.Bottom)
        ) {
            MainScreenNavGraph(darkTheme,navController,it, toggleTheme = toggleTheme)
        }
    }

}

@Composable
fun MainBottomAppBar(navController: NavController) {
    var selectedTab by remember { mutableStateOf(0) }
    val tabs = MainTab.values()
    CustomNavigationBar(
        Modifier.windowInsetsBottomHeight(
            WindowInsets.navigationBars.add(WindowInsets(bottom = 80.dp))
        )
    ) {
        tabs.forEach { tab ->
            NavigationBarItem(
                icon = { Icon(imageVector = tab.icon, contentDescription = null) },
                colors = NavigationBarItemDefaults.colors(

                ),
                selected = tab.title ==selectedTab,
                onClick = {
                    selectedTab = tab.title
                    val route = when (tab) {
                        MainTab.PROFILE -> Graph.PROFILE_GRAPH
                        MainTab.SUPPORT -> MainScreenDestinations.SupportMain.route
                        MainTab.FEED -> MainScreenDestinations.FeedMain.route
                    }
                    navController.navigate(route = route)
                },
            )
        }
    }
}
enum class MainTab(
    @StringRes val title: Int,
    val icon: ImageVector
) {
    FEED(R.string.main_feed, Icons.Filled.Search),
    SUPPORT(R.string.main_support, Icons.Filled.ThumbUp),
    PROFILE(R.string.main_profile, Icons.Filled.AccountBox);
   // EXTRA( R.string.main_profile, Icons.Filled.AddCircle);

    companion object {
        fun getTabFromResource(@StringRes resource: Int): MainTab {
            return when (resource) {
                R.string.main_feed -> FEED
                R.string.main_support-> SUPPORT
                R.string.main_profile-> PROFILE
                else ->  PROFILE
            }
        }
    }
}