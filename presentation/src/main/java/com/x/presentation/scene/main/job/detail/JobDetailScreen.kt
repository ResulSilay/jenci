package com.x.presentation.scene.main.job.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.PlayArrow
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.insets.navigationBarsPadding
import com.google.accompanist.pager.ExperimentalPagerApi
import com.x.domain.model.JobBuildItemModel
import com.x.domain.model.JobHealthReportModel
import com.x.domain.model.JobModel
import com.x.presentation.R
import com.x.presentation.scene.main.job.build.action.JobBuildBottomSheet
import com.x.presentation.scene.main.job.detail.JobDetailContract.Event
import com.x.presentation.ui.composable.*
import com.x.presentation.ui.composable.EmptyStates.CustomEmptyState
import com.x.presentation.ui.composable.EmptyStates.FailureEmptyState
import com.x.presentation.ui.composable.EmptyStates.NetworkEmptyState
import com.x.presentation.ui.composable.EmptyStates.NotFoundEmptyState
import com.x.presentation.ui.theme.Theme
import com.x.presentation.util.JenkinsExtension.scoreToDrawableId
import com.x.presentation.util.TimeExtension.toAgoDate
import com.x.presentation.util.getString
import com.x.presentation.util.toFieldEmptyString
import kotlinx.coroutines.launch

@ExperimentalMaterialApi
@ExperimentalMaterial3Api
@ExperimentalPagerApi
@Composable
fun JobDetailScreen(
    viewModel: JobDetailViewModel = hiltViewModel(),
    jobUrl: String,
    navigateJobDetail: (jobUrl: String?, buildId: String) -> Unit,
    navigateUp: () -> Unit,
) {
    val state = viewModel.state.collectAsState()
    val jobModelResource = state.value.jobModel
    val buildResource = state.value.buildModel

    val coroutineScope = rememberCoroutineScope()
    val bottomSheetState = rememberBottomSheetScaffoldState(
        bottomSheetState = BottomSheetState(BottomSheetValue.Collapsed)
    )

    LaunchedEffect(Unit) {
        viewModel.publish(event = Event.GetJob(jobUrl = jobUrl))
    }

    Resource(
        resource = jobModelResource,
        onSuccess = { jobModel ->
            JobBuildBottomSheet(
                jobActionModel = jobModel?.actions?.firstOrNull(),
                bottomSheetState = bottomSheetState,
                content = {
                    JobDetailBody(
                        jobModel = jobModel,
                        navigateUp = navigateUp,
                        navigateBuild = {
                            coroutineScope.launch {
                                bottomSheetState.bottomSheetState.expand()
                            }
                        },
                        navigateJobDetail = navigateJobDetail
                    )
                },
                onDismiss = {
                    coroutineScope.launch {
                        bottomSheetState.bottomSheetState.collapse()
                    }
                },
                onBuild = { fields ->
                    if (fields.isNotEmpty()) {
                        viewModel.publish(
                            event = Event.BuildWithParameters(
                                jobUrl = jobUrl,
                                fieldMap = fields
                            )
                        )
                    } else {
                        viewModel.publish(
                            event = Event.Build(
                                jobUrl = jobUrl
                            )
                        )
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
                    viewModel.publish(event = Event.GetJob(jobUrl = jobUrl))
                }
            )
        },
        onFailure = {
            FailureEmptyState(
                onTryAgainActionClick = {
                    viewModel.publish(event = Event.GetJob(jobUrl = jobUrl))
                }
            )
        }
    )

    Resource(
        resource = buildResource,
        onSuccess = {
            CustomEmptyState(
                title = R.string.empty_states_job_build_action_title,
                description = R.string.empty_states_job_build_action_desc,
                image = R.drawable.ic_empty_states_done,
                content = {
                    CircularCountDownLoading {
                        viewModel.publish(event = Event.GetJob(jobUrl = jobUrl))
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
            FailureEmptyState(
                onTryAgainActionClick = {
                    viewModel.publish(event = Event.GetJob(jobUrl = jobUrl))
                }
            )
        },
        onFailure = {
            FailureEmptyState(
                onTryAgainActionClick = {
                    viewModel.publish(event = Event.GetJob(jobUrl = jobUrl))
                }
            )
        }
    )
}

@ExperimentalMaterial3Api
@Composable
fun JobDetailBody(
    jobModel: JobModel?,
    navigateUp: () -> Unit,
    navigateBuild: () -> Unit,
    navigateJobDetail: (jobUrl: String?, buildId: String) -> Unit,
) {
    Scaffold(
        topBar = {
            MainAppBar(
                title = "${jobModel?.fullDisplayName}",
                backgroundColor = Theme.colors.surface,
                navigateUp = navigateUp
            )
        },
        floatingActionButton = {
            JobBuildFAB(
                onClick = {
                    navigateBuild()
                }
            )
        },
        content = {
            jobModel?.let { jobModel ->
                JobDetailContent(
                    jobModel = jobModel,
                    jobBuildItemClick = { buildId ->
                        navigateJobDetail(jobModel.url, buildId)
                    }
                )
            }
        }
    )
}

@Composable
fun JobDetailContent(
    jobModel: JobModel?,
    jobBuildItemClick: (buildId: String) -> Unit,
) {
    LazyColumn(
        modifier = Modifier
            .paddingFromBaseline(bottom = Theme.dimens.space.large)
    ) {
        item {
            JobHealthReportCard(
                healthReportModel = jobModel?.healthReport,
                content = {
                    JobDetailItem(
                        label = R.string.job_detail_item_status_text.getString(),
                        value = jobModel?.status?.name
                    )
                    JobDetailItem(
                        label = R.string.job_detail_item_buildable_text.getString(),
                        value = jobModel?.buildable
                    )
                    JobDetailItem(
                        label = R.string.job_detail_item_disabled_text.getString(),
                        value = jobModel?.disabled
                    )
                    JobDetailItem(
                        label = R.string.job_detail_item_concurrentBuild_text.getString(),
                        value = jobModel?.concurrentBuild
                    )
                    JobDetailItem(
                        label = R.string.job_detail_item_inQueue_text.getString(),
                        value = jobModel?.inQueue
                    )
                    JobDetailItem(
                        label = R.string.job_detail_item_keepDependencies_text.getString(),
                        value = jobModel?.keepDependencies
                    )
                }
            )
        }
        item {
            jobModel?.let {
                Header(
                    title = R.string.job_header_overview_text.getString()
                )
                JobLastBuildCard(
                    jobModel = it,
                    jobBuildItemClick = jobBuildItemClick
                )
            }
        }
        item {
            jobModel?.buildItems?.let {
                Header(
                    title = R.string.job_header_build_text.getString()
                )
                JobBuildList(
                    jobBuildItemList = it,
                    jobBuildItemClick = jobBuildItemClick
                )
            }
        }
    }
}

@Composable
fun JobBuildFAB(
    onClick: () -> Unit,
) {
    return ExtendedFloatingActionButton(
        containerColor = Theme.colors.primary,
        contentColor = contentColorFor(backgroundColor = Theme.colors.primary),
        onClick = {
            onClick()
        },
        icon = {
            Icon(
                imageVector = Icons.Outlined.PlayArrow,
                contentDescription = R.string.job_build_fab_desc.getString(),
                tint = contentColorFor(backgroundColor = Theme.colors.primary)
            )
        },
        text = {
            Text(
                text = R.string.job_build_fab_text.getString(),
                color = contentColorFor(backgroundColor = Theme.colors.primary)
            )
        },
        modifier = Modifier
            .navigationBarsPadding()
    )
}

@Composable
fun JobHealthReportCard(
    healthReportModel: JobHealthReportModel?,
    content: @Composable () -> Unit,
) {
    Card(
        shape = Theme.shapes.none,
        elevation = Theme.dimens.elevation.medium,
        backgroundColor = Theme.colors.onSecondary,
        modifier = Modifier.paddingFromBaseline(
            bottom = Theme.dimens.space.xxlarge
        )
    ) {
        Column {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .background(color = Theme.colors.surface)
                    .padding(
                        start = Theme.dimens.space.medium,
                        end = Theme.dimens.space.medium,
                        top = Theme.dimens.space.medium,
                        bottom = Theme.dimens.space.large,
                    )
                    .height(IntrinsicSize.Min)
                    .fillMaxSize()
            ) {
                Image(
                    painter = painterResource(id = healthReportModel?.score.scoreToDrawableId()),
                    contentDescription = R.string.job_health_report_icon_desc.getString(),
                    modifier = Modifier
                        .size(48.dp)
                )
                Column(
                    verticalArrangement = Arrangement.aligned(Alignment.CenterVertically),
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(
                            start = Theme.dimens.space.medium
                        )
                ) {
                    Text(
                        text = R.string.job_health_report_desc_text.getString()
                    )
                    Text(
                        text = healthReportModel?.description.toFieldEmptyString(),
                        style = Theme.typography.bodyMedium
                    )
                }
            }
            Column {
                content()
            }
        }
    }
}

@Composable
fun JobDetailItem(
    label: String,
    value: Any?,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Text(
            text = label,
            modifier = Modifier
                .padding(all = Theme.dimens.space.medium)
                .weight(weight = 2F)
        )
        Text(
            text = value?.toString().toFieldEmptyString(),
            textAlign = TextAlign.End,
            modifier = Modifier
                .padding(all = Theme.dimens.space.medium)
                .weight(weight = 1F)
        )
    }
    CustomDivider()
}

@Composable
fun JobBuildList(
    jobBuildItemList: List<JobBuildItemModel>,
    jobBuildItemClick: (buildId: String) -> Unit,
) {
    Card(
        shape = Theme.shapes.none,
        elevation = Theme.dimens.elevation.medium,
        backgroundColor = Theme.colors.onSecondary,
        modifier = Modifier.paddingFromBaseline(
            bottom = Theme.dimens.space.xxlarge
        )
    ) {
        Column {
            jobBuildItemList.forEachIndexed { _, jobBuildItemModel ->
                Column {
                    JobBuildItem(
                        jobBuildItemModel = jobBuildItemModel,
                        jobBuildItemClick = jobBuildItemClick
                    )
                    CustomDivider()
                }
            }
        }
    }
}

@Composable
fun JobBuildItem(
    jobBuildItemModel: JobBuildItemModel,
    jobBuildItemClick: (buildId: String) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                jobBuildItemModel.number?.let { buildId ->
                    jobBuildItemClick(buildId.toString())
                }
            }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(
                space = Theme.dimens.space.small
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    all = Theme.dimens.space.medium
                )
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .weight(2F)
            ) {
                BuildResult(
                    status = jobBuildItemModel.result
                )
                Text(
                    text = jobBuildItemModel.number.toString(),
                    modifier = Modifier.padding(
                        start = Theme.dimens.space.small
                    )
                )
            }
            Text(
                text = jobBuildItemModel.timestamp.toAgoDate().toFieldEmptyString(),
                textAlign = TextAlign.End,
                modifier = Modifier
                    .weight(1F)
            )
        }
    }
}

@Composable
fun JobLastBuildCard(
    jobModel: JobModel,
    jobBuildItemClick: (buildId: String) -> Unit,
) {
    Card(
        shape = Theme.shapes.none,
        elevation = Theme.dimens.elevation.medium,
        backgroundColor = Theme.colors.onSecondary,
        modifier = Modifier
            .paddingFromBaseline(
                bottom = Theme.dimens.space.xlarge
            )
    ) {
        Column {
            jobModel.firstBuild?.let { buildItem ->
                JobLastBuildItem(
                    title = R.string.job_build_first.getString(),
                    buildNumber = buildItem.number,
                    timestamp = buildItem.timestamp,
                    jobBuildItemClick = jobBuildItemClick
                )
            }
            jobModel.lastBuild?.let { buildItem ->
                JobLastBuildItem(
                    title = R.string.job_build_last.getString(),
                    buildNumber = buildItem.number,
                    timestamp = buildItem.timestamp,
                    jobBuildItemClick = jobBuildItemClick
                )
            }
            jobModel.lastCompletedBuild?.let { buildItem ->
                JobLastBuildItem(
                    title = R.string.job_build_completed.getString(),
                    buildNumber = buildItem.number,
                    timestamp = buildItem.timestamp,
                    jobBuildItemClick = jobBuildItemClick
                )
            }
            jobModel.lastFailedBuild?.let { buildItem ->
                JobLastBuildItem(
                    title = R.string.job_build_failed.getString(),
                    buildNumber = buildItem.number,
                    timestamp = buildItem.timestamp,
                    jobBuildItemClick = jobBuildItemClick
                )
            }
            jobModel.lastSuccessfulBuild?.let { buildItem ->
                JobLastBuildItem(
                    title = R.string.job_build_successful.getString(),
                    buildNumber = buildItem.number,
                    timestamp = buildItem.timestamp,
                    jobBuildItemClick = jobBuildItemClick
                )
            }
            jobModel.lastUnsuccessfulBuild?.let { buildItem ->
                JobLastBuildItem(
                    title = R.string.job_build_unsuccessful.getString(),
                    buildNumber = buildItem.number,
                    timestamp = buildItem.timestamp,
                    jobBuildItemClick = jobBuildItemClick
                )
            }
            jobModel.lastStableBuild?.let { buildItem ->
                JobLastBuildItem(
                    title = R.string.job_build_stable.getString(),
                    buildNumber = buildItem.number,
                    timestamp = buildItem.timestamp,
                    jobBuildItemClick = jobBuildItemClick
                )
            }
            jobModel.lastUnstableBuild?.let { buildItem ->
                JobLastBuildItem(
                    title = R.string.job_build_unstable.getString(),
                    buildNumber = buildItem.number,
                    timestamp = buildItem.timestamp,
                    jobBuildItemClick = jobBuildItemClick
                )
            }
        }
    }
}

@Composable
fun JobLastBuildItem(
    title: String,
    buildNumber: Int?,
    timestamp: Long?,
    jobBuildItemClick: (buildId: String) -> Unit,
) {
    Column {
        Row(
            horizontalArrangement = Arrangement.spacedBy(Theme.dimens.space.medium),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    buildNumber?.let { buildId ->
                        jobBuildItemClick(buildId.toString())
                    }
                }
        ) {
            val buildNumberString = (buildNumber ?: R.string.empty_field.getString()).toString()
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(Theme.dimens.space.medium)
                    .weight(3F)
            ) {
                Text(text = title)
                Text(
                    text = timestamp.toAgoDate().toFieldEmptyString(),
                    style = Theme.typography.labelSmall,
                    fontStyle = FontStyle.Italic,
                    color = Theme.colors.onSurfaceVariant,
                    modifier = Modifier.padding(start = Theme.dimens.space.min)
                )
            }

            Text(
                text = buildNumberString,
                textAlign = TextAlign.End,
                modifier = Modifier
                    .padding(Theme.dimens.space.medium)
                    .weight(1F)
            )
        }
        CustomDivider()
    }
}