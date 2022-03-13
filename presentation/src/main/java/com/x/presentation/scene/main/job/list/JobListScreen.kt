package com.x.presentation.scene.main.job.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.x.common.extension.pathToTitle
import com.x.common.type.JobType
import com.x.domain.model.JobListItemModel
import com.x.presentation.scene.main.job.list.JobListContract.Event
import com.x.presentation.ui.composable.*
import com.x.presentation.ui.theme.Theme
import com.x.presentation.util.toFieldEmptyString

@ExperimentalMaterialApi
@ExperimentalMaterial3Api
@ExperimentalPagerApi
@Composable
fun JobListScreen(
    viewModel: JobListViewModel = hiltViewModel(),
    jobUrl: String,
    navigateJob: (jobUrl: String?, type: JobType) -> Unit,
    navigateUp: () -> Unit,
) {
    val state = viewModel.state.collectAsState()
    val jobModelResource = state.value.jobModel
    val isRefreshing = state.value.isRefreshing

    DisposableEffect(Unit) {
        viewModel.publish(event = Event.GetJobs(jobName = jobUrl))
        onDispose {}
    }

    Resource(
        resource = jobModelResource,
        onSuccess = { jobListModel ->
            JobListBody(
                jobUrl = jobUrl,
                jobList = jobListModel?.jobs,
                isRefreshing = isRefreshing,
                navigateUp = navigateUp,
                navigateJob = navigateJob,
                onRefresh = {
                    viewModel.publish(event = Event.GetJobs(jobName = jobUrl))
                }
            )
        },
        notFound = {
            EmptyStates.NotFoundEmptyState()
        },
        onLoading = {
            CircularLoading()
        },
        onNetwork = {
            EmptyStates.NetworkEmptyState(
                onTryAgainActionClick = {
                    viewModel.publish(event = Event.GetJobs(jobName = jobUrl))
                }
            )
        },
        onFailure = {
            EmptyStates.FailureEmptyState(
                onTryAgainActionClick = {
                    viewModel.publish(event = Event.GetJobs(jobName = jobUrl))
                }
            )
        }
    )
}

@ExperimentalMaterial3Api
@Composable
fun JobListBody(
    jobUrl: String,
    jobList: List<JobListItemModel>?,
    isRefreshing: Boolean,
    navigateUp: () -> Unit,
    navigateJob: (jobUrl: String?, type: JobType) -> Unit,
    onRefresh: () -> Unit,
) {
    Scaffold(
        topBar = {
            MainAppBar(
                title = jobUrl.pathToTitle(),
                backgroundColor = Theme.colors.onSecondary,
                navigateUp = navigateUp
            )
        },
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
                jobList?.let { jobList ->
                    JobListContent(
                        jobList = jobList,
                        navigateJob = navigateJob
                    )
                }
            }
        }
    )
}

@Composable
fun JobListContent(
    jobList: List<JobListItemModel>?,
    navigateJob: (jobUrl: String?, type: JobType) -> Unit,
) {
    Column(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxSize()
    ) {
        Card(
            shape = Theme.shapes.none,
            elevation = Theme.dimens.elevation.medium,
            backgroundColor = Theme.colors.onSecondary,
        ) {
            Column(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                if (jobList != null) {
                    JobListView(
                        jobListItemList = jobList,
                        navigateJob = navigateJob
                    )
                }
            }
        }
    }
}

@Composable
fun JobListView(
    jobListItemList: List<JobListItemModel>,
    navigateJob: (jobUrl: String?, type: JobType) -> Unit,
) {
    LazyColumn {
        items(jobListItemList) { item ->
            JobListItem(
                jobListItemModel = item,
                navigateJob = navigateJob
            )
            CustomDivider()
        }
    }
}

@Composable
fun JobListItem(
    jobListItemModel: JobListItemModel,
    navigateJob: (jobUrl: String?, type: JobType) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                jobListItemModel.let { jobItem ->
                    jobItem.type?.let { jobItemType ->
                        navigateJob(
                            jobItem.url,
                            jobItemType
                        )
                    }
                }
            }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(
                space = Theme.dimens.space.small
            ),
            modifier = Modifier.padding(
                all = Theme.dimens.space.medium
            )
        ) {
            JobStatus(
                type = jobListItemModel.type,
                status = jobListItemModel.status
            )
            Text(
                text = jobListItemModel.name.toFieldEmptyString(),
                modifier = Modifier.padding(
                    start = Theme.dimens.space.min
                )
            )
        }
    }
}