package stud.gilmon.presentation.ui.profile

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import stud.gilmon.R
import stud.gilmon.presentation.theme.BackGroundDark2

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(darkTheme:Boolean, navController: NavHostController = rememberNavController(),toggleTheme:()-> Unit) {
    Scaffold(topBar = { ProfileTopAppBar(navController = navController) },
        contentWindowInsets = WindowInsets.systemBars.only(WindowInsetsSides.Bottom)
    ) {
        ProfileNavGraph(darkTheme,navController = navController, paddingValues = it, toggleTheme = toggleTheme)
    }

}

@Composable
fun ProfileTopAppBar(navController: NavController) {
    var selectedTab by remember { mutableStateOf(R.string.profile_coupons) }
    val tabs = ProfileTab.values()
   // val navBackStackEntry by navController.currentBackStackEntryAsState()
  //  val currentRoute = navBackStackEntry?.destination?.route
    NavigationBar(
        Modifier.height(50.dp),
        containerColor = BackGroundDark2,
    ) {
        tabs.forEach { tab ->
            NavigationBarItem(
                modifier = Modifier.background(MaterialTheme.colorScheme.onBackground),
                colors = NavigationBarItemDefaults.colors(indicatorColor = MaterialTheme.colorScheme.onBackground,
                ),
                icon = { },
                label = { Text(text = stringResource(tab.title), color = if(tab.title ==selectedTab) MaterialTheme.colorScheme.tertiary else Color.White) },
                selected = tab.title ==selectedTab,
                onClick = {
                    selectedTab = tab.title
                    val route = when (tab) {
                        ProfileTab.SETTINGS -> ProfileDestinations.SettingsProfile.route
                        ProfileTab.COUPONS -> ProfileDestinations.CouponsProfile.route
                        ProfileTab.REVIEWS -> ProfileDestinations.ReviewsProfile.route
                    }
                    navController.navigate(route = route){
                        launchSingleTop=true
                        popUpTo(ProfileDestinations.CouponsProfile.route){
                            saveState=true
                        }
                        restoreState=true
                    }
                },
            )
        }
    }
}

enum class ProfileTab(
    @StringRes val title: Int,
) {
    COUPONS(R.string.profile_coupons),
    REVIEWS(R.string.profile_reviews),
    SETTINGS(R.string.profile_settings);
}