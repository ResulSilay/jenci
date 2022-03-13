package com.x.presentation.scene.main.dashboard

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.flowlayout.FlowMainAxisAlignment
import com.google.accompanist.flowlayout.FlowRow
import com.google.accompanist.flowlayout.SizeMode
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.x.common.type.DashboardStatsType
import com.x.common.type.JenkinsActionType
import com.x.domain.model.*
import com.x.presentation.R
import com.x.presentation.scene.main.dashboard.DashboardContract.Event
import com.x.presentation.scene.main.queue.QueueItem
import com.x.presentation.ui.composable.*
import com.x.presentation.ui.composable.EmptyStates.CustomEmptyState
import com.x.presentation.ui.composable.EmptyStates.FailureEmptyState
import com.x.presentation.ui.composable.EmptyStates.NetworkEmptyState
import com.x.presentation.ui.composable.EmptyStates.NotFoundEmptyState
import com.x.presentation.ui.theme.Shapes
import com.x.presentation.ui.theme.Theme
import com.x.presentation.util.*

@ExperimentalMaterialApi
@ExperimentalMaterial3Api
@ExperimentalFoundationApi
@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel = hiltViewModel(),
    navigateStatsClick: (dashboardStatsType: DashboardStatsType) -> Unit,
    navigateQueueDetail: (id: Int) -> Unit,
    navigateProfileClick: () -> Unit,
) {
    val state = viewModel.state.collectAsState()
    val dashboardModelResource = state.value.statsModel
    val actionModelResource = state.value.actionModel
    val isRefreshing = state.value.isRefreshing

    val isChartLine = remember { mutableStateOf(false) }
    val selectedActionType = remember { mutableStateOf(JenkinsActionType.NONE) }
    val accountModel = viewModel.cacheManager().getModel(AccountModel::class)

    DisposableEffect(Unit) {
        viewModel.publish(event = Event.GetStats)
        onDispose {}
    }

    Resource(
        resource = dashboardModelResource,
        onSuccess = { statsModel ->
            DashboardBody(
                statsModel = statsModel,
                accountModel = accountModel,
                isRefreshing = isRefreshing,
                isChartLine = isChartLine,
                selectedJenkinsActionType = selectedActionType,
                navigateStatsClick = navigateStatsClick,
                navigateQueueDetail = navigateQueueDetail,
                navigateProfileClick = navigateProfileClick,
                onRefresh = {
                    viewModel.publish(event = Event.GetStats)
                },
                onDialogConfirm = { actionType ->
                    when (actionType) {
                        JenkinsActionType.RESTART -> viewModel.publish(event = Event.ActionRestart)
                        JenkinsActionType.SAFE_RESTART -> viewModel.publish(event = Event.ActionSafeRestart)
                        JenkinsActionType.SHUTDOWN -> viewModel.publish(event = Event.ActionShutdown)
                        JenkinsActionType.SAFE_SHUTDOWN -> viewModel.publish(event = Event.ActionSafeShutdown)
                        JenkinsActionType.QUIET_DOWN -> viewModel.publish(event = Event.ActionQuietDown)
                        JenkinsActionType.CANCEL_QUIET_DOWN -> viewModel.publish(event = Event.ActionCancelQuietDown)
                        JenkinsActionType.NONE -> {}
                    }
                }
            )
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
                    viewModel.publish(event = Event.GetStats)
                }
            )
        },
        onFailure = {
            FailureEmptyState(
                onTryAgainActionClick = {
                    viewModel.publish(event = Event.GetStats)
                }
            )
        }
    )

    Resource(resource = actionModelResource,
        onSuccess = {
            DashboardActionEmptyState {
                viewModel.publish(event = Event.GetStats)
            }
        },
        notFound = {
            DashboardActionEmptyState {
                viewModel.publish(event = Event.GetStats)
            }
        },
        onLoading = {
            DashboardActionEmptyState {
                viewModel.publish(event = Event.GetStats)
            }
        },
        onNetwork = {
            DashboardActionEmptyState {
                viewModel.publish(event = Event.GetStats)
            }
        },
        onFailure = {
            DashboardActionEmptyState {
                viewModel.publish(event = Event.GetStats)
            }
        }
    )
}

