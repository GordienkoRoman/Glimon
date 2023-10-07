package stud.gilmon.presentation.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import stud.gilmon.R

@Composable
fun IconWithText(
    icon: ImageVector,
    text: String,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSecondary
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = text,
            color = MaterialTheme.colorScheme.onBackground
        )
    }
}

@Composable
fun SocialNetworkIcon(modifier: Modifier= Modifier,idIcon:Int=R.drawable.odnoklassniki,color:Color=Color.Transparent,onClick:()->Unit={}){

    Button(modifier = modifier, onClick = onClick, colors = ButtonDefaults.buttonColors(
        containerColor = color),
        shape = RoundedCornerShape(5.dp),)
    {
        Icon(
            ImageVector.vectorResource(idIcon),
            contentDescription = null,
            tint = Color.Unspecified
        )
    }

}