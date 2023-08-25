package stud.gilmon.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.MeasureResult
import androidx.compose.ui.layout.MeasureScope
import androidx.compose.ui.layout.Placeable
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun RowScope.CustomNavigationBarItem(
    selected: Boolean,
    onClick: () -> Unit,
    icon: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    label: @Composable (() -> Unit)? = null,
    alwaysShowLabel: Boolean = true,
    colors: NavigationBarItemColors = NavigationBarItemDefaults.colors(),
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() }
) {


    var itemWidth by remember { mutableStateOf(0) }

    Box(
        modifier
            .selectable(
                selected = selected,
                onClick = onClick,
                enabled = enabled,
                role = Role.Tab,
                interactionSource = interactionSource,
                indication = null,
            )
            .weight(1f)
            .onSizeChanged {
                itemWidth = it.width
            },
        contentAlignment = Alignment.Center
    ) {




        val indicator = @Composable {
            Box(
                Modifier
                    .layoutId(IndicatorLayoutIdTag)
                    .background(
                        color = Color.Red,
                        shape = ShapeDefaults.ExtraLarge,
                    )
            )
        }

        val indicatorRipple = @Composable {
            Box(
                Modifier
                    .scale(1f)
                    .clip(shape = CircleShape)
                    .size(100.dp)
                    .background(Color.Yellow)
            )
        }
        NavigationBarItemBaselineLayout(
            indicator = indicator,
            alwaysShowLabel = alwaysShowLabel
        )
    }
}
@Composable
private fun NavigationBarItemBaselineLayout(
    indicator: @Composable () -> Unit,
    alwaysShowLabel: Boolean,
) {
    Layout({
            Box(
                Modifier
                    .padding(horizontal = 15.dp)
            ) { Text("label") }
    }) { measurables, constraints ->
        val iconPlaceable =
            measurables.first { it.layoutId == "14" }.measure(constraints)

        val totalIndicatorWidth = iconPlaceable.width + (25.dp).roundToPx()
        val indicatorHeight = iconPlaceable.height + (25.dp).roundToPx()
        val indicatorRipplePlaceable =
            measurables
                .first { it.layoutId =="13" }
                .measure(
                    Constraints.fixed(
                        width = totalIndicatorWidth,
                        height = indicatorHeight
                    )
                )




            placeLabelAndIcon(
                iconPlaceable,
                indicatorRipplePlaceable,
                constraints,
                alwaysShowLabel,
            )

    }
}
private fun MeasureScope.placeLabelAndIcon(
    iconPlaceable: Placeable,
    indicatorRipplePlaceable: Placeable,
    constraints: Constraints,
    alwaysShowLabel: Boolean,
): MeasureResult {
    val height = constraints.maxHeight

    // Label should be `ItemVerticalPadding` from the bottom
    val labelY = height  - 25.dp.roundToPx()

    // Icon (when selected) should be `ItemVerticalPadding` from the top
    val selectedIconY = 25.dp.roundToPx()
    val unselectedIconY =
        if (alwaysShowLabel) selectedIconY else (height - iconPlaceable.height) / 2

    // How far the icon needs to move between unselected and selected states.
    val iconDistance = unselectedIconY - selectedIconY

    // The interpolated fraction of iconDistance that all placeables need to move based on
    // animationProgress.

    val containerWidth = constraints.maxWidth

    val labelX = (containerWidth) / 2
    val iconX = (containerWidth - iconPlaceable.width) / 2

    val rippleX = (containerWidth - indicatorRipplePlaceable.width) / 2
    val rippleY = selectedIconY - 25.dp.roundToPx()

    return layout(containerWidth, height) {
    }
}
private const val IndicatorRippleLayoutIdTag: String = "indicatorRipple"

private const val IndicatorLayoutIdTag: String = "indicator"

private const val IconLayoutIdTag: String = "icon"

private const val LabelLayoutIdTag: String = "label"


private const val ItemAnimationDurationMillis: Int = 100

/*@VisibleForTesting*/
internal val NavigationBarItemHorizontalPadding: Dp = 8.dp

/*@VisibleForTesting*/
internal val NavigationBarItemVerticalPadding: Dp = 16.dp


private val IndicatorVerticalOffset: Dp = 12.dp