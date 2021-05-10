package com.compose.component.text

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import com.compose.theme.PrimerTheme

@Composable
fun SecondaryText(
    modifier: Modifier = Modifier,
    text: AnnotatedString,
    color: Color = PrimerTheme.colors.textSecondary,
    maxLines: Int = Int.MAX_VALUE
) {
    Text(
        modifier = modifier,
        text = text,
        color = color,
        maxLines = maxLines,
        style =  PrimerTheme.types.secondary
    )
}