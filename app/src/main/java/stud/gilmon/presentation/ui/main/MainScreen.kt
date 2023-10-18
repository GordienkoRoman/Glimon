package stud.gilmon.presentation.ui.main

import androidx.annotation.StringRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.add
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.dog_observer.viewModelFactory.ViewModelFactory
import kotlinx.coroutines.launch
import stud.gilmon.R
import stud.gilmon.presentation.components.CustomDragHandle
import stud.gilmon.presentation.components.CustomNavigationBar
import stud.gilmon.presentation.ui.login.LoginScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(darkTheme:Boolean,
               login:String,
               navController: NavHostController = rememberNavController(),
               toggleTheme:()-> Unit,
viewModelFactory: ViewModelFactory){
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberBottomSheetScaffoldState()

    BottomSheetScaffold(
        sheetDragHandle = {CustomDragHandle()},
        containerColor = MaterialTheme.colorScheme.onBackground,
        sheetContainerColor =MaterialTheme.colorScheme.onBackground,
        sheetContent = {
            LoginScreen(navController, viewModelFactory = viewModelFactory) {
                scope.launch {
                    if (scaffoldState.bottomSheetState.isVisible)
                    {
                        scaffoldState.bottomSheetState.partialExpand()
                    }
                }
            }
        },
        modifier = Modifier.background(MaterialTheme.colorScheme.onBackground),
        scaffoldState = scaffoldState
    ) {
        Scaffold(bottomBar = { MainBottomAppBar(navController = navController,login) },
            contentWindowInsets = WindowInsets.safeDrawing
        ) {
            MainScreenNavGraph(darkTheme,navController,it, toggleTheme = toggleTheme,viewModelFactory = viewModelFactory)
        }
    }

}

@Composable
fun MainBottomAppBar(navController: NavController,login: String) {
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
                        MainTab.PROFILE -> Graph.PROFILE_GRAPH+"/"+login
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