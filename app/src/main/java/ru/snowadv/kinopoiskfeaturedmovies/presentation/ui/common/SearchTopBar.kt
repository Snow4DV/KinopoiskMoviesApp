package ru.snowadv.kinopoiskfeaturedmovies.presentation.ui.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.TextFieldDefaults.TextFieldDecorationBox
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.nativeKeyCode
import androidx.compose.ui.input.key.onKeyEvent
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.snowadv.kinopoiskfeaturedmovies.R

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SearchTopBar(
    modifier: Modifier = Modifier,
    searchMode: Boolean,
    title: String,
    onSwitchMode: (Boolean) -> Unit,
    textFieldValue: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit
) {

    val focusRequester = remember { FocusRequester() }
    val interactionSource = remember { MutableInteractionSource() }

    LaunchedEffect(searchMode) {
        if(searchMode) {
            focusRequester.requestFocus()
            onValueChange(textFieldValue.copy(selection = TextRange(textFieldValue.text.length)))
        }
    }

    Row(
        modifier = modifier
            .padding(top = 20.dp, start = 20.dp, end = 20.dp, bottom = 0.dp)
            .padding(top = 5.dp),
        verticalAlignment = Alignment.Bottom
    ) {
        if(searchMode) {
            Icon(
                modifier = Modifier
                    .height(with(LocalDensity.current) { 29.sp.toDp() })
                    .padding(end = 8.dp)
                    .clickable(onClick = {onSwitchMode(false)}),
                imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                contentDescription = stringResource(R.string.search),
                tint = colorResource(id = R.color.primary)
            )
            BasicTextField(
                textStyle = TextStyle.Default.copy(fontSize = 26.sp),
                modifier = Modifier.focusRequester(focusRequester)
                    .onKeyEvent { event ->
                        if (event.key.nativeKeyCode == android.view.KeyEvent.KEYCODE_BACK) {
                            onSwitchMode(false)
                            true
                        } else {
                            false
                        }
                    },
                value = textFieldValue,
                onValueChange = onValueChange,
                interactionSource = interactionSource,
                maxLines = 1,
                decorationBox = {
                    TextFieldDecorationBox(
                        value = textFieldValue.text,
                        innerTextField = it,
                        enabled = true,
                        singleLine = true,
                        visualTransformation = VisualTransformation.None,
                        interactionSource = interactionSource,
                        contentPadding = PaddingValues(0.dp),
                        placeholder = {
                            Text(
                                color = Color.Gray,
                                fontSize = 25.sp,
                                text = stringResource(id = R.string.search)
                            )
                        }
                    )
                }

            )
        } else {
            Text(
                modifier = Modifier
                    .weight(1.0f)
                    .padding(end = 5.dp),
                fontSize = 25.sp,
                fontWeight = FontWeight.SemiBold,
                text = title
            )
            Icon(
                modifier = Modifier
                    .height(with(LocalDensity.current) { 29.sp.toDp() })
                    .clickable(onClick = {onSwitchMode(true)}),
                imageVector = Icons.Filled.Search,
                contentDescription = stringResource(R.string.search),
                tint = colorResource(id = R.color.primary)
            )
        }
    }
}

@Preview
@Composable
fun SearchTopBarPreviewWithTitle() {
    SearchTopBar(
        modifier = Modifier.width(420.dp),
        searchMode = false,
        onSwitchMode = {},
        title = "Популярные",
        textFieldValue = TextFieldValue(),
        onValueChange = {}
    )
}

@Preview
@Composable
fun SearchTopBarPreviewWithSearchBar() {
    SearchTopBar(
        modifier = Modifier.width(420.dp),
        searchMode = true,
        onSwitchMode = {},
        title = "Популярные",
        textFieldValue = TextFieldValue("Фильм"),
        onValueChange = {}
    )
}

@Preview
@Composable
fun SearchTopBarPreviewWithSearchBarEmpty() {
    SearchTopBar(
        modifier = Modifier.width(420.dp),
        searchMode = true,
        onSwitchMode = {},
        title = "Популярные",
        textFieldValue = TextFieldValue(),
        onValueChange = {}
    )
}