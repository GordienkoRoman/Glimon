package stud.gilmon

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import stud.gilmon.presentation.ui.main.MainScreen
import stud.gilmon.presentation.theme.GilmonTheme
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

                    MainScreen(darkTheme, toggleTheme = { darkTheme=!darkTheme })
                }
            }
        }
    }

}