package stud.gilmon.presentation.ui.profile

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.StringRes
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.example.dog_observer.viewModelFactory.ViewModelFactory
import stud.gilmon.R
import stud.gilmon.data.local.entities.UsersEntity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    darkTheme: Boolean,
    user: MutableState<UsersEntity>,
    navController: NavHostController = rememberNavController(),
    toggleTheme: () -> Unit,
    viewModelFactory: ViewModelFactory,
    onClick: () -> Unit
) {
    val viewModel: ProfileViewModel = viewModel(factory = viewModelFactory)
    val lazyListStateList: List<LazyListState> = listOf(
        rememberLazyListState(),
        rememberLazyListState(),
        rememberLazyListState()
    )
    //val  user = viewModel.getUser(login)
    mainContent(
        darkTheme = darkTheme,
        user = user,
        navController = navController,
        toggleTheme = { toggleTheme() },
        viewModelFactory = viewModelFactory,
        viewModel =viewModel,
        lazyListStateList,
        onClick = onClick
    )


}

@Composable
fun mainContent(
    darkTheme: Boolean,
    user: MutableState<UsersEntity>,
    navController: NavHostController,
    toggleTheme: () -> Unit,
    viewModelFactory: ViewModelFactory,
    viewModel: ProfileViewModel,
    lazyListStateList: List<LazyListState>,
    onClick: () -> Unit
) {

    val topPadding by animateDpAsState(
        targetValue = if (lazyListStateList[0].isScrolled||
                lazyListStateList[1].isScrolled||
                lazyListStateList[2].isScrolled) 0.dp else TOP_BAR_HEIGHT,
        animationSpec = tween(durationMillis = 300)
    )
    Scaffold(
        content = { paddingValues ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                Scaffold(
                    modifier = Modifier.padding(top = topPadding),
                    topBar = { ProfileTopNavigationBar(navController = navController) },
                    contentWindowInsets = WindowInsets.systemBars.only(WindowInsetsSides.Bottom)
                ) {
                    ProfileNavGraph(
                        darkTheme,
                        user = user,
                        lazyListStateList,
                        navController = navController,
                        paddingValues = it,
                        toggleTheme = toggleTheme,
                        viewModelFactory = viewModelFactory,
                        onClick
                    )
                }
                ProfileTopBar(lazyListStateList = lazyListStateList, user,viewModel)
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
        Modifier.height(TOP_NAVIGATION_BAR_HEICHT),
        containerColor = MaterialTheme.colorScheme.onBackground,
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
fun ProfileTopBar(
    lazyListStateList: List<LazyListState>,
    user: MutableState<UsersEntity>,
    viewModel: ProfileViewModel
) {

    val selectedImageUri = rememberSaveable { mutableStateOf("") }
    val painter = rememberAsyncImagePainter(model =selectedImageUri.value.ifEmpty { R.drawable.genuine_beauty })
    val singlePhotoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia(),
        onResult = { uri ->
            selectedImageUri.value = uri.toString()
          //  user.value = user.value.copy(avatarUrl = uri.toString())
        viewModel.updateUserData(user.value.copy(avatarUrl = uri.toString()))}
    )
    TopAppBar(
        modifier = Modifier
            .fillMaxWidth()
            .animateContentSize(animationSpec = tween(durationMillis = 300))
            .height(
                height = if (lazyListStateList[0].isScrolled ||
                    lazyListStateList[1].isScrolled ||
                    lazyListStateList[2].isScrolled
                ) 0.dp else TOP_BAR_HEIGHT
            ),
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.onBackground
        ),
        title = {
            Row()
            {
                Text(
                    "Hi, ${user.value.firstName} ${user.value.lastName}",
                    fontSize = 30.sp,
                    color = Color.White,
                    modifier = Modifier
                        .padding(
                            horizontal = 15.dp
                        )
                        .weight(1f)
                )
                Image(painter = painter,
                    modifier = Modifier
                        .size(70.dp)
                        .clip(CircleShape)
                        .clickable(
                            onClick = {
                                singlePhotoPickerLauncher.launch(
                                    PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                )
                            }
                        ),
                    contentDescription = "god_beauty",
                    contentScale = ContentScale.Crop)
            }

        }
    )
}

val TOP_BAR_HEIGHT = 80.dp
val TOP_NAVIGATION_BAR_HEICHT = 50.dp
val LazyListState.isScrolled: Boolean
    get() = firstVisibleItemIndex > 0 || firstVisibleItemScrollOffset > 0

enum class ProfileTab(
    @StringRes val title: Int,
) {
    COUPONS(R.string.profile_coupons),
    REVIEWS(R.string.profile_reviews),
    SETTINGS(R.string.profile_settings);
}

@Composable
fun selectImage() {

}