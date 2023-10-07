package stud.gilmon

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.dog_observer.viewModelFactory.ViewModelFactory
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import stud.gilmon.presentation.ui.main.MainScreen
import stud.gilmon.presentation.theme.GilmonTheme
import stud.gilmon.presentation.ui.login.LoginViewModel
import javax.inject.Inject

@ExperimentalMaterial3Api
class MainActivity : ComponentActivity() {


    @Inject
    lateinit var  viewModelFactory: ViewModelFactory
    private val component by lazy {
        (application as BaseApplication).component
    }


    private val viewModel by viewModels<MainViewModel>{
        viewModelFactory
    }
    override fun onCreate(savedInstanceState: Bundle?) {


        component.inject(this)
        super.onCreate(savedInstanceState)
        setContent {
            val scope = rememberCoroutineScope()
            val login = remember { mutableStateOf("") }
            val viewModel: MainViewModel = viewModel(factory = viewModelFactory)
            viewModel.readFromDataStore.observe(this
            ) {
                login.value = it.toString()
            }
            installSplashScreen().apply {
                setKeepOnScreenCondition{
                    viewModel.loadingFlow.value
                }
            }
//            LaunchedEffect(key1 = Unit )
//            {
//                scope.launch {
//                    login.value=viewModel.getTUser()?: "404"
//                }
//            }

            var darkTheme by remember{ mutableStateOf(true)}
                //screenState.collectAsState(CommentsScreenState.Initial)
            GilmonTheme(darkTheme) {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    //Box(modifier = Modifier.background(Color.Red))
                    MainScreen(darkTheme,login.value, toggleTheme = { darkTheme=!darkTheme }, viewModelFactory = viewModelFactory)
                }
            }
        }
    }

}