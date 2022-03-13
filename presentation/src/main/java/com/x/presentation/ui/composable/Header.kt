package com.x.presentation.ui.composable

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.x.presentation.ui.theme.Theme

@Composable
fun Header(
    title: String,
    content: @Composable () -> Unit? = {},
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .padding(bottom = Theme.dimens.space.small)
            .fillMaxWidth()
    ) {
        Row(
            modifier = Modifier.padding(
                start = Theme.dimens.space.medium,
                end = Theme.dimens.space.medium
            )
        ) {
            Text(
                text = title,
                fontSize = Theme.dimens.text.medium,
                textAlign = TextAlign.Start,
                color = Theme.colors.onSurfaceVariant,
            )
            content()
        }
    }
}