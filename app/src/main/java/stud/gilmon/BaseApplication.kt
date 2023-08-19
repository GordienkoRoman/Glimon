package stud.gilmon

import android.app.Application
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext

/*class BaseApplication : Application(){

    lateinit var component : AppComponent
   *//* val component : AppComponent by lazy {
        DaggerAppComponent.factory()
            .create(this)
    }*//*
    val isDark = mutableStateOf(false)

    fun toggleLightTheme(){
        isDark.value = !isDark.value
    }

    override fun onCreate() {
        DaggerAppComponent.factory()
            .create(this)
        super.onCreate()
    }
}*/
class BaseApplication : Application(){

    // should be saved in data store
    val isDark = mutableStateOf(false)

    fun toggleLightTheme(){
        isDark.value = !isDark.value
    }

}
/*
@Composable
fun getApplicationComponent(): AppComponent {
    return (LocalContext.current.applicationContext as BaseApplication).component
}*/
