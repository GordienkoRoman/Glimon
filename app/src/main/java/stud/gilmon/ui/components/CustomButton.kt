package stud.gilmon.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.List
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import stud.gilmon.ui.theme.BackGroundDark2
import stud.gilmon.ui.theme.SpacerColor
import stud.gilmon.ui.theme.TextFieldLabelColor

@Composable
fun CustomButton(text: String,modifier: Modifier=Modifier, onClick: () -> Unit = {}) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.onBackground
        ),
        shape = RoundedCornerShape(5.dp),
        modifier = modifier
            .fillMaxWidth()

        //modifier = Modifier.clip(RoundedCornerShape(1.dp))
    ) {

        Text(
            text = text,
            color = Color.White,
        )
    }
}

@Composable
fun CustomCancelButton(text: String,modifier: Modifier=Modifier, onClick: () -> Unit = {}) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.White
        ),
        shape = RoundedCornerShape(10.dp),
        modifier = modifier
            .fillMaxWidth()

    ) {
        Text(
            text = text,
            color = Color.LightGray,
            modifier = Modifier.padding(vertical = 5.dp)
        )
    }
}

@Composable
fun LinkButton(text: String, icon: ImageVector, onClick: () -> Unit = {}) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.onBackground
        ),
        shape = RoundedCornerShape(5.dp),
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSecondary
        )
        Spacer(modifier = Modifier.width(5.dp))
        Text(
            text = text,
            color = Color.White,
            modifier = Modifier.weight(1f)
        )
        Icon(
            imageVector = Icons.Filled.ArrowForward,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSecondary
        )
    }
}

@Composable
fun SelectButton(labelText: String, text: String,underline:Boolean=false,onClick: () -> Unit = {}) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = BackGroundDark2
        ),
        shape = RoundedCornerShape(5.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 5.dp)
    ) {
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = labelText,
                color = Color.LightGray,
            )
            Text(
                text = text,
                color = Color.White,
            )
        }

        Icon(
            imageVector = Icons.Filled.ArrowDropDown,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSecondary
        )
    }
    if(underline)
        Spacer(Modifier.padding(horizontal = 20.dp)
            .fillMaxWidth()
            .size(1.dp)
            .background(SpacerColor))
}