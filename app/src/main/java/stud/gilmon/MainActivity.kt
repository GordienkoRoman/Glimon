package stud.gilmon

import android.os.Bundle
import android.view.Surface
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Surface
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import stud.gilmon.ui.main.MainScreen
import stud.gilmon.ui.theme.GilmonTheme
import javax.inject.Inject

@ExperimentalMaterial3Api
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var application: BaseApplication
    override fun onCreate(savedInstanceState: Bundle?) {
        //component.inject(this)
        super.onCreate(savedInstanceState)
        setContent {

            var darkTheme by remember{ mutableStateOf(true)}
                //screenState.collectAsState(CommentsScreenState.Initial)
            GilmonTheme(darkTheme) {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    //TutorialBottomSheetScreen()
                    MainScreen(darkTheme, toggleTheme = { darkTheme=!darkTheme })
                }
            }
        }
    }

}