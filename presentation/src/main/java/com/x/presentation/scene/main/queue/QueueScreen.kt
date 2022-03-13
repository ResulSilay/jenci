package com.x.presentation.scene.main.queue

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.x.domain.model.QueueItemModel
import com.x.presentation.R
import com.x.presentation.scene.main.queue.QueueContract.Event
import com.x.presentation.ui.composable.*
import com.x.presentation.ui.composable.EmptyStates.CustomEmptyState
import com.x.presentation.ui.composable.EmptyStates.FailureEmptyState
import com.x.presentation.ui.composable.EmptyStates.NetworkEmptyState
import com.x.presentation.ui.composable.EmptyStates.NotFoundEmptyState
import com.x.presentation.ui.theme.Theme
import com.x.presentation.util.TimeExtension.toAgoDate
import com.x.presentation.util.getString

@ExperimentalMaterial3Api
@ExperimentalPagerApi
@Composable
fun QueueScreen(
    viewModel: QueueViewModel = hiltViewModel(),
    navigateQueueDetail: (id: Int) -> Unit,
    navigateUp: () -> Unit,
) {
    val state = viewModel.state.collectAsState()
    val queueModelResource = state.value.queueModel
    val actionModelResource = state.value.actionModel

    val queueCancelDialogId = remember { mutableStateOf(-1) }

    Resource(
        resource = queueModelResource,
        onSuccess = { queueModel ->
            QueueBody(
                queues = queueModel?.items,
                queueCancelDialogId = queueCancelDialogId,
                navigateUp = navigateUp,
                navigateQueueDetail = navigateQueueDetail,
                onQueueCancelConfirm = {
                    viewModel.publish(event = Event.CancelQueue(queueId = queueCancelDialogId.value.toString()))
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
                    viewModel.publish(event = Event.GetAllQueue)
                }
            )
        },
        onFailure = {
            FailureEmptyState(
                onTryAgainActionClick = {
                    viewModel.publish(event = Event.GetAllQueue)
                }
            )
        }
    )

    Resource(resource = actionModelResource,
        onSuccess = {
            QueueCancelEmptyState {
                viewModel.publish(event = Event.GetAllQueue)
            }
        },
        notFound = {
            NotFoundEmptyState()
        },
        onLoading = {
            CircularLoading()
        },
        onNetwork = {
            FailureEmptyState(
                onTryAgainActionClick = {
                    viewModel.publish(event = Event.GetAllQueue)
                }
            )
        },
        onFailure = {
            FailureEmptyState(
                onTryAgainActionClick = {
                    viewModel.publish(event = Event.GetAllQueue)
                }
            )
        }
    )
}

@ExperimentalMaterial3Api
@Composable
fun QueueBody(
    queues: List<QueueItemModel>?,
    queueCancelDialogId: MutableState<Int>,
    navigateUp: () -> Unit,
    navigateQueueDetail: (id: Int) -> Unit,
    onQueueCancelConfirm: () -> Unit,
) {
    Scaffold(
        topBar = {
            MainAppBar(
                title = R.string.queue_header_text.getString(),
                backgroundColor = Theme.colors.onSecondary,
                navigateUp = navigateUp
            )
        },
        content = {
            queues?.let { queueList ->
                QueueList(
                    queueList = queueList,
                    navigateQueueDetail = { queueId ->
                        queueCancelDialogId.value = queueId
                        navigateQueueDetail(queueId)
                    }
                )

                if (queueCancelDialogId.value > 0) {
                    QueueCancelDialog(
                        onConfirm = onQueueCancelConfirm,
                        onDismiss = {
                            queueCancelDialogId.value = 0
                        }
                    )
                }
            }
        }
    )
}

@Composable
fun QueueList(
    queueList: List<QueueItemModel>?,
    navigateQueueDetail: (id: Int) -> Unit,
) {
    queueList?.let { queues ->
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
                items(queues) { item ->
                    QueueItem(
                        queueItemModel = item,
                        navigateQueueDetail = navigateQueueDetail
                    )
                    CustomDivider()
                }
            }
        }
    }
}

@Composable
fun QueueItem(
    queueItemModel: QueueItemModel,
    navigateQueueDetail: (id: Int) -> Unit,
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(Theme.dimens.space.medium),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                queueItemModel.id?.let { id ->
                    navigateQueueDetail(id)
                }
            }
    ) {
        Text(
            text = "${R.string.queue_item_number_text.getString()}${queueItemModel.id.toString()}",
            modifier = Modifier
                .weight(1F)
                .padding(
                    all = Theme.dimens.space.medium
                )
        )
        Text(
            text = "${queueItemModel.timestamp?.toAgoDate()}",
            style = Theme.typography.labelSmall,
            fontStyle = FontStyle.Italic,
            textAlign = TextAlign.End,
            color = Theme.colors.onSurfaceVariant,
            modifier = Modifier
                .weight(1F)
                .padding(
                    all = Theme.dimens.space.medium
                )
        )
    }
}

@Composable
fun QueueCancelDialog(
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
) {
    CustomAlertDialog(
        title = R.string.action_dialog_title,
        message = R.string.action_dialog_desc,
        confirmText = R.string.dialog_yes,
        dismissText = R.string.dialog_cancel,
        onConfirm = {
            onConfirm()
            onDismiss()
        },
        onDismiss = {
            onDismiss()
        }
    )
}

@Composable
fun QueueCancelEmptyState(
    onAction: () -> Unit,
) {
    CustomEmptyState(
        title = R.string.empty_states_queue_cancel_action_title,
        description = R.string.empty_states_queue_cancel_action_desc,
        image = R.drawable.ic_empty_states_done,
        content = {
            CircularCountDownLoading {
                onAction()
            }
        }
    )
}