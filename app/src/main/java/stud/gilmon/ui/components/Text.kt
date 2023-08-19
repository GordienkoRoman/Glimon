package stud.gilmon.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import stud.gilmon.ui.theme.BackGroundDark1
import stud.gilmon.ui.theme.BackGroundDark2
import stud.gilmon.ui.theme.TextFieldLabelColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTextField(
    value: String,
    readOnly: Boolean = false,
    label:String,
    onValueChange:(String)->Unit={}) {
  TextField(
      label = {Text(label, color = TextFieldLabelColor)},
        value = value,
        onValueChange = onValueChange,
        readOnly = readOnly,
        colors = TextFieldDefaults.colors(
            focusedTextColor = MaterialTheme.colorScheme.primary,
            focusedContainerColor = Color.White,
            unfocusedContainerColor= Color.White,
            disabledIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,

        ),
        modifier = Modifier.padding(horizontal = 5.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
    )


}

@Composable
fun CustomText(text:String,modifier: Modifier=Modifier){
    Text(text,
        color = Color.White ,
        modifier = modifier.padding(
            horizontal = 15.dp
        ))
}

@Composable
fun LabelText(text:String,modifier: Modifier=Modifier){
    Text(text,
        fontSize= 25.sp,
        color = Color.White ,
        modifier = modifier.padding(
            horizontal = 15.dp
        ))
}