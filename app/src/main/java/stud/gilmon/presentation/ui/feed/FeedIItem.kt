package stud.gilmon.presentation.ui.feed

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import stud.gilmon.R
import stud.gilmon.domain.FeedItem
import stud.gilmon.presentation.components.IconWithText
import stud.gilmon.presentation.theme.SpacerColor

@Preview
@Composable
fun FeedItem(
    item: FeedItem= FeedItem(0),
    modifier: Modifier = Modifier,
    onDetails: (Int) -> Unit={},
) {
    Card(
        modifier = Modifier
            .width(250.dp)
            .padding(8.dp)

    ) {
        Column(
            modifier = Modifier
                .clickable { onDetails(item.id) }
                .background(MaterialTheme.colorScheme.onBackground)
        ) {
            Surface(
                modifier = modifier
                    .fillMaxWidth()
                    .height(150.dp),
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.genuine_beauty),
                        contentDescription = "Cuisine image",
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )
                }
            }
            Text(
                text = item.title,
                color = Color.White,
                maxLines = 1,
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .padding(top = 4.dp)
                    .fillMaxWidth()
            )


            Text(
                text = "location",
                color = Color.White,
                maxLines = 1,
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .padding(top = 4.dp)
                    .fillMaxWidth()
            )
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(SpacerColor)
                    .size(2.dp)
            )
            IconWithText(icon = Icons.Filled.ShoppingCart, text ="123")
         //   FeedItemBottom()
        }
    }
}
@Composable
fun FeedItemBottom(){
    Row(){
        IconWithText(icon = Icons.Filled.ShoppingCart, text ="123")
    }
}