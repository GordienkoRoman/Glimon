package stud.gilmon.presentation.ui.feed

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import stud.gilmon.R
import stud.gilmon.data.remote.UnsplashImages
import stud.gilmon.presentation.components.CustomText
import stud.gilmon.presentation.components.IconWithText
import stud.gilmon.presentation.components.LabelText

@Preview
@Composable
fun FeedItem(
    photo: UnsplashImages = UnsplashImages(),
    modifier: Modifier = Modifier,
    onDetails: (Int) -> Unit = {},
) {
    Card(
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(20.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        ) {
            SubcomposeAsyncImage(
                model = photo.urls?.raw,
                contentDescription = "",
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.Crop,
                loading = {
                    CircularProgressIndicator()
                }
            )
        }
        Column(
            modifier = Modifier
                .clickable { }
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.onBackground)
                .padding(horizontal = 15.dp, vertical = 20.dp),
            verticalArrangement = Arrangement.spacedBy(15.dp),
        ) {
            LabelText(text = photo.user.name ?: "name")
            CustomText(text = photo.user.username ?: "name")
            CustomText(text = photo.description ?: photo.user.bio ?: "Description")
            CustomText(text = photo.location.name ?: photo.user.location ?: "Location")
        }
        Divider( thickness = 1.dp, color = Color.DarkGray)

        FeedItemBottom(photo)
    }
}


@Composable
fun FeedItemBottom(photo: UnsplashImages) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.onBackground)
            .padding(horizontal = 15.dp, vertical = 20.dp)
    ) {
        IconWithText(painter = painterResource(R.drawable.baseline_shopping_cart_24), text = (photo.downloads ?: 1).toString())
        Spacer(modifier = Modifier.width(15.dp))
        IconWithText(painter = painterResource(R.drawable.baseline_message_24), text = (photo.likes ?: 1).toString())

    }
}