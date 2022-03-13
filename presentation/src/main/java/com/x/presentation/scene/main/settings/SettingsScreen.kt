package com.x.presentation.scene.main.settings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.x.presentation.R
import com.x.presentation.scene.main.settings.SettingsContract.Event
import com.x.presentation.ui.composable.CircularLoading
import com.x.presentation.ui.composable.CustomDivider
import com.x.presentation.ui.composable.EmptyStates.FailureEmptyState
import com.x.presentation.ui.composable.EmptyStates.NetworkEmptyState
import com.x.presentation.ui.composable.EmptyStates.NotFoundEmptyState
import com.x.presentation.ui.composable.MainAppBar
import com.x.presentation.ui.composable.Resource
import com.x.presentation.ui.theme.Theme
import com.x.presentation.util.getString
import com.x.presentation.util.share
import com.x.presentation.util.start

@ExperimentalMaterial3Api
@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel(),
    navigatePeople: () -> Unit,
    navigateProfile: () -> Unit,
    onRestart: () -> Unit,
) {
    val context = LocalContext.current
    val state = viewModel.state.collectAsState()
    val logoutModelResource = state.value.responseModel

    val shareMessage = R.string.share_play_store_text.getString()

    SettingsBody(
        onPeopleClick = navigatePeople,
        onProfileClick = navigateProfile,
        onGithubClick = {
            context.start(url = "https://github.com/ResulSilay/jenci")
        },
        onShareClick = {
            context.share(text = shareMessage + "https://play.google.com/store/apps/details?id=${context.packageName}")
        },
        onRateUsClick = {
            context.start(url = "market://details?id=${context.packageName}")
        }
    ) {
        viewModel.publish(event = Event.OnLogout)
    }

    Resource(
        resource = logoutModelResource,
        onSuccess = {
            viewModel.cacheManager().clear()
            onRestart()
        },
        notFound = {
            NotFoundEmptyState()
        },
        onLoading = {
            CircularLoading()
        },
        onNetwork = {
            NetworkEmptyState(
                onTryAgainActionClick = {
                    viewModel.publish(event = Event.OnLogout)
                }
            )
        },
        onFailure = {
            FailureEmptyState(
                onTryAgainActionClick = {
                    viewModel.cacheManager().clear()
                    onRestart()
                }
            )
        }
    )
}

@ExperimentalMaterial3Api
@Composable
fun SettingsBody(
    onPeopleClick: () -> Unit,
    onProfileClick: () -> Unit,
    onGithubClick: () -> Unit,
    onShareClick: () -> Unit,
    onRateUsClick: () -> Unit,
    onLogoutClick: () -> Unit,
) {
    Scaffold(
        topBar = {
            MainAppBar(
                title = R.string.settings_header_text.getString(),
                backgroundColor = Theme.colors.onSecondary
            )
        },
        content = {
            LazyColumn {
                item {
                    SettingsGroup(
                        title = R.string.settings_group_general_text.getString(),
                        content = {
                            SettingsGroupItem(
                                icon = Icons.Outlined.People,
                                text = R.string.settings_group_general_people_text.getString(),
                                onItemClick = onPeopleClick
                            )
                            SettingsGroupItem(
                                icon = Icons.Outlined.Person,
                                text = R.string.settings_group_general_profile_text.getString(),
                                onItemClick = onProfileClick
                            )
                        }
                    )
                }
                item {
                    SettingsGroup(
                        title = R.string.settings_group_about_text.getString(),
                        content = {
                            SettingsGroupItem(
                                icon = Icons.Outlined.DeviceHub,
                                text = R.string.settings_group_about_github_text.getString(),
                                onItemClick = onGithubClick
                            )
                            SettingsGroupItem(
                                icon = Icons.Outlined.Share,
                                text = R.string.settings_group_about_share_text.getString(),
                                onItemClick = onShareClick
                            )
                            SettingsGroupItem(
                                icon = Icons.Outlined.StarRate,
                                text = R.string.settings_group_about_rate_us_text.getString(),
                                onItemClick = onRateUsClick
                            )
                        }
                    )
                }
                item {
                    SettingsGroup(
                        title = R.string.settings_group_session_text.getString(),
                        content = {
                            SettingsGroupItem(
                                icon = Icons.Outlined.Logout,
                                text = R.string.settings_group_session_logout_text.getString(),
                                onItemClick = onLogoutClick
                            )
                        }
                    )
                }
            }
        }
    )
}

@Composable
fun SettingsGroup(
    title: String,
    content: @Composable () -> Unit,
) {
    Column {
        SettingsGroupHeader(title = title)
        Card(
            shape = Theme.shapes.none,
            elevation = Theme.dimens.elevation.medium,
            backgroundColor = Theme.colors.onSecondary,
            modifier = Modifier
                .paddingFromBaseline(
                    bottom = Theme.dimens.space.medium
                )
        ) {
            Column {
                content()
            }
        }
    }
}

@Composable
fun SettingsGroupHeader(
    title: String,
) {
    Text(
        text = title,
        color = Theme.colors.onSurfaceVariant,
        modifier = Modifier.padding(
            start = Theme.dimens.space.medium,
            end = Theme.dimens.space.medium,
            top = Theme.dimens.space.medium,
            bottom = Theme.dimens.space.small
        )
    )
}

@Composable
fun SettingsGroupItem(
    icon: ImageVector,
    text: String,
    onItemClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .clickable {
                onItemClick()
            }
    ) {
        Row(
            modifier = Modifier
                .padding(Theme.dimens.space.medium)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = text,
                tint = Theme.colors.onSurface)
            Text(
                text = text,
                Modifier
                    .padding(
                        horizontal = Theme.dimens.space.smallExtra
                    )
            )
        }
        CustomDivider()
    }
}
