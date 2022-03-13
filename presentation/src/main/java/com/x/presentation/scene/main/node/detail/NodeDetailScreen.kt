package com.x.presentation.scene.main.node.detail


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFromBaseline
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.x.domain.model.NodeDetailModel
import com.x.presentation.R
import com.x.presentation.scene.main.node.detail.NodeDetailContract.Event
import com.x.presentation.ui.composable.CircularLoading
import com.x.presentation.ui.composable.CustomDivider
import com.x.presentation.ui.composable.EmptyStates.FailureEmptyState
import com.x.presentation.ui.composable.EmptyStates.NetworkEmptyState
import com.x.presentation.ui.composable.EmptyStates.NotFoundEmptyState
import com.x.presentation.ui.composable.MainAppBar
import com.x.presentation.ui.composable.Resource
import com.x.presentation.ui.theme.Theme
import com.x.presentation.util.getString

@ExperimentalMaterial3Api
@Composable
fun NodeDetailScreen(
    viewModel: NodeDetailViewModel = hiltViewModel(),
    nodeLabel: String,
    navigateUp: () -> Unit,
) {
    val state = viewModel.state.collectAsState()
    val nodeDetailModelResource = state.value.nodeDetailModel

    LaunchedEffect(Unit) {
        viewModel.publish(event = Event.GetNode(nodeName = nodeLabel))
    }

    Resource(
        resource = nodeDetailModelResource,
        onSuccess = { nodeDetailModel ->
            NodeDetailBody(
                nodeDetailModel = nodeDetailModel,
                navigateUp = navigateUp
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
                    viewModel.publish(event = Event.GetNode(nodeName = nodeLabel))
                }
            )
        },
        onFailure = {
            FailureEmptyState(
                onTryAgainActionClick = {
                    viewModel.publish(event = Event.GetNode(nodeName = nodeLabel))
                }
            )
        }
    )
}

@ExperimentalMaterial3Api
@Composable
fun NodeDetailBody(
    nodeDetailModel: NodeDetailModel?,
    navigateUp: () -> Unit,
) {
    Scaffold(
        topBar = {
            MainAppBar(
                title = R.string.node_detail_header.getString(),
                backgroundColor = Theme.colors.onSecondary,
                navigateUp = navigateUp
            )
        },
        content = {
            nodeDetailModel?.let { nodeItemModel ->
                NodeDetailContent(
                    nodeDetailModel = nodeItemModel
                )
            }
        }
    )
}

@Composable
fun NodeDetailContent(
    nodeDetailModel: NodeDetailModel,
) {
    Card(
        shape = Theme.shapes.none,
        elevation = Theme.dimens.elevation.medium,
        backgroundColor = Theme.colors.onSecondary,
        modifier = Modifier
            .paddingFromBaseline(
                bottom = Theme.dimens.space.medium
            )
    ) {
        LazyColumn {
            item {
                NodeDetailItem(
                    label = R.string.node_detail_item_name_text.getString(),
                    value = nodeDetailModel.displayName
                )
            }
            item {
                NodeDetailItem(
                    label = R.string.node_detail_item_desc_text.getString(),
                    value = nodeDetailModel.description
                )
            }
            item {
                NodeDetailItem(
                    label = R.string.node_detail_item_idle_text.getString(),
                    value = nodeDetailModel.idle?.toString()
                )
            }
            item {
                NodeDetailItem(
                    label = R.string.node_detail_item_jnlp_agent_text.getString(),
                    value = nodeDetailModel.jnlpAgent?.toString()
                )
            }
            item {
                NodeDetailItem(
                    label = R.string.node_detail_item_launch_supported_text.getString(),
                    value = nodeDetailModel.launchSupported?.toString()
                )
            }
            item {
                NodeDetailItem(
                    label = R.string.node_detail_item_manuel_launch_allowed_text.getString(),
                    value = nodeDetailModel.manualLaunchAllowed?.toString()
                )
            }
        }
    }
}

@Composable
fun NodeDetailItem(
    label: String,
    value: String?,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { }
    ) {
        Column(
            modifier = Modifier.padding(
                start = Theme.dimens.space.medium,
                end = Theme.dimens.space.medium,
                top = Theme.dimens.space.small,
                bottom = Theme.dimens.space.medium
            )
        ) {
            Text(
                text = "$label:",
                color = Theme.colors.secondary
            )
            value?.let { Text(it) }
        }
        CustomDivider()
    }
}