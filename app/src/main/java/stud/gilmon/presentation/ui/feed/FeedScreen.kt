package stud.gilmon.presentation.ui.feed

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.dog_observer.viewModelFactory.ViewModelFactory
import stud.gilmon.R
import stud.gilmon.data.remote.UnsplashImages
import stud.gilmon.presentation.components.ChangeLocationBottomSheet
import stud.gilmon.presentation.components.CustomButton
import stud.gilmon.presentation.components.SelectButton
import stud.gilmon.presentation.theme.TextFieldContainerColor
import stud.gilmon.presentation.theme.TextFieldLabelColor

@Composable
fun FeedScreen(
    photos: List<UnsplashImages>,
    factory:ViewModelFactory,
    onSearckClick: () -> Unit
) {
    val viewModel:FeedViewModel =  viewModel(factory = factory)
    viewModel.setPhotos(photos)
    Column() {

        SearchBar(userlocation = "",onSearckClick)

        LazyColumn(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background),
            contentPadding = PaddingValues(   bottom = 75.dp,top = 15.dp, start = 15.dp, end = 15.dp)

            /*   .windowInsetsTopHeight(WindowInsets.safeDrawing)*/,
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            items(photos.size)
            {
                FeedItem(photos[it])
            }


        }
    }
}


@Composable
fun SearchBar(userlocation: String,onSearckClick:()->Unit) {
    val location = rememberSaveable { mutableStateOf("") }
    val showChooseLocationBottomSheet = rememberSaveable { mutableStateOf(false) }
    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.onBackground)
            .padding(15.dp)
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        SelectButton(
            labelText = "Show promotions in",
            text = location.value,
            icon = Icons.Filled.LocationOn,
            containerColor = TextFieldContainerColor
        )
        {
            showChooseLocationBottomSheet.value = !showChooseLocationBottomSheet.value
        }
        Button(
            onClick = {onSearckClick()},
            colors = ButtonDefaults.buttonColors(
                containerColor = TextFieldContainerColor
            ),
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
        ) {
            Icon(
                modifier = Modifier.size(26.dp),
                imageVector = Icons.Filled.Search,
                contentDescription = null,
                tint = Color.White
            )
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                modifier = Modifier.weight(1f),
                text = "text",
                color = TextFieldLabelColor,
            )

            Icon(
                modifier = Modifier.size(26.dp),
                painter = painterResource(id = R.drawable.baseline_mic_24),
                contentDescription = null,
                tint = TextFieldLabelColor
            )
        }
        Row {
            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier.size(35.dp),
                shape = RoundedCornerShape(10.dp),
            ) {
                Icon(
                    modifier = Modifier.size(26.dp),
                    imageVector = Icons.Filled.Info,
                    contentDescription = null,
                    tint = TextFieldLabelColor
                )
            }
            LazyRow(horizontalArrangement = spacedBy(10.dp)) {
                items(10) {
                    CustomButton(
                       text ="button$it",
                        containerColor = TextFieldContainerColor
                    ) {

                    }
                }
            }
        }
    }


    ChangeLocationBottomSheet(
        showModalBottomSheet = showChooseLocationBottomSheet,
        option = location
    ) {
        showChooseLocationBottomSheet.value = false
    }
}