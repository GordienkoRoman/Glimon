package stud.gilmon.presentation.ui.profile

import androidx.annotation.StringRes
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.dog_observer.viewModelFactory.ViewModelFactory
import stud.gilmon.R
import stud.gilmon.presentation.theme.BackGroundDark2

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    darkTheme: Boolean,
    login: String,
    navController: NavHostController = rememberNavController(),
    toggleTheme: () -> Unit,
    viewModelFactory: ViewModelFactory
) {
    val viewModel: ProfileViewModel = viewModel(factory = viewModelFactory)
    val lazyListStateList: List<LazyListState> = listOf(
        rememberLazyListState(),
        rememberLazyListState(),
        rememberLazyListState()
    )
    Scaffold(
        content = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(it)
            ) {
                Scaffold(
                    modifier = Modifier.padding(top = TOP_BAR_HEIGHT),
                    topBar = { ProfileTopNavigationBar(navController = navController) },
                    contentWindowInsets = WindowInsets.systemBars.only(WindowInsetsSides.Bottom)
                ) {
                    ProfileNavGraph(
                        darkTheme,
                        lazyListStateList,
                        navController = navController,
                        paddingValues = it,
                        toggleTheme = toggleTheme,
                        viewModelFactory = viewModelFactory
                    )
                }
                ProfileTopBar(lazyListState = lazyListStateList[0])
            }
        }
    )


}

@Composable
fun ProfileTopNavigationBar(navController: NavController) {
    var selectedTab by remember { mutableStateOf(R.string.profile_coupons) }
    val tabs = ProfileTab.values()
    // val navBackStackEntry by navController.currentBackStackEntryAsState()
    //  val currentRoute = navBackStackEntry?.destination?.route
    NavigationBar(
        Modifier.height(50.dp)
            ,
        containerColor = BackGroundDark2,
    ) {
        tabs.forEach { tab ->
            NavigationBarItem(
                modifier = Modifier.background(MaterialTheme.colorScheme.onBackground),
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = MaterialTheme.colorScheme.onBackground,
                ),
                icon = { },
                label = {
                    Text(
                        text = stringResource(tab.title),
                        color = if (tab.title == selectedTab) MaterialTheme.colorScheme.tertiary else Color.White
                    )
                },
                selected = tab.title == selectedTab,
                onClick = {
                    selectedTab = tab.title
                    val route = when (tab) {
                        ProfileTab.SETTINGS -> ProfileDestinations.SettingsProfile.route
                        ProfileTab.COUPONS -> ProfileDestinations.CouponsProfile.route
                        ProfileTab.REVIEWS -> ProfileDestinations.ReviewsProfile.route
                    }
                    navController.navigate(route = route) {
                        launchSingleTop = true
                        popUpTo(ProfileDestinations.CouponsProfile.route) {
                            saveState = true
                        }
                        restoreState = true
                    }
                },
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileTopBar(lazyListState: LazyListState) {
    TopAppBar(
        modifier = Modifier
            .fillMaxWidth()
            .background(color = MaterialTheme.colorScheme.background)
            .animateContentSize(animationSpec = tween(durationMillis = 300))
            .height(height = if (lazyListState.isScrolled) 0.dp else TOP_BAR_HEIGHT),
        title = {
            Text(
                text = "Title",
                style = TextStyle(
                    color = MaterialTheme.colorScheme.onBackground
                )
            )
        }
    )
}

val TOP_BAR_HEIGHT = 56.dp
val LazyListState.isScrolled: Boolean
    get() = firstVisibleItemIndex > 0 || firstVisibleItemScrollOffset > 0

enum class ProfileTab(
    @StringRes val title: Int,
) {
    COUPONS(R.string.profile_coupons),
    REVIEWS(R.string.profile_reviews),
    SETTINGS(R.string.profile_settings);
}