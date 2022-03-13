package com.x.presentation.scene.main.view

import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.ScrollableTabRow
import androidx.compose.material.Tab
import androidx.compose.material.TabRowDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.toUpperCase
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.pager.*
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.x.common.type.JobType
import com.x.domain.model.ViewItemModel
import com.x.domain.model.JobListItemModel
import com.x.presentation.scene.main.job.list.JobListView
import com.x.presentation.scene.main.view.ViewContract.Event
import com.x.presentation.ui.composable.CircularLoading
import com.x.presentation.ui.composable.EmptyStates.FailureEmptyState
import com.x.presentation.ui.composable.EmptyStates.NetworkEmptyState
import com.x.presentation.ui.composable.EmptyStates.NotFoundEmptyState
import com.x.presentation.ui.composable.Resource
import com.x.presentation.ui.theme.Theme
import com.x.presentation.util.toFieldEmptyString
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@ExperimentalMaterial3Api
@ExperimentalPagerApi
@Composable
fun ViewScreen(
    viewModel: ViewViewModel = hiltViewModel(),
    navigateJob: (jobUrl: String?, type: JobType) -> Unit,
) {
    val state = viewModel.state.collectAsState()
    val viewModelResource = state.value.viewModel
    val jobModelResource = state.value.jobViewListModel
    val isRefreshing = state.value.isRefreshing

    val coroutineScope = rememberCoroutineScope()
    val pagerState = rememberPagerState(
        initialPage = 0,
    )
    val tabIndex = pagerState.currentPage

    DisposableEffect(Unit) {
        viewModel.publish(event = Event.GetViews)
        onDispose {}
    }

    Resource(
        resource = viewModelResource,
        onSuccess = { viewDataModel ->
            ViewBody(
                views = viewDataModel?.views,
                jobItemList = jobModelResource?.data?.jobs,
                isRefreshing = isRefreshing,
                coroutineScope = coroutineScope,
                pagerState = pagerState,
                tabIndex = tabIndex,
                onPageChange = { viewName ->
                    viewName?.let { viewNameIt ->
                        viewModel.publish(event = Event.GetViewJobs(viewName = viewNameIt))
                    }
                },
                navigateJob = navigateJob,
                onRefresh = {
                    viewModel.publish(event = Event.GetViews)
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
                    viewModel.publish(event = Event.GetViews)
                }
            )
        },
        onFailure = {
            FailureEmptyState(
                onTryAgainActionClick = {
                    viewModel.publish(event = Event.GetViews)
                }
            )
        }
    )
}

@ExperimentalMaterial3Api
@ExperimentalPagerApi
@Composable
fun ViewBody(
    views: List<ViewItemModel>?,
    jobItemList: List<JobListItemModel>?,
    isRefreshing: Boolean,
    coroutineScope: CoroutineScope,
    pagerState: PagerState,
    tabIndex: Int,
    onPageChange: (viewName: String?) -> Unit,
    navigateJob: (jobUrl: String?, type: JobType) -> Unit,
    onRefresh: () -> Unit,
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
                views?.let { viewList ->
                    ViewContent(
                        viewList = viewList.reversed(),
                        jobListItemList = jobItemList,
                        coroutineScope = coroutineScope,
                        pagerState = pagerState,
                        tabIndex = tabIndex,
                        onPageChange = onPageChange,
                        navigateJob = navigateJob
                    )
                }
            }
        }
    )
}

@ExperimentalPagerApi
@Composable
fun ViewContent(
    viewList: List<ViewItemModel>,
    jobListItemList: List<JobListItemModel>?,
    coroutineScope: CoroutineScope,
    pagerState: PagerState,
    tabIndex: Int,
    onPageChange: (viewName: String?) -> Unit,
    navigateJob: (jobUrl: String?, type: JobType) -> Unit,
) {
    Column {
        ScrollableTabRow(
            backgroundColor = Theme.colors.onSecondary,
            contentColor = contentColorFor(backgroundColor = Theme.colors.onSecondary),
            selectedTabIndex = tabIndex,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    Modifier.pagerTabIndicatorOffset(pagerState, tabPositions)
                )
            },
        ) {
            viewList.forEachIndexed { index, view ->
                Tab(
                    text = {
                        Text(
                            text = view.name.toFieldEmptyString().toUpperCase(Locale.current)
                        )
                    },
                    selected = tabIndex == index,
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    }
                )
            }
        }

        LaunchedEffect(pagerState) {
            snapshotFlow { pagerState.currentPage }.collect {
                onPageChange(viewList[it].name)
            }
        }

        HorizontalPager(
            count = viewList.size,
            state = pagerState
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
                        if (jobListItemList != null) {
                            JobListView(
                                jobListItemList = jobListItemList,
                                navigateJob = navigateJob
                            )
                        }
                    }
                }
            }
        }
    }
}