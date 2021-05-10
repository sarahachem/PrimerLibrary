package com.compose.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowRight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import com.compose.component.text.Body1
import com.compose.theme.DarkThemePreviewParamProvider
import com.compose.theme.SixteenDp
import com.compose.theme.PrimerTheme

const val FontTreshold = 1.2f

@Composable
fun LabelIconCell(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    labelText: String,
    topDivider: Boolean = false,
    bottomDivider: Boolean = false,
    showArrow: Boolean = false
) {
    CellLayout(
        modifier = modifier,
        onClick = onClick,
        label = { Body1(text = labelText) },
        showArrow = showArrow,
        topDivider = topDivider,
        bottomDivider = bottomDivider
    )
}

@Composable
private fun CellLayout(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    label: @Composable () -> Unit,
    topDivider: Boolean = false,
    bottomDivider: Boolean = false,
    showArrow: Boolean = false
) {
    Column(
        Modifier
            .fillMaxWidth()
            .clickable(enabled = onClick != null, onClick = { onClick?.invoke() })
            .then(modifier)
    ) {
        if (topDivider) Divider(color = PrimerTheme.colors.divider)
        CellContent(
            label = label,
            showArrow = showArrow
        )
        if (bottomDivider) Divider(color = PrimerTheme.colors.divider)
    }
}

@Composable
private fun CellContent(
    label: (@Composable () -> Unit)? = null,
    showArrow: Boolean = false
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = SixteenDp)
    ) {
        val fontScale = LocalConfiguration.current.fontScale
        val accessibleConstraintSet = accessibilityConstraints(
            isHorizontal = fontScale < FontTreshold,
            hasEndPart = showArrow
        )
        ConstraintLayout(constraintSet = accessibleConstraintSet, modifier = Modifier.weight(1f)) {
            label?.let {
                Box(Modifier.layoutId("label")) {
                    label()
                }
            }
        }
        if (showArrow) {
            Icon(
                modifier = Modifier.layoutId("icon"),
                imageVector = Icons.Rounded.KeyboardArrowRight,
                contentDescription = "Accessory",
                tint = MaterialTheme.colors.onSurface
            )
        }
    }
}

fun accessibilityConstraints(isHorizontal: Boolean, hasEndPart: Boolean): ConstraintSet {
    return ConstraintSet {
        val guidelineEnd = createGuidelineFromStart(if (hasEndPart) 0.5f else 1f)
        val icon = createRefFor("icon")
        val label = createRefFor("label")
        if (isHorizontal) {
            constrain(label) {
                linkTo(start = parent.start, end = icon.start, bias = 0f)
                centerVerticallyTo(parent)
                width = Dimension.fillToConstraints
            }
            constrain(icon) {
                linkTo(start = guidelineEnd, end = parent.end, bias = 1f)
                centerVerticallyTo(parent)
                width = Dimension.preferredWrapContent
            }
        } else {
            constrain(label) {
                linkTo(start = parent.start, end = parent.end)
                linkTo(top = parent.top, bottom = icon.top)
                width = Dimension.fillToConstraints
            }
            constrain(label) {
                linkTo(start = parent.start, end = parent.end)
                linkTo(top = label.bottom, bottom = parent.bottom, topMargin = 8.dp)
                width = Dimension.fillToConstraints
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Preview(name = "SampleLabelWithIcon", showBackground = true)
@Composable
fun SampleLabelWithIcon(@PreviewParameter(DarkThemePreviewParamProvider::class) isDarkTheme: Boolean) {
    PrimerTheme(darkTheme = isDarkTheme) {
        LabelIconCell(
            modifier = Modifier
                .padding(horizontal = 24.dp),
            labelText = "I'm the label",
            showArrow = true
        )
    }
}
