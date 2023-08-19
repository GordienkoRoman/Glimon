package stud.gilmon.ui.main.profile.settings

import android.content.res.Resources.Theme
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch
import stud.gilmon.ui.components.CustomButton
import stud.gilmon.ui.components.CustomTextField
import stud.gilmon.ui.components.LabelText
import stud.gilmon.ui.components.SelectButton
import stud.gilmon.ui.theme.BackGroundDark2


@Composable
fun SettingsProfile(darkTheme:Boolean, settings: Settings = Settings(),toggleTheme:()-> Unit){

    val viewModel: SettingsViewModel = viewModel()
    Column(modifier = Modifier
        .background(MaterialTheme.colorScheme.background)
        .fillMaxSize()
        .padding(
            top = 75.dp,
            start = 25.dp,
            end = 25.dp
        )
        .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(15.dp)) {
        Log.d("JC_TAG","screen")
        PersonalData()
        AccountSettings(settings)
        AdditionalSettings(darkTheme)
        CustomButton(text = "Sign out", onClick = toggleTheme)
    }
}

@Composable
fun PersonalData(){
    Column(modifier = Modifier
        .clip(shape = RoundedCornerShape(20.dp))
        .background(BackGroundDark2)
        .padding(15.dp)
        .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ){
        LabelText(text = "Label")
        CustomTextField(value = "text", label = "label")
        CustomTextField(value = "text", label = "label")
        CustomTextField(value = "", label = "label")

    }
}

@Composable
fun AccountSettings(settings: Settings){
    Column(modifier = Modifier
        .clip(shape = RoundedCornerShape(20.dp))
        .background(BackGroundDark2)
        .padding(15.dp)
        .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        LabelText(text = "Label")
        Log.d("JC_TAG","account")
        SelectButton(labelText = "label", text = "text", underline = true)
        SelectButton(labelText = "label", text = "text", underline = true)
        SelectButton(labelText = "label", text = "text", underline = true)
        SelectButton(labelText = "label", text = "text", underline = true)

        Row(Modifier.padding(horizontal = 30.dp)) {
            Column(Modifier.weight(1f)) {
                Column() {
                    Text(
                        text = "label",
                        color = Color.LightGray,
                    )
                    Text(
                        text = "text",
                        color = Color.White,
                    )
                }
            }
            Switch(checked = settings.darkTheme,
                colors = SwitchDefaults.colors(
                    uncheckedBorderColor = Color.Transparent,
                    uncheckedThumbColor = Color.White,
                    uncheckedTrackColor = MaterialTheme.colorScheme.background,
                    checkedTrackColor = MaterialTheme.colorScheme.tertiary
                ),
                onCheckedChange = { TODO() })
        }
    }
}

@Composable
fun AdditionalSettings(darkTheme:Boolean){
    val coroutineScope = rememberCoroutineScope()
    Column(modifier = Modifier
        .clip(shape = RoundedCornerShape(20.dp))
        .background(BackGroundDark2)
        .padding(15.dp)
        .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Log.d("JC_TAG","additional")
        LabelText(text = "Label")
        SelectButton(labelText = "DARK THEME", text = darkTheme.toString(), underline = true){
            coroutineScope.launch {

            }
        }
        SelectButton(labelText = "DARK THEME", text = "text", underline = true)
    }
}

