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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import stud.gilmon.R
import stud.gilmon.data.local.entities.UsersEntity
import stud.gilmon.data.model.FeedItem
import stud.gilmon.data.remote.UnsplashImages
import stud.gilmon.presentation.components.CustomText
import stud.gilmon.presentation.components.IconWithText
import stud.gilmon.presentation.components.LabelText

@Composable
fun FeedItemComponent(
    photo: UnsplashImages = UnsplashImages(),
    onItemClick: (Int) -> Unit = {},
    index: Int
) {
    val feedItem = remember {
        mutableStateOf(FeedItem(
            companyName = photo.user.name ?: "name",
            promotionName = photo.user.name ?: "name",
            description = photo.description ?: photo.user.bio ?: "Description",
            location = photo.location.name ?: photo.user.location ?: "Location",
            imgUrl = photo.urls?.raw.toString()
        ))
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemClick(index) },
        shape = RoundedCornerShape(20.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
        ) {
            SubcomposeAsyncImage(
                model = feedItem.value.imgUrl,
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
            LabelText(text = feedItem.value.companyName)
            CustomText(text = feedItem.value.promotionName)
            CustomText(text = feedItem.value.description)
            CustomText(text = feedItem.value.location)
        }
        Divider(thickness = 1.dp, color = Color.DarkGray)

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
        IconWithText(
            painter = painterResource(R.drawable.baseline_shopping_cart_24),
            text = (photo.downloads ?: 1).toString()
        )
        Spacer(modifier = Modifier.width(15.dp))
        IconWithText(
            painter = painterResource(R.drawable.baseline_message_24),
            text = (photo.likes ?: 1).toString()
        )

    }
}