@ExperimentalMaterialApi
@ExperimentalMaterial3Api
@ExperimentalFoundationApi
@Composable
private fun DashboardBody(
    statsModel: StatsModel?,
    accountModel: AccountModel?,
    isRefreshing: Boolean,
    isChartLine: MutableState<Boolean>,
    selectedJenkinsActionType: MutableState<JenkinsActionType>,
    navigateStatsClick: (dashboardStatsType: DashboardStatsType) -> Unit,
    navigateQueueDetail: (id: Int) -> Unit,
    navigateProfileClick: () -> Unit,
    onRefresh: () -> Unit,
    onDialogConfirm: (jenkinsActionType: JenkinsActionType) -> Unit,
) {
    Scaffold(
        content = {
            SwipeRefresh(
                state = rememberSwipeRefreshState(isRefreshing),
                onRefresh = onRefresh,
                indicator = { state, trigger ->
                    SwipeRefreshIndicator(
                        state = state,
                        refreshTriggerDistance = trigger,
                        backgroundColor = Theme.colors.background,
                        contentColor = Theme.colors.primary
                    )
                }
            ) {
                Column {
                    DashboardContent(
                        statsModel = statsModel,
                        accountModel = accountModel,
                        isChartLine = isChartLine,
                        onStatsItemClick = navigateStatsClick,
                        navigateQueueDetail = navigateQueueDetail,
                        navigateProfileClick = navigateProfileClick,
                        onActionClick = { actionType ->
                            selectedJenkinsActionType.value = actionType
                        }
                    )
                }
            }

            if (selectedJenkinsActionType.value != JenkinsActionType.NONE) {
                DashboardActionDialog(
                    jenkinsActionType = selectedJenkinsActionType.value,
                    onConfirm = onDialogConfirm,
                    onDismiss = {
                        selectedJenkinsActionType.value = JenkinsActionType.NONE
                    }
                )
            }
        }
    )
}

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun DashboardContent(
    statsModel: StatsModel?,
    accountModel: AccountModel?,
    isChartLine: MutableState<Boolean>,
    onStatsItemClick: (dashboardStatsType: DashboardStatsType) -> Unit,
    onActionClick: (jenkinsActionType: JenkinsActionType) -> Unit,
    navigateQueueDetail: (id: Int) -> Unit,
    navigateProfileClick: () -> Unit,
) {
    LazyColumn(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        item {
            DashboardTopBar(
                username = accountModel?.username.toFieldEmptyString(),
                onProfileClick = navigateProfileClick
            )
        }
        item {
            StatsGridCard(
                statsList = listOf(
                    StatsItemModel(
                        title = R.string.dashboard_stats_node_card_text.getString(),
                        value = statsModel?.nodeCount.toString()
                    ),
                    StatsItemModel(
                        title = R.string.dashboard_stats_view_card_text.getString(),
                        value = statsModel?.viewCount.toString()
                    ),
                    StatsItemModel(
                        title = R.string.dashboard_stats_queries_card_text.getString(),
                        value = statsModel?.queueCount.toString()
                    )
                ),
                onStatsItemClick = onStatsItemClick
            )
        }
        item {
            Header(
                title = R.string.dashboard_build_card_header_text.getString()
            ) {
                DashboardChartTypeSelection(
                    isChartLine = isChartLine
                )
            }
        }
        item {
            DashboardBuildChartCard(
                buildChartModel = statsModel?.buildChartList,
                isChartLine = isChartLine
            )
        }
        item {
            Header(
                title = R.string.dashboard_queue_card_header_text.getString()
            )
        }
        item {
            QueueCard(
                queueList = statsModel?.queueList,
                navigateQueueDetail = navigateQueueDetail
            )
        }
        item {
            Header(
                title = R.string.dashboard_action_card_header_text.getString()
            )
        }
        item {
            val actions = R.array.actions.getStringArray()
            ActionGridCard(
                actionList = listOf(
                    Pair(first = Icons.Outlined.RestartAlt, second = actions[0]),
                    Pair(first = Icons.Outlined.Shield, second = actions[1]),
                    Pair(first = Icons.Outlined.Power, second = actions[2]),
                    Pair(first = Icons.Outlined.PowerSettingsNew, second = actions[3]),
                    Pair(first = Icons.Outlined.Close, second = actions[4]),
                    Pair(first = Icons.Outlined.GppBad, second = actions[5]),
                ),
                onActionClick = onActionClick
            )
        }
    }
}

