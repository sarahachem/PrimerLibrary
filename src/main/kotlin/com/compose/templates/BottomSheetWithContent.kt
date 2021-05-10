package com.compose.templates

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.shape.ZeroCornerSize
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Text
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.navigationBarsWithImePadding
import com.compose.component.FilledButton
import com.compose.component.input.FormInputField
import com.compose.component.input.InputType
import com.compose.theme.FourDp
import com.compose.theme.TwelveDp
import com.compose.theme.DarkThemePreviewParamProvider
import com.compose.theme.PrimerTheme

@ExperimentalMaterialApi
@Composable
fun BottomSheetWithContent(
    modifier: Modifier = Modifier,
    state: ModalBottomSheetState,
    content: @Composable () -> Unit,
    backgroundContent: @Composable () -> Unit
) {
    ModalBottomSheetLayout(
        sheetState = state,
        sheetShape = BottomSheetShape,
        modifier = modifier,
        sheetBackgroundColor = Color.Transparent,
        sheetContent = {
            Column(
                modifier = Modifier
                    .background(color = MaterialTheme.colors.surface)
                    .navigationBarsWithImePadding()
            ) {
                Spacer(modifier = Modifier.height(FourDp))
                RoundedDivider(modifier = Modifier.align(Alignment.CenterHorizontally))
                Spacer(modifier = Modifier.height(FourDp))
                Spacer(modifier = Modifier.height(FourDp))
                Box(modifier = Modifier.verticalScroll(rememberScrollState())) {
                    content()
                }
            }
        },
        content = backgroundContent
    )
}

val BottomSheetShape = RoundedCornerShape(
    topStart = CornerSize(4.dp),
    topEnd = CornerSize(4.dp),
    bottomStart = ZeroCornerSize,
    bottomEnd = ZeroCornerSize
)

@Composable
private fun RoundedDivider(modifier: Modifier) = Box(
    modifier = Modifier
        .fillMaxWidth(0.2f)
        .height(5.dp)
        .background(color = PrimerTheme.colors.divider, shape = RoundedCornerShape(5.dp))
        .then(modifier)
)

@OptIn(ExperimentalMaterialApi::class)
@Preview(name = "SampleBottomSheet", showBackground = true)
@Composable
fun PreviewBottomSheetHello(@PreviewParameter(DarkThemePreviewParamProvider::class) isDarkTheme: Boolean) {
    val bottomSheetState = rememberModalBottomSheetState(ModalBottomSheetValue.HalfExpanded)
    PrimerTheme(darkTheme = isDarkTheme) {
        BottomSheetWithContent(
            state = bottomSheetState,
            content = {
                Column {
                    FormInputField(
                        type = InputType.Text,
                        label = "Hello",
                        text = "Hello"
                    ) {}
                    Spacer(modifier = Modifier.height(TwelveDp))
                    FilledButton(text = "Continue", onClick = { })
                }
            }
        ) {
            Text(text = "What's up?")
        }
    }
}
