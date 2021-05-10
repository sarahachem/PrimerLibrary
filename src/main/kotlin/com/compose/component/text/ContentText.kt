package com.compose.component.text

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import com.compose.theme.PrimerTheme

@Composable
fun ContentText(
    modifier: Modifier = Modifier,
    text: String,
    color: Color = PrimerTheme.colors.textSecondary,
    maxLines: Int = Int.MAX_VALUE,
) = Text(modifier = modifier,
        text = text,
        color = color,
        maxLines = maxLines,
        overflow = TextOverflow.Ellipsis
)

@Composable
fun Body1(modifier: Modifier = Modifier, text: String, color: Color = PrimerTheme.colors.textSecondary) = ContentText(modifier, text,  color)