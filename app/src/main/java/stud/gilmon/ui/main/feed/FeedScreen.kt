package stud.gilmon.ui.main.feed

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import stud.gilmon.ui.components.CustomText
import stud.gilmon.ui.theme.BackGroundDark1

@Composable
fun FeedScreen(){
    Box(modifier = Modifier.background(MaterialTheme.colorScheme.background)
        .fillMaxSize()){
        CustomText(text = "FeedScreen")
    }
}