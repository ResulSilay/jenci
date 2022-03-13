package com.x.presentation.ui.composable

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.x.presentation.ui.theme.Theme

@Composable
fun MultiToggleButton(
    currentSelection: String,
    toggleStates: List<String>,
    onToggleChange: (Int) -> Unit,
) {
    var selectedOption by remember {
        mutableStateOf(currentSelection)
    }

    val onSelectionChange = { text: String ->
        selectedOption = text
    }

    Row(
        horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        toggleStates.forEachIndexed { index, text ->
            Row(
                horizontalArrangement = Arrangement.End,
            ) {
                Text(
                    text = text,
                    modifier = Modifier
                        .padding(
                            all = Theme.dimens.space.small
                        )
                        .clickable {
                            onToggleChange(index)
                            onSelectionChange(text)
                        },
                    fontSize = Theme.dimens.text.small,
                    color = if (text == selectedOption) {
                        Theme.colors.onPrimaryContainer
                    } else {
                        Theme.colors.onSurfaceVariant
                    }
                )
            }
        }
    }
}