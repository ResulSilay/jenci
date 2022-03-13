package com.x.presentation.ui.composable

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import com.x.presentation.R
import com.x.presentation.ui.theme.Theme
import com.x.presentation.util.getString

@Composable
fun CustomTextField(
    label: Int,
    text: MutableState<TextFieldValue>,
    placeholder: Int? = null,
    validation: Validation? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardActions: (text: String) -> Unit = {},
    trailingIcon: @Composable () -> Unit = {},
    modifier: Modifier,
) {
    Column(
        modifier = modifier,
    ) {
        TextField(
            value = text.value,
            onValueChange = { text.value = it },
            isError = validation?.isError?.value == true,
            shape = Theme.shapes.medium,
            singleLine = true,
            label = {
                Text(text = label.getString(), color = Theme.colors.secondaryContainer)
            },
            trailingIcon = {
                if (validation?.isError?.value == true) {
                    Icon(
                        imageVector = Icons.Filled.Error,
                        contentDescription = R.string.text_field_error_icon_content_description.getString(),
                        tint = Theme.colors.error
                    )
                } else {
                    trailingIcon()
                }
            },
            visualTransformation = visualTransformation,
            placeholder = {
                if (placeholder != null)
                    Text(text = placeholder.getString())
            },
            keyboardActions = KeyboardActions {
                keyboardActions(text.value.text)
            },
            modifier = Modifier
                .fillMaxSize(),
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Theme.colors.surfaceVariant.copy(alpha = 0.9f),
                textColor = Theme.colors.onSurface,
                placeholderColor = Theme.colors.onTertiaryContainer,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                cursorColor = Theme.colors.primary
            ),
        )

        if (validation?.isError?.value == true) {
            Text(
                text = validation.errorMessage,
                color = Theme.colors.error,
                modifier = Modifier.padding(
                    top = Theme.dimens.space.min,
                    end = Theme.dimens.space.min,
                    start = Theme.dimens.space.min
                )
            )
        }
    }
}

@JvmOverloads
@Composable
fun CustomTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    singleLine: Boolean = true,
    maxLines: Int = 1,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    modifier: Modifier = Modifier,
) {
    Column {
        TextField(
            value = value,
            onValueChange = { onValueChange(it) },
            shape = Theme.shapes.medium,
            singleLine = singleLine,
            maxLines = maxLines,
            visualTransformation = visualTransformation,
            label = {
                Text(text = label, color = Theme.colors.secondaryContainer)
            },
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Theme.colors.surfaceVariant.copy(alpha = 0.9f),
                textColor = Theme.colors.onSurface,
                placeholderColor = Theme.colors.onTertiaryContainer,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                cursorColor = Theme.colors.primary
            ),
            modifier = modifier.fillMaxSize()
        )
    }
}

data class Validation(
    val isError: MutableState<Boolean> = mutableStateOf(false),
    val errorMessage: String,
)