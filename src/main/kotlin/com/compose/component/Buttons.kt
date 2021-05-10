package com.compose.component

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.ButtonColors
import androidx.compose.material.ButtonDefaults.buttonColors
import androidx.compose.material.Divider
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.compose.theme.DarkThemePreviewParamProvider
import com.compose.theme.EightDp
import com.compose.theme.SixteenDp
import com.compose.theme.PrimerTheme
import com.primer.R

enum class ColorStyle {
    Default,
    Error
}

@Composable
fun ButtonWithIconCell(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    iconResId: Int? = null,
    button: (@Composable () -> Unit)? = null,
    topDivider: Boolean = false,
    bottomDivider: Boolean = false,
) {
    Column(
        Modifier
            .fillMaxWidth()
            .clickable(enabled = onClick != null, onClick = { onClick?.invoke() })
            .then(modifier)
    ) {
        if (topDivider) Divider(color = PrimerTheme.colors.divider)
        ButtonLayoutContent(
            iconResId = iconResId,
            button = button,
        )
        if (bottomDivider) Divider(color = PrimerTheme.colors.divider)
    }
}

@Composable
private fun ButtonLayoutContent(
    iconResId: Int?,
    iconWidthInDp: Dp = 36.dp,
    iconHeightInDp: Dp = 36.dp,
    button: (@Composable () -> Unit)? = null,
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(vertical = EightDp)
    ) {
        iconResId?.let {
            Image(
                painter = painterResource(id = iconResId),
                modifier = Modifier
                    .padding(horizontal = EightDp)
                    .size(iconWidthInDp, iconHeightInDp),
                contentDescription = ""
            )
        }
        val fontScale = LocalConfiguration.current.fontScale
        val accessibilityCS = accessibilityConstraints(
            isHorizontal = fontScale < FontTreshold,
            hasEndPart = true
        )
        ConstraintLayout(constraintSet = accessibilityCS, modifier = Modifier.weight(1f)) {
            button?.invoke()
        }
    }
}

@Composable
fun FilledButton(
    modifier: Modifier = Modifier,
    text: String,
    enabled: Boolean = true,
    colorStyle: ColorStyle = ColorStyle.Default,
    isLoading: Boolean = false,
    onClick: () -> Unit
) {
    val colors = getColors(fill = true, colorStyle = colorStyle)
    val contentColor: Color by colors.contentColor(enabled = enabled)
    Button(
        modifier = modifier,
        onClick = { if (!isLoading) onClick.invoke() },
        enabled = enabled,
        shape = MaterialTheme.shapes.large,
        elevation = null,
        colors = colors,
        contentPadding = PaddingValues(
            start = 20.dp,
            top = 12.dp,
            end = 20.dp,
            bottom = 12.dp
        ),
        content = { ButtonContent(contentColor, text) }
    )
}

@Composable
fun OutlinedButton(
    modifier: Modifier = Modifier,
    text: String,
    enabled: Boolean = true,
    colorStyle: ColorStyle = ColorStyle.Default,
    isLoading: Boolean = false,
    onClick: () -> Unit
) {
    val colors = getColors(fill = false, colorStyle = colorStyle)
    val contentAndBorderColor: Color by colors.contentColor(enabled = enabled)
    OutlinedButton(
        modifier = modifier,
        onClick = { if (!isLoading) onClick.invoke() },
        enabled = enabled,
        shape = MaterialTheme.shapes.large,
        elevation = null,
        colors = getColors(fill = false, colorStyle = colorStyle),
        border = BorderStroke(1.dp, contentAndBorderColor),
        contentPadding = PaddingValues(
            start = 20.dp,
            top = 12.dp,
            end = 20.dp,
            bottom = 12.dp
        ),
        content = {
            ButtonContent(
                contentAndBorderColor,
                text
            )
        }
    )
}

@Composable
private fun RowScope.ButtonContent(
    contentColor: Color,
    text: String
) {
    Row(
        Modifier.weight(1f),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(SixteenDp, Alignment.CenterHorizontally)
    ) {
        Text(
            text,
            textAlign = TextAlign.Center,
            style = PrimerTheme.types.button1,
            color = contentColor
        )
    }
}

@Composable
private fun getColors(
    fill: Boolean,
    colorStyle: ColorStyle,
): ButtonColors {
    val backgroundColor: Color
    val contentColor: Color
    val disabledContentColor: Color
    val disabledBackgroundColor: Color
    if (fill) {
        backgroundColor = when (colorStyle) {
            ColorStyle.Default -> PrimerTheme.colors.mainColor
            ColorStyle.Error -> MaterialTheme.colors.error
        }
        disabledBackgroundColor = when (colorStyle) {
            ColorStyle.Default -> PrimerTheme.colors.mainColorDisabled
            ColorStyle.Error -> MaterialTheme.colors.error.copy(alpha = 0.7f)
        }
        contentColor = when (colorStyle) {
            ColorStyle.Default,
            ColorStyle.Error -> PrimerTheme.colors.onClick
        }
        disabledContentColor = when (colorStyle) {
            ColorStyle.Default -> Color.DarkGray
            else -> contentColor
        }
    } else {
        backgroundColor = Color.Transparent
        disabledBackgroundColor = Color.Transparent
        contentColor = when (colorStyle) {
            ColorStyle.Error -> MaterialTheme.colors.error
            else -> PrimerTheme.colors.mainColor
        }
        disabledContentColor = contentColor.copy(alpha = 0.7f)
    }
    return buttonColors(
        backgroundColor = backgroundColor,
        disabledBackgroundColor = disabledBackgroundColor,
        contentColor = contentColor,
        disabledContentColor = disabledContentColor
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Preview(name = "SampleButtonWithIcon", showBackground = true)
@Composable
fun SampleButtonWithNoIcon(@PreviewParameter(DarkThemePreviewParamProvider::class) isDarkTheme: Boolean) {
    PrimerTheme(darkTheme = isDarkTheme) {
        ButtonWithIconCell(
            button = {
                FilledButton(
                    modifier = Modifier
                        .height(height = 48.dp)
                        .fillMaxWidth(),
                    text = "I'm the button",
                    enabled = true,
                    isLoading = false,
                    onClick = {})
            }
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Preview(name = "SampleButtonWithIcon", showBackground = false)
@Composable
fun SampleButtonWithIcon(@PreviewParameter(DarkThemePreviewParamProvider::class) isDarkTheme: Boolean) {
    PrimerTheme(darkTheme = isDarkTheme) {
        ButtonWithIconCell(
            button = {
                FilledButton(
                    modifier = Modifier
                        .height(height = 48.dp)
                        .fillMaxWidth(),
                    text = "I'm the button with icon",
                    enabled = false,
                    isLoading = false,
                    onClick = {})
            },
            iconResId = R.drawable.ic_action_info
        )
    }
}