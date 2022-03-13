package com.x.presentation.ui.composable

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.x.presentation.R
import com.x.presentation.ui.theme.Theme
import com.x.presentation.util.getString

object EmptyStates {

    @Composable
    fun NetworkEmptyState(
        onTryAgainActionClick: () -> Unit = {},
    ) {
        EmptyStatesContent(
            title = R.string.empty_states_network_title,
            description = R.string.empty_states_network_description,
            image = R.drawable.ic_empty_states_network,
        ) {
            EmptyStatesAction(
                text = R.string.empty_states_action_try_again,
                onClick = onTryAgainActionClick
            )
        }
    }

    @Composable
    fun FailureEmptyState(
        onTryAgainActionClick: () -> Unit = {},
    ) {
        EmptyStatesContent(
            title = R.string.empty_states_failure_title,
            description = R.string.empty_states_failure_description,
            image = R.drawable.ic_empty_states_error
        ) {
            EmptyStatesAction(
                text = R.string.empty_states_action_try_again,
                onClick = onTryAgainActionClick
            )
        }
    }

    @Composable
    fun NotFoundEmptyState() {
        EmptyStatesContent(
            title = R.string.empty_states_not_found_title,
            description = R.string.empty_states_not_found_description,
            image = R.drawable.ic_empty_states_not_found
        ) {

        }
    }

    @Composable
    fun CustomEmptyState(
        title: Int,
        description: Int,
        image: Int,
        actionText: Int,
        onActionClick: () -> Unit = {},
    ) {
        EmptyStatesContent(
            title = title,
            description = description,
            image = image
        ) {
            EmptyStatesAction(
                text = actionText,
                onClick = onActionClick
            )
        }
    }

    @Composable
    fun CustomEmptyState(
        title: Int,
        description: Int,
        image: Int,
        content: @Composable () -> Unit = {},
    ) {
        EmptyStatesContent(
            title = title,
            description = description,
            image = image
        ) {
            content()
        }
    }

    @Composable
    fun EmptyStatesContent(
        @StringRes title: Int,
        @StringRes description: Int,
        @DrawableRes image: Int,
        content: @Composable () -> Unit,
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .background(Theme.colors.background)
                .fillMaxSize()
                .padding(
                    bottom = Theme.dimens.space.large
                )
        ) {
            Image(
                painter = painterResource(id = image),
                contentDescription = title.getString(),
                Modifier
                    .size(200.dp)
            )
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = title.getString(),
                    color = Theme.colors.primary,
                    style = Theme.typography.headlineMedium
                )
                Text(
                    text = description.getString(),
                    color = Theme.colors.onPrimaryContainer,
                    modifier = Modifier.padding(
                        all = Theme.dimens.space.medium
                    )
                )

                content()
            }
        }
    }

    @Composable
    private fun EmptyStatesAction(
        text: Int,
        onClick: () -> Unit,
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    all = Theme.dimens.space.medium
                )
        ) {
            Button(
                onClick = onClick,
                modifier = Modifier
                    .padding(
                        start = Theme.dimens.space.large,
                        end = Theme.dimens.space.large,
                    )
            ) {
                Text(
                    text = text.getString(),
                    color = Theme.colors.onSecondary
                )
            }
        }
    }
}