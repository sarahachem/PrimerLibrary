package com.compose.templates

import androidx.activity.OnBackPressedDispatcherOwner
import androidx.activity.compose.BackHandler
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.ExperimentalAnimatedInsets
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.insets.navigationBarsWithImePadding
import com.google.accompanist.insets.statusBarsPadding
import com.compose.theme.TwelveDp
import com.compose.theme.TwentyFourDp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@OptIn(ExperimentalAnimatedInsets::class)
@Composable
fun MainLayoutWithBottomSheet(
    scope: CoroutineScope,
    onBackPressedDispatcherOwner: OnBackPressedDispatcherOwner,
    onBackClicked: () -> Unit,
    bodyContentPadding: PaddingValues = PaddingValues(),
    mainButton: @Composable () -> Unit = { },
    backgroundColor: Color = MaterialTheme.colors.background,
    sheetState: ModalBottomSheetState,
    sheetContent: @Composable () -> Unit,
    bodyContent: @Composable ColumnScope.() -> Unit
) {
    ProvideWindowInsets(consumeWindowInsets = false) {
        CompositionLocalProvider(LocalOnBackPressedDispatcherOwner provides onBackPressedDispatcherOwner) {
            BackHandler {
                if (sheetState.isVisible.not()) {
                    scope.launch { sheetState.show() }
                } else {
                    onBackClicked()
                }
            }
            BottomSheetWithContent(
                modifier = Modifier.statusBarsPadding(),
                state = sheetState,
                content = sheetContent
            ) {
                MainPresentationScaffold(
                    contentModifier = Modifier.padding(top = AppBarHeight),
                    bodyContentPadding = bodyContentPadding,
                    mainButton = mainButton,
                    backgroundColor = backgroundColor,
                    shouldReactToKeyboardPadding = false,
                    bodyContent = bodyContent
                )
            }
        }
    }
}

val AppBarHeight = 56.dp

@Composable
private fun MainPresentationScaffold(
    modifier: Modifier = Modifier,
    contentModifier: Modifier = Modifier,
    bodyContentPadding: PaddingValues = PaddingValues(),
    mainButton: @Composable () -> Unit = { },
    backgroundColor: Color = MaterialTheme.colors.background,
    shouldReactToKeyboardPadding: Boolean = true,
    bodyContent: @Composable ColumnScope.() -> Unit
) {
    Scaffold(
        modifier = modifier,
        backgroundColor = backgroundColor,
    ) {
        Column(Modifier.fillMaxSize()) {
            Box(
                Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                Column(
                    Modifier
                        .verticalScroll(rememberScrollState())
                        .fillMaxSize()
                        .then(contentModifier)
                ) {
                    Column(
                        Modifier
                            .fillMaxWidth()
                            .padding(bodyContentPadding)
                    ) {
                        bodyContent()
                    }
                }
            }
            MainButtonBar(
                shouldReactToKeyboardPadding = shouldReactToKeyboardPadding,
                mainButton = mainButton,
            )
        }
    }
}

@Composable
private fun MainButtonBar(
    shouldReactToKeyboardPadding: Boolean = true,
    mainButton: @Composable () -> Unit = { },
) {
    Column(
        Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colors.surface)
            .then(if (shouldReactToKeyboardPadding) Modifier.navigationBarsWithImePadding() else Modifier.navigationBarsPadding())
            .padding(
                start = TwentyFourDp,
                end = TwentyFourDp,
                bottom = TwentyFourDp,
                top = TwelveDp
            )
    ) {
        mainButton()
    }
}
