package stud.gilmon

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.content.pm.ActivityInfo
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import com.example.dog_observer.viewModelFactory.ViewModelFactory
import stud.gilmon.base.utils.launchAndCollectIn
import stud.gilmon.data.local.entities.UsersEntity
import stud.gilmon.data.remote.UnsplashImages
import stud.gilmon.presentation.theme.GilmonTheme
import stud.gilmon.presentation.ui.feed.FeedItemScreen.FeedItemScreen
import stud.gilmon.presentation.ui.main.MainScreen
import javax.inject.Inject

@ExperimentalMaterial3Api
class MainActivity : ComponentActivity() {


    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private val component by lazy {
        (application as BaseApplication).component
    }


    private val viewModel by viewModels<MainViewModel> {
        viewModelFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        component.inject(this)
        super.onCreate(savedInstanceState)
        installSplashScreen().apply {
            setKeepOnScreenCondition {
                viewModel.loadingFlow.value
            }
        }
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            LockScreenOrientation(orientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
            val lifecycleOwner = rememberUpdatedState(LocalLifecycleOwner.current)
            val scope = rememberCoroutineScope()
            val login = remember { mutableStateOf("") }
            val user = remember { mutableStateOf(UsersEntity(userId = "", reviewId = 1)) }
           // val viewModel: MainViewModel = viewModel(factory = viewModelFactory)
            viewModel.readFromDataStore.observe(this) {
                login.value = it.toString()
                user.value = viewModel.getUser(it.toString()) ?: user.value.copy()
            }
            LaunchedEffect(key1 = true) {
                viewModel.getPhotos()
            }

            val photos = remember { mutableStateOf(listOf(UnsplashImages())) }
//            LaunchedEffect(key1 = Unit )
//            {
//                scope.launch {
//                    login.value=viewModel.getTUser()?: "404"
//                }
//            }

            SideEffect {
                viewModel.remoteRandomPhotosStateFlow.launchAndCollectIn(lifecycleOwner.value) {
                    if (it != null)
                        photos.value = it
                }
            }
            var darkTheme by remember { mutableStateOf(true) }

            //screenState.collectAsState(CommentsScreenState.Initial)
            GilmonTheme(darkTheme) {

                //  A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .windowInsetsPadding(WindowInsets.safeDrawing),
                    color = MaterialTheme.colorScheme.background
                ) {
                   // Test()
                    MainScreen(
                        darkTheme,
                        photos.value,
                        user,
                        toggleTheme = { darkTheme = !darkTheme },
                        viewModelFactory = viewModelFactory
                    )
                }
            }
        }
    }
}
@Composable
fun LockScreenOrientation(orientation: Int) {
    val context = LocalContext.current
    DisposableEffect(Unit) {
        val activity = context.findActivity() ?: return@DisposableEffect onDispose {}
        val originalOrientation = activity.requestedOrientation
        activity.requestedOrientation = orientation
        onDispose {
            // restore original orientation when view disappears
            activity.requestedOrientation = originalOrientation
        }
    }
}

fun Context.findActivity(): Activity? = when (this) {
    is Activity -> this
    is ContextWrapper -> baseContext.findActivity()
    else -> null
}
@Composable
fun Test() {

}
@Composable
fun lazycolumn() {
    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(
                bottom = 75.dp
            ),
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        item{
            Box(
                modifier = Modifier
                    .background(Color.Black)
                    .fillMaxWidth()
                    .height(100.dp)
            )
        }
    }
}