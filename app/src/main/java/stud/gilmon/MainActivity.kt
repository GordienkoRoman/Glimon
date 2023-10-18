package stud.gilmon

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.dog_observer.viewModelFactory.ViewModelFactory
import stud.gilmon.presentation.components.ChangeEmailBottomSheet
import stud.gilmon.presentation.components.SelectButton
import stud.gilmon.presentation.ui.main.MainScreen
import stud.gilmon.presentation.theme.GilmonTheme
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
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            val scope = rememberCoroutineScope()
            val login = remember { mutableStateOf("") }
            val viewModel: MainViewModel = viewModel(factory = viewModelFactory)
            viewModel.readFromDataStore.observe(
                this
            ) {
                login.value = it.toString()
            }
            installSplashScreen().apply {
//                setKeepOnScreenCondition{
//                    viewModel.loadingFlow.value
//                }
            }
//            LaunchedEffect(key1 = Unit )
//            {
//                scope.launch {
//                    login.value=viewModel.getTUser()?: "404"
//                }
//            }

            var darkTheme by remember { mutableStateOf(true) }

            //screenState.collectAsState(CommentsScreenState.Initial)
            GilmonTheme(darkTheme) {

                //  A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    // test()

                    MainScreen(
                        darkTheme,
                        login.value,
                        toggleTheme = { darkTheme = !darkTheme },
                        viewModelFactory = viewModelFactory
                    )
                }
            }
        }
    }
}

@Composable
fun test() {
    val showChangeEmailBottomSheet = rememberSaveable { mutableStateOf(false) }
    val mail = rememberSaveable { mutableStateOf("") }
    Column(
        Modifier
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        LazyColumn(
            Modifier.weight(1f), reverseLayout = true
        ) {
            item {
                SelectButton(labelText = "E-mail", text = mail.value, underline = true) {
                    showChangeEmailBottomSheet.value = !showChangeEmailBottomSheet.value
                } }
            items(100) {
                Text("item $it")
            }
            item {
                SelectButton(labelText = "E-mail", text = mail.value, underline = true) {
                showChangeEmailBottomSheet.value = !showChangeEmailBottomSheet.value
            } }
        }
        TextField(
            value = "",
            onValueChange = {},
            modifier = Modifier
                .fillMaxWidth()
                .imePadding()
        )
    }
    ChangeEmailBottomSheet(showModalBottomSheet = showChangeEmailBottomSheet, option = mail) {
        showChangeEmailBottomSheet.value = false
    }

}