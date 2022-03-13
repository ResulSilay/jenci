package com.x.presentation.scene.main.job.build.detail

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Card
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.intl.Locale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.toUpperCase
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import com.x.domain.model.JobBuildArtifactModel
import com.x.domain.model.JobBuildDetailModel
import com.x.presentation.R
import com.x.presentation.scene.main.job.build.detail.JobBuildDetailContract.Event
import com.x.presentation.ui.composable.CircularLoading
import com.x.presentation.ui.composable.CustomDivider
import com.x.presentation.ui.composable.EmptyStates.FailureEmptyState
import com.x.presentation.ui.composable.EmptyStates.NetworkEmptyState
import com.x.presentation.ui.composable.EmptyStates.NotFoundEmptyState
import com.x.presentation.ui.composable.MainAppBar
import com.x.presentation.ui.composable.Resource
import com.x.presentation.ui.theme.Theme
import com.x.presentation.util.TimeExtension.toAgoTime
import com.x.presentation.util.TimeExtension.toDateFormat
import com.x.presentation.util.getString
import com.x.presentation.util.getStringList
import com.x.presentation.util.toFieldEmptyString
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@ExperimentalMaterial3Api
@ExperimentalPagerApi
@Composable
fun JobBuildDetailScreen(
    viewModelBuild: JobBuildDetailViewModel = hiltViewModel(),
    jobUrl: String,
    buildId: String,
    navigateUp: () -> Unit,
) {
    val state = viewModelBuild.state.collectAsState()
    val jobBuildModelResource = state.value.jobBuildDetailModel
    val tabs = R.array.job_tabs.getStringList()

    LaunchedEffect(Unit) {
        viewModelBuild.publish(event = Event.GetBuild(jobUrl = jobUrl, buildId = buildId))
    }

    Resource(
        resource = jobBuildModelResource,
        onSuccess = { jobBuildModel ->
            JobBuildDetailBody(tabs = tabs,
                buildId = buildId,
                jobBuildDetailModel = jobBuildModel,
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
                    viewModelBuild.publish(event = Event.GetBuild(jobUrl = jobUrl, buildId = buildId))
                }
            )
        },
        onFailure = {
            FailureEmptyState(
                onTryAgainActionClick = {
                    viewModelBuild.publish(event = Event.GetBuild(jobUrl = jobUrl, buildId = buildId))
                }
            )
        }
    )
}

@ExperimentalMaterial3Api
@ExperimentalPagerApi
@Composable
fun JobBuildDetailBody(
    tabs: List<String>,
    buildId: String,
    jobBuildDetailModel: JobBuildDetailModel?,
    navigateUp: () -> Unit,
) {
    Scaffold(
        topBar = {
            MainAppBar(
                title = "${jobBuildDetailModel?.fullDisplayName}",
                backgroundColor = Theme.colors.onSecondary,
                navigateUp = navigateUp
            )
        },
        content = {
            jobBuildDetailModel?.let { jobBuildModel ->
                JobBuildDetailContent(
                    tabs = tabs,
                    jobBuildDetailModel = jobBuildModel
                )
            }
        }
    )
}

@ExperimentalPagerApi
@Composable
fun JobBuildDetailContent(
    tabs: List<String>,
    jobBuildDetailModel: JobBuildDetailModel?,
) {
    Column {
        jobBuildDetailModel?.let { jobBuildModel ->
            JobBuildTabBarPager(
                tabs = tabs,
                tabOverviewContent = {
                    OverviewContent(
                        jobBuildDetailModel = jobBuildModel
                    )
                },
                tabArtifactContent = {
                    jobBuildModel.artifacts?.let {
                        ArtifactContent(it)
                    }
                },
                tabConsoleLogContent = {
                    jobBuildModel.consoleLog?.let {
                        ConsoleLogContent(it)
                    }
                },
                onPageChange = {

                }
            )
        }
    }
}

@ExperimentalPagerApi
@Composable
fun JobBuildTabBarPager(
    tabs: List<String>,
    tabOverviewContent: @Composable () -> Unit,
    tabArtifactContent: @Composable () -> Unit,
    tabConsoleLogContent: @Composable () -> Unit,
    onPageChange: (pageIndex: Int) -> Unit,
) {
    val pagerState = rememberPagerState(
        initialPage = 0,
    )
    val tabIndex = pagerState.currentPage
    val coroutineScope = rememberCoroutineScope()

    Column {
        TabRow(
            backgroundColor = Theme.colors.onSecondary,
            contentColor = contentColorFor(backgroundColor = Theme.colors.onSecondary),
            selectedTabIndex = tabIndex,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    Modifier.pagerTabIndicatorOffset(pagerState, tabPositions)
                )
            },
        ) {
            tabs.forEachIndexed { index, tab ->
                Tab(
                    text = {
                        Text(
                            text = tab.toUpperCase(Locale.current),
                            softWrap = false
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
            snapshotFlow {
                pagerState.currentPage
            }.collect {
                onPageChange(it)
            }
        }

        HorizontalPager(
            count = tabs.size,
            state = pagerState
        ) { index ->
            Column(
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .wrapContentHeight()
                    .fillMaxSize()
            ) {
                Card(
                    shape = Theme.shapes.none,
                    elevation = Theme.dimens.elevation.medium,
                    backgroundColor = Theme.colors.onSecondary,
                ) {
                    Column {
                        when (index) {
                            0 -> tabOverviewContent()
                            1 -> tabArtifactContent()
                            2 -> tabConsoleLogContent()
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun OverviewContent(
    jobBuildDetailModel: JobBuildDetailModel,
) {
    Column(
        modifier = Modifier
    ) {
        LazyColumn(
            verticalArrangement = Arrangement.aligned(Alignment.CenterVertically),
        ) {
            item {
                BuildOverviewItem(
                    title = R.string.job_detail_item_display_name_text.getString(),
                    value = jobBuildDetailModel.fullDisplayName
                )
                BuildOverviewItem(
                    title = R.string.job_detail_item_desc_text.getString(),
                    value = jobBuildDetailModel.description
                )
                BuildOverviewItem(
                    title = R.string.job_detail_item_build_number_text.getString(),
                    value = jobBuildDetailModel.number
                )
                BuildOverviewItem(
                    title = R.string.job_detail_item_queue_id_text.getString(),
                    value = jobBuildDetailModel.queueId
                )
                BuildOverviewItem(
                    title = R.string.job_detail_item_building_text.getString(),
                    value = jobBuildDetailModel.building
                )
                BuildOverviewItem(
                    title = R.string.job_detail_item_keep_log_text.getString(),
                    value = jobBuildDetailModel.keepLog
                )
                BuildOverviewItem(
                    title = R.string.job_detail_item_duration_text.getString(),
                    value = jobBuildDetailModel.duration?.toAgoTime()
                )
                BuildOverviewItem(
                    title = R.string.job_detail_item_estimated_duration_text.getString(),
                    value = jobBuildDetailModel.estimatedDuration?.toAgoTime()
                )
                BuildOverviewItem(
                    title = R.string.job_detail_item_datetime_text.getString(),
                    value = jobBuildDetailModel.timestamp?.toDateFormat()
                )
                BuildOverviewItem(
                    title = R.string.job_detail_item_result_text.getString(),
                    value = jobBuildDetailModel.result
                )
            }
        }
    }
}

@Composable
fun ArtifactContent(
    artifacts: List<JobBuildArtifactModel>,
) {
    LazyColumn(
        modifier = Modifier
            .background(color = Theme.colors.onSecondary)
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        items(artifacts) { artifactModel ->
            ArtifactItem(artifactModel = artifactModel)
        }
    }
}

@Composable
fun ConsoleLogContent(
    log: String,
) {
    LazyColumn(
        modifier = Modifier
            .background(color = Theme.colors.onSecondary)
            .padding(
                all = Theme.dimens.space.medium
            )
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        item {
            Text(text = log)
        }
    }
}

@Composable
fun ArtifactItem(
    artifactModel: JobBuildArtifactModel,
) {
    Column(
        modifier = Modifier.padding(
            all = Theme.dimens.space.medium
        )
    ) {
        Text(text = artifactModel.fileName.toFieldEmptyString())
        CustomDivider()
    }
}

@Composable
fun BuildOverviewItem(
    title: String,
    value: Any?,
) {
    value?.let { valueData ->
        Column(
            modifier = Modifier.padding(
                start = Theme.dimens.space.medium,
                end = Theme.dimens.space.medium,
                top = Theme.dimens.space.small,
                bottom = Theme.dimens.space.small
            )
        ) {
            Text(
                text = "$title:",
                color = Theme.colors.secondary
            )
            Text(
                text = valueData.toString().toFieldEmptyString(),
                textAlign = TextAlign.End,
                modifier = Modifier.fillMaxWidth()
            )
        }
        CustomDivider()
    }
}