package com.x.presentation.scene.main.node

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.x.domain.model.NodeComputerModel
import com.x.presentation.R
import com.x.presentation.scene.main.node.NodeContract.Event
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
fun NodeScreen(
    viewModel: NodeViewModel = hiltViewModel(),
    navigateNodeDetail: (label: String) -> Unit,
) {
    val state = viewModel.state.collectAsState()
    val nodeModelResource = state.value.nodeModel

    Resource(
        resource = nodeModelResource,
        onSuccess = { nodes ->
            NodeBody(
                nodes = nodes?.computer,
                navigateNodeDetail = navigateNodeDetail
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
                    viewModel.publish(event = Event.GetNodes)
                }
            )
        },
        onFailure = {
            FailureEmptyState(
                onTryAgainActionClick = {
                    viewModel.publish(event = Event.GetNodes)
                }
            )
        }
    )
}

@ExperimentalMaterial3Api
@Composable
fun NodeBody(
    nodes: List<NodeComputerModel>?,
    navigateNodeDetail: (name: String) -> Unit,
) {
    Scaffold(
        topBar = {
            MainAppBar(
                title = R.string.node_header.getString(),
                backgroundColor = Theme.colors.onSecondary
            )
        },
        content = {
            nodes?.let { nodeList ->
                NodeList(
                    nodeList = nodeList,
                    navigateNodeDetail = navigateNodeDetail
                )
            }
        }
    )
}

@Composable
fun NodeList(
    nodeList: List<NodeComputerModel>,
    navigateNodeDetail: (name: String) -> Unit,
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
            items(nodeList) { item ->
                NodeItem(item, navigateNodeDetail = navigateNodeDetail)
                CustomDivider()
            }
        }
    }
}

@Composable
fun NodeItem(
    nodeComputerModel: NodeComputerModel,
    navigateNodeDetail: (name: String) -> Unit,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(Theme.dimens.space.medium),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                nodeComputerModel.label?.let {
                    if (it == "built-in" || it == "master")
                        navigateNodeDetail("($it)")
                    else
                        navigateNodeDetail(it)
                }
            }
    ) {
        Text(
            text = nodeComputerModel.displayName.toString(),
            modifier = Modifier
                .padding(Theme.dimens.space.medium)
        )
    }
}