@Composable
fun DashboardTopBar(
    username: String,
    onProfileClick: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Theme.colors.background)
    ) {
        Row(
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(
                    start = Theme.dimens.space.medium,
                    end = Theme.dimens.space.medium,
                    top = Theme.dimens.space.large,
                    bottom = Theme.dimens.space.medium,
                )
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .weight(1F)
            ) {
                Text(
                    text = R.string.dashboard_user_welcome_text.getString(),
                    fontSize = Theme.dimens.text.xlarge
                )
                Text(
                    text = username,
                    fontSize = Theme.dimens.text.medium
                )
            }

            Column(
                horizontalAlignment = Alignment.End,
                modifier = Modifier
                    .weight(1F)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_default_profile),
                    contentDescription = R.string.app_name_logo_desc.getString(),
                    modifier = Modifier
                        .size(40.dp)
                        .clip(RoundedCornerShape(percent = 22))
                        .clickable {
                            onProfileClick()
                        }
                )
            }
        }
    }
}

@Composable
fun DashboardActionDialog(
    jenkinsActionType: JenkinsActionType,
    onConfirm: (jenkinsActionType: JenkinsActionType) -> Unit,
    onDismiss: () -> Unit,
) {
    CustomAlertDialog(
        title = R.string.action_dialog_title,
        message = R.string.action_dialog_desc,
        confirmText = R.string.dialog_yes,
        dismissText = R.string.dialog_cancel,
        onConfirm = {
            onConfirm(jenkinsActionType)
            onDismiss()
        },
        onDismiss = {
            onDismiss()
        }
    )
}

@Composable
fun DashboardActionEmptyState(
    onAction: () -> Unit,
) {
    CustomEmptyState(
        title = R.string.empty_states_jenkins_action_title,
        description = R.string.empty_states_jenkins_action_desc,
        image = R.drawable.ic_empty_states_done,
        actionText = R.string.empty_states_jenkins_action_text,
        onActionClick = {
            onAction()
        }
    )
}

@Composable
fun DashboardBuildChartCard(
    buildChartModel: BuildChartModel?,
    isChartLine: MutableState<Boolean>,
) {
    Card(
        shape = Theme.shapes.none,
        elevation = Theme.dimens.elevation.none,
        backgroundColor = Theme.colors.background,
        modifier = Modifier
            .padding(
                start = Theme.dimens.space.small,
                bottom = Theme.dimens.space.medium
            )
    ) {
        val (x, y) = buildChartModel.chartData()

        if (isChartLine.value) {
            Chart(isChartLine = true, x = x, y = y)
        } else {
            Chart(isChartLine = false, x = x, y = y)
        }

        if (!buildChartModel.isNotEmpty()) {
            RowCenter {
                Text(
                    text = R.string.dashboard_chart_not_found_message.getString(),
                    textAlign = TextAlign.Center,
                    color = Theme.colors.onPrimaryContainer,
                    modifier = Modifier
                        .padding(
                            all = Theme.dimens.space.medium
                        )
                        .fillMaxWidth()
                )
            }
        }
    }
}

@Composable
fun DashboardChartTypeSelection(
    isChartLine: MutableState<Boolean>,
) {
    MultiToggleButton(
        currentSelection = ChartType.LINE.value.getString(),
        toggleStates = listOf(
            ChartType.BAR.value.getString(),
            ChartType.LINE.value.getString()
        ),
        onToggleChange = {
            when (it) {
                0 -> isChartLine.value = true
                1 -> isChartLine.value = false
            }
        }
    )
}

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun StatsGridCard(
    statsList: List<StatsItemModel>,
    onStatsItemClick: (dashboardStatsType: DashboardStatsType) -> Unit,
) {
    Column(
        modifier = Modifier.paddingFromBaseline(
            bottom = Theme.dimens.space.xxlarge
        )
    ) {
        Card(
            shape = Theme.shapes.none,
            backgroundColor = Theme.colors.background,
            elevation = Theme.dimens.elevation.none,
        ) {
            Column(
                modifier = Modifier.padding(
                    bottom = Theme.dimens.space.min
                )
            ) {
                Row(
                    modifier = Modifier.padding(
                        all = Theme.dimens.space.small
                    )
                ) {
                    statsList.forEachIndexed { index, item ->
                        Column(modifier = Modifier.weight(1F)) {
                            StatsItemCard(
                                index = index,
                                item = item,
                                onStatsItemClick = onStatsItemClick
                            )
                        }
                    }
                }
            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun StatsItemCard(
    index: Int,
    item: StatsItemModel,
    onStatsItemClick: (dashboardStatsType: DashboardStatsType) -> Unit,
) {
    Card(
        backgroundColor = Theme.colors.surfaceVariant,
        shape = Shapes.medium,
        elevation = Theme.dimens.elevation.medium,
        onClick = {
            DashboardStatsType
                .get(index)
                ?.let { onStatsItemClick(it) }
        },
        modifier = Modifier
            .padding(all = Theme.dimens.space.xMin)
            .wrapContentHeight(Alignment.CenterVertically)
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxSize()
                .padding(all = Theme.dimens.space.small)
        ) {
            Text(
                text = item.value,
                fontSize = Theme.dimens.text.xlarge,
                modifier = Modifier
                    .padding(all = Theme.dimens.space.small)
            )
            Text(
                text = item.title,
                color = Theme.colors.onSurfaceVariant,
                modifier = Modifier
                    .padding(Theme.dimens.space.small)
            )
        }
    }
}

@Composable
fun QueueCard(
    queueList: List<QueueItemModel>?,
    navigateQueueDetail: (id: Int) -> Unit,
) {
    Card(
        shape = Theme.shapes.medium,
        elevation = Theme.dimens.elevation.medium,
        backgroundColor = Theme.colors.surfaceVariant,
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                start = Theme.dimens.space.smallExtra,
                end = Theme.dimens.space.smallExtra,
                bottom = Theme.dimens.space.large
            )
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (queueList != null && queueList.isNotEmpty()) {
                DashboardQueueList(
                    queueList = queueList,
                    navigateQueueDetail = navigateQueueDetail
                )
            } else {
                Text(
                    text = R.string.dashboard_queue_card_empty_state_text.getString(),
                    textAlign = TextAlign.Center,
                    color = Theme.colors.onSurfaceVariant,
                    modifier = Modifier
                        .padding(
                            all = Theme.dimens.space.medium
                        )
                )
            }
        }
    }
}

@Composable
fun DashboardQueueList(
    queueList: List<QueueItemModel>?,
    navigateQueueDetail: (id: Int) -> Unit,
) {
    queueList?.let { queues ->
        queues.forEachIndexed { _, queueItemModel ->
            Column {
                QueueItem(
                    queueItemModel = queueItemModel,
                    navigateQueueDetail = navigateQueueDetail
                )
                CustomDivider(color = Theme.colors.inverseOnSurface)
            }
        }
    }
}

@ExperimentalFoundationApi
@Composable
fun ActionGridCard(
    actionList: List<Pair<ImageVector, String>>,
    onActionClick: (jenkinsActionType: JenkinsActionType) -> Unit,
) {
    Card(
        shape = Theme.shapes.medium,
        elevation = Theme.dimens.elevation.medium,
        backgroundColor = Theme.colors.surfaceVariant,
        modifier = Modifier
            .padding(
                start = Theme.dimens.space.smallExtra,
                end = Theme.dimens.space.smallExtra,
                bottom = Theme.dimens.space.large
            )
    ) {
        Column {
            val itemSize: Dp = (LocalConfiguration.current.screenWidthDp.dp - 20.dp) / 2

            FlowRow(
                mainAxisAlignment = FlowMainAxisAlignment.Center,
                mainAxisSize = SizeMode.Expand,
            ) {
                actionList.forEachIndexed { index, item ->
                    ActionItemCard(
                        index = index,
                        itemSize = itemSize,
                        icon = item.first,
                        actionText = item.second,
                        onActionClick = onActionClick
                    )
                }
            }
        }
    }
}

@Composable
fun ActionItemCard(
    itemSize: Dp,
    index: Int,
    icon: ImageVector,
    actionText: String,
    onActionClick: (jenkinsActionType: JenkinsActionType) -> Unit,
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(itemSize)
            .clickable {
                JenkinsActionType
                    .get(value = index)
                    ?.let { onActionClick(it) }
            }
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .padding(Theme.dimens.space.medium)
        ) {
            Icon(
                imageVector = icon,
                contentDescription = R.string.dashboard_action_icon_desc.getString(),
                tint = Theme.colors.onSurface
            )
            Text(
                text = actionText,
                textAlign = TextAlign.Center,
                fontSize = Theme.dimens.text.normal,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(
                    top = Theme.dimens.space.small
                )
            )
        }
        CustomDivider(color = Theme.colors.inverseOnSurface)
    }
}