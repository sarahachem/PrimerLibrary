package com.compose.theme

import androidx.compose.ui.graphics.Color

data class ThemedColor(val light: Color, val dark: Color)
fun ThemedColor.forTheme(isDark: Boolean) = if (isDark) dark else light

internal object ThemeColorAlias {
    val backgroundMain = ThemedColor(light = ColorAlias.whiteGrey, dark = ColorAlias.black)
    val backgroundSecondary = ThemedColor(light = ColorAlias.lightGrey, dark = ColorAlias.darkGrey)
    val textMain = ThemedColor(light = ColorAlias.black, dark = ColorAlias.lightGrey)
    val textSecondary = ThemedColor(light = ColorAlias.grey, dark = ColorAlias.veryLightGrey)
    val textPlaceholder = ThemedColor(light = ColorAlias.disabledGrey, dark = ColorAlias.disabledGrey)
    val actionMain = ThemedColor(light = ColorAlias.blue, dark = ColorAlias.blue)
    val actionMainError = ThemedColor(light = ColorAlias.red, dark = ColorAlias.red)
    val actionMainDisabled = ThemedColor(light = ColorAlias.disabledGrey, dark = ColorAlias.disabledGrey)
    val onClickMain = ThemedColor(light = ColorAlias.lightGrey, dark = ColorAlias.lightGrey)
    val divider = ThemedColor(light = ColorAlias.veryLightGrey, dark = ColorAlias.grey)
    val theme = ThemedColor(light = ColorAlias.cyan, dark = ColorAlias.cyan)
}

private object ColorAlias {
    val black = Color(0xff000000)
    val darkGrey = Color(0xff23212C)
    val grey = Color(0xff3F424B)
    val disabledGrey = Color(0xffD8DAE1)
    val veryLightGrey = Color(0xffE5E6EA)
    val whiteGrey = Color(0xffF5F6FA)
    val lightGrey = Color(0xffFFFFFF)
    val blue = Color(0xff0009A9)
    val red = Color(0xffD93421)
    val cyan = Color(0xff5CB2CC)
}
    