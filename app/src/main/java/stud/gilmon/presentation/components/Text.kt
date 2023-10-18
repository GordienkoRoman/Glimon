package stud.gilmon.presentation.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import stud.gilmon.presentation.theme.TextFieldContainerColor
import stud.gilmon.presentation.theme.TextFieldLabelColor

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun CustomTextField(
    modifier: Modifier = Modifier,
    value: String = "",
    readOnly: Boolean = false,
    label: String = "label",
    onValueChange: (String) -> Unit = {}
) {
    TextField(
        label = { Text(label, color = TextFieldLabelColor) },
        value = value,
        onValueChange = onValueChange,
        readOnly = readOnly,
        colors = TextFieldDefaults.colors(
            unfocusedTextColor = MaterialTheme.colorScheme.onSecondary,
            focusedTextColor = MaterialTheme.colorScheme.onSecondary,
            focusedContainerColor = TextFieldContainerColor,
            unfocusedContainerColor = TextFieldContainerColor,
            disabledIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            unfocusedLabelColor = TextFieldLabelColor,

            ),
        modifier = modifier
            .padding(horizontal = 5.dp)
            .fillMaxWidth()
            .imePadding(),
        shape = RoundedCornerShape(10.dp),
    )


}

@Composable
fun CustomText(text: String, modifier: Modifier = Modifier) {
    Text(
        text,
        color = Color.White,
        modifier = modifier.padding(
            horizontal = 15.dp
        )
    )
}

@Composable
fun LabelText(text: String, modifier: Modifier = Modifier) {
    Text(
        text,
        fontSize = 25.sp,
        color = Color.White,
        modifier = modifier.padding(
            horizontal = 15.dp
        )
    )
}