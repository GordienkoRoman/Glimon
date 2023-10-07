package stud.gilmon.presentation.ui.profile.coupons

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import stud.gilmon.R
import stud.gilmon.presentation.components.CustomBottomSheetContainer
import stud.gilmon.presentation.components.SelectButton
import stud.gilmon.presentation.theme.BackGroundDark2
import androidx.compose.runtime.*
import stud.gilmon.presentation.components.CustomList


@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CouponsProfile(lazyListState: LazyListState) {

    val viewModel: CouponsViewModel = viewModel()
    val couponStatus = rememberSaveable { mutableStateOf("All") }
    val sortType = rememberSaveable { mutableStateOf("By new") }
    val screenState = viewModel.screenState.collectAsState(CouponsScreenState.Initial)
    val showCouponStatusBottomSheet = rememberSaveable { mutableStateOf(false) }
    val showSortTypeBottomSheet = rememberSaveable { mutableStateOf(false) }


    val windowInsets = WindowInsets(0)

    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(
                top = 75.dp,
                start = 25.dp,
                end = 25.dp
            ),
        state = lazyListState,
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {
        item {
            SelectButton("Coupon status", couponStatus.value)
            {
                showCouponStatusBottomSheet.value = !showCouponStatusBottomSheet.value
            }
        }
        item {
            SelectButton("How to sort?", sortType.value)
            {
                showSortTypeBottomSheet.value = !showSortTypeBottomSheet.value
            }
        }
        items(20) {
            Column(
                Modifier.clip(shape = RoundedCornerShape(20.dp)).fillMaxSize()
                    .background(MaterialTheme.colorScheme.onBackground)
                    ,
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.blank_paper_icon),
                    contentDescription = null,
                    tint = Color.White,
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
    CopunsStatusBottomSheet(showCouponStatusBottomSheet, couponStatus)
    SortTypeBottomSheet(showSortTypeBottomSheet, sortType)

}

@Composable
fun CopunsStatusBottomSheet(
    showModalBottomSheet: MutableState<Boolean>,
    option: MutableState<String>
) {
    if (showModalBottomSheet.value)
        CustomBottomSheetContainer(showModalBottomSheet = showModalBottomSheet) {
            CustomList(listOf("All", "Current", "Expired"), option, showModalBottomSheet)
        }
}

@Composable
fun SortTypeBottomSheet(showModalBottomSheet: MutableState<Boolean>, option: MutableState<String>) {
    if (showModalBottomSheet.value)
        CustomBottomSheetContainer(showModalBottomSheet = showModalBottomSheet) {
            CustomList(
                listOf("By new", "By old", "By amount of discount"),
                option,
                showModalBottomSheet
            )
        }
}


