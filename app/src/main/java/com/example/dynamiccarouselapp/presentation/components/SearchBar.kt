package com.example.dynamiccarouselapp.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import com.example.dynamiccarouselapp.R
import com.example.dynamiccarouselapp.core.util.Dimens
import com.example.dynamiccarouselapp.ui.theme.GrayColor

@Composable
fun SearchBar(
    query: String,
    onQueryChanged: (String) -> Unit
) {
    val focusManager = LocalFocusManager.current
    val keyboardController = LocalSoftwareKeyboardController.current

    OutlinedTextField(
        value = query,
        onValueChange = onQueryChanged,
        placeholder = { Text(stringResource(R.string.search_placeholder), color = Color.Gray) },
        leadingIcon = {
            Icon(
                Icons.Default.Search,
                contentDescription = stringResource(R.string.search_icon_description),
                tint = Color.Gray
            )
        },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = Dimens.PaddingS)
            .clip(RoundedCornerShape(Dimens.FieldCornerRadius))
            .background(GrayColor),
        shape = RoundedCornerShape(Dimens.FieldCornerRadius),
        singleLine = true,
        colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = GrayColor,
            unfocusedBorderColor = GrayColor,
            cursorColor = Color.Black,
            unfocusedContainerColor = Color.Transparent,
            focusedContainerColor = Color.Transparent
        ),
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Search),
        keyboardActions = KeyboardActions(
            onSearch = {
                keyboardController?.hide()
                focusManager.clearFocus()
            }
        )
    )
}
