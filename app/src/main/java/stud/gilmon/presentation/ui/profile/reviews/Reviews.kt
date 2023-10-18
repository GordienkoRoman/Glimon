package stud.gilmon.presentation.ui.profile.reviews

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import stud.gilmon.R
import stud.gilmon.presentation.components.CustomBottomSheetContainer
import stud.gilmon.presentation.components.CustomList
import stud.gilmon.presentation.components.SelectButton
import stud.gilmon.presentation.ui.profile.coupons.CouponsScreenState
import stud.gilmon.presentation.ui.profile.coupons.CouponsViewModel
import stud.gilmon.presentation.ui.profile.TOP_NAVIGATION_BAR_HEICHT

@Composable
fun ReviewsProfile(lazyListState: LazyListState) {
    val viewModel: CouponsViewModel = viewModel()
    val reviewsStatus = rememberSaveable { mutableStateOf("All") }
    val sortType = rememberSaveable { mutableStateOf("By new") }
    val screenState = viewModel.screenState.collectAsState(CouponsScreenState.Initial)
    val showReviewsStatusBottomSheet = rememberSaveable { mutableStateOf(false) }
    val showSortTypeBottomSheet = rememberSaveable { mutableStateOf(false) }



    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(
                top = TOP_NAVIGATION_BAR_HEICHT,
                start = 15.dp,
                end = 15.dp,
                bottom = 75.dp
            )
            .navigationBarsPadding(),
        state = lazyListState,
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        item {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .size(15.dp)
            )
            SelectButton("Review status", reviewsStatus.value)
            {
                showReviewsStatusBottomSheet.value = !showReviewsStatusBottomSheet.value
            }
        }
        item {
            SelectButton("How to sort?", sortType.value)
            {
                showSortTypeBottomSheet.value = !showSortTypeBottomSheet.value
            }
        }
        item {
            Column(
                Modifier.height(200.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.blank_paper_icon),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSecondary,
                    modifier = Modifier.size(50.dp)
                )
                Text(
                    text = "Empty list",
                    fontSize = 30.sp,
                    color = Color.White
                )
            }
        }
    }
    SortTypeBottomSheet(showSortTypeBottomSheet, sortType)
    ReviewsStatusBottomSheet(showReviewsStatusBottomSheet, reviewsStatus)
}

@Composable
fun ReviewsStatusBottomSheet(
    showModalBottomSheet: MutableState<Boolean>,
    option: MutableState<String>
) {
    if (showModalBottomSheet.value)
        CustomBottomSheetContainer(
            showModalBottomSheet = showModalBottomSheet,
            onDismissRequest = { showModalBottomSheet.value = false }) {
            CustomList(listOf("All", "Positive", "Negative"), option, showModalBottomSheet)
        }
}


@Composable
fun SortTypeBottomSheet(showModalBottomSheet: MutableState<Boolean>, option: MutableState<String>) {
    if (showModalBottomSheet.value)
        CustomBottomSheetContainer(
            showModalBottomSheet = showModalBottomSheet,
            onDismissRequest = { showModalBottomSheet.value = false }) {
            CustomList(listOf("By new", "By old", "By usefulness"), option, showModalBottomSheet)
        }
}