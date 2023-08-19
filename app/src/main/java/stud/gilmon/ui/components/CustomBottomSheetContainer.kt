package stud.gilmon.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import stud.gilmon.ui.theme.TextFieldLabelColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomBottomSheetContainer(
    showModalBottomSheet: MutableState<Boolean>,
    content: @Composable ColumnScope.() -> Unit
) {
    val scope = rememberCoroutineScope()
    val skipPartially by remember { mutableStateOf(true) }
    val bottomSheetState = rememberModalBottomSheetState(skipPartially)

    androidx.compose.material3.ModalBottomSheet(
        containerColor = MaterialTheme.colorScheme.onBackground,
        onDismissRequest = { showModalBottomSheet.value = false },
        sheetState = bottomSheetState,
        windowInsets = WindowInsets.systemBars.only(WindowInsetsSides.Bottom),
        dragHandle = { CustomDragHandle() },
    )
    {
        Column(
            Modifier
                .fillMaxWidth()
                .windowInsetsPadding(WindowInsets.systemBars.only(WindowInsetsSides.Bottom))
                .padding(bottom = 60.dp, start = 10.dp, end = 10.dp)
                .background(MaterialTheme.colorScheme.onBackground),
            horizontalAlignment = CenterHorizontally
        ) {
            Text(
                text = "Choose coupon status",
                color = Color.LightGray,
                modifier = Modifier.padding(vertical = 15.dp)

            )
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.LightGray)
                    .size(1.dp)
            )
            content()
            CustomCancelButton(
                text = "Cancel"
            ) {
                scope.launch { bottomSheetState.hide() }.invokeOnCompletion {
                    if (!bottomSheetState.isVisible) {
                        showModalBottomSheet.value = false
                    }
                }
            }
        }
    }
}

val DockedDragHandleHeight = 4.0.dp
const val DockedDragHandleOpacity = 0.4f
val DockedDragHandleWidth = 22.0.dp
private val DragHandleVerticalPadding = 10.dp

@Preview
@Composable
fun CustomDragHandle(
    modifier: Modifier = Modifier,
    width: Dp = DockedDragHandleWidth,
    height: Dp = DockedDragHandleHeight,
    shape: Shape = MaterialTheme.shapes.medium,
    color: Color = TextFieldLabelColor
        .copy(DockedDragHandleOpacity),
) {
    Surface(
        modifier = modifier
            .padding(top = DragHandleVerticalPadding),
        color = color,
        shape = shape
    ) {
        Box(
            Modifier
                .size(
                    width = width,
                    height = height
                )
        )
    }
}