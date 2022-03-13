package com.x.presentation.ui.composable

import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.x.presentation.R
import com.x.presentation.ui.theme.Theme
import com.x.presentation.util.getString

@Composable
fun MainAppBar(
    title: String,
    backgroundColor: Color = Color.Transparent,
    contentColor: Color = Theme.colors.surface,
) {
    TopAppBar(
        title = { Text(text = title) },
        backgroundColor = backgroundColor,
        contentColor = contentColorFor(backgroundColor = contentColor),
        elevation = Theme.dimens.elevation.none
    )
}

@Composable
fun MainAppBar(
    title: String? = null,
    backgroundColor: Color = Color.Transparent,
    contentColor: Color = Theme.colors.surface,
    navigateUp: () -> Unit,
) {
    TopAppBar(
        title = {
            title?.let {
                Text(text = it)
            }
        },
        backgroundColor = backgroundColor,
        contentColor = contentColorFor(backgroundColor = contentColor),
        elevation = Theme.dimens.elevation.none,
        navigationIcon = {
            IconButton(
                onClick = {
                    navigateUp()
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = R.string.app_bar_back_content_description.getString()
                )
            }
        }
    )
}

@Composable
fun MainAppBar(
    titleContent: @Composable () -> Unit,
    backgroundColor: Color = Color.Transparent,
    contentColor: Color = MaterialTheme.colorScheme.surface,
) {
    TopAppBar(
        title = titleContent,
        backgroundColor = backgroundColor,
        contentColor = contentColorFor(backgroundColor = contentColor),
        elevation = Theme.dimens.elevation.none,
    )
}

@Composable
fun MainAppBar(
    titleContent: @Composable () -> Unit,
    backgroundColor: Color = Color.Transparent,
    contentColor: Color = Theme.colors.surface,
    navigateUp: () -> Unit?,
) {
    TopAppBar(
        title = titleContent,
        backgroundColor = backgroundColor,
        contentColor = contentColorFor(backgroundColor = contentColor),
        elevation = Theme.dimens.elevation.none,
        navigationIcon = {
            IconButton(onClick = { navigateUp() }) {
                Icon(
                    imageVector = Icons.Filled.ArrowBack,
                    contentDescription = R.string.app_bar_back_content_description.getString()
                )
            }
        }
    )
}