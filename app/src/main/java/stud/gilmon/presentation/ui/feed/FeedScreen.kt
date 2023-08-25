package stud.gilmon.presentation.ui.feed

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import stud.gilmon.presentation.components.CustomText

@Composable
fun FeedScreen(){
    Box(modifier = Modifier.background(MaterialTheme.colorScheme.background)
        .fillMaxSize()){
        CustomText(text = "FeedScreen")
    }
}