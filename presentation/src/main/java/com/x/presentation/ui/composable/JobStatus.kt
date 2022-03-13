package com.x.presentation.ui.composable

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Icon
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.x.common.type.BuildStatusType
import com.x.common.type.JobType
import com.x.presentation.R
import com.x.presentation.ui.theme.BuildStatusColors
import com.x.presentation.ui.theme.BuildStatusColors.Companion.get
import com.x.presentation.ui.theme.JobStatusColors
import com.x.presentation.ui.theme.JobStatusColors.Companion.get
import com.x.presentation.util.getString

@Composable
fun JobStatus(
    type: JobType?,
    status: BuildStatusType?,
) {
    if (status != BuildStatusType.NONE) {
        JobBuildStatus(
            status = status
        )
        return
    }
    when (type) {
        JobType.FOLDER -> {
            JobFolderStatus()
        }
        JobType.FREE -> {
            JobProjectStatus()
        }
        else -> {
            JobBuildStatus(
                status = status
            )
        }
    }
}

@Composable
fun JobFolderStatus() {
    JobStatusTypeItem(
        icon = Icons.Filled.Folder,
        color = JobStatusColors.FOLDER.get()
    )
}

@Composable
fun JobProjectStatus() {
    JobStatusTypeItem(
        icon = Icons.Filled.Architecture,
        color = JobStatusColors.PROJECT.get()
    )
}

@Composable
fun JobBuildStatus(
    status: BuildStatusType?,
) {
    when (status) {
        BuildStatusType.NONE -> {
            JobStatusTypeItem(
                color = BuildStatusColors.NONE.get()
            )
        }
        BuildStatusType.SUCCESS -> {
            JobStatusTypeItem(
                color = BuildStatusColors.SUCCESSFUL.get()
            )
        }
        BuildStatusType.SUCCESS_IN_PROGRESS -> {
            JobStatusInProgress(
                color = BuildStatusColors.SUCCESSFUL.get()
            )
        }
        BuildStatusType.FAILED -> {
            JobStatusTypeItem(
                color = BuildStatusColors.FAILED.get()
            )
        }
        BuildStatusType.FAILED_IN_PROGRESS -> {
            JobStatusInProgress(
                color = BuildStatusColors.FAILED.get()
            )
        }
        BuildStatusType.UNSTABLE -> {
            JobStatusTypeItem(
                color = BuildStatusColors.SUCCESSFUL.get()
            )
        }
        BuildStatusType.UNSTABLE_IN_PROGRESS -> {
            JobStatusInProgress(
                color = BuildStatusColors.UNSTABLE.get()
            )
        }
        BuildStatusType.PENDING -> {
            JobStatusTypeItem(
                color = BuildStatusColors.PENDING.get()
            )
        }
        BuildStatusType.PENDING_IN_PROGRESS -> {
            JobStatusInProgress(
                color = BuildStatusColors.PENDING.get()
            )
        }
        BuildStatusType.DISABLED -> {
            JobStatusTypeItem(
                color = BuildStatusColors.DISABLED.get()
            )
        }
        BuildStatusType.DISABLED_PROGRESS -> {
            JobStatusInProgress(
                color = BuildStatusColors.DISABLED.get()
            )
        }
        BuildStatusType.ABORTED -> {
            JobStatusTypeItem(
                color = BuildStatusColors.ABORTED.get()
            )
        }
        BuildStatusType.ABORTED_PROGRESS -> {
            JobStatusInProgress(
                color = BuildStatusColors.ABORTED.get()
            )
        }
        BuildStatusType.NO_BUILT -> {
            JobStatusTypeItem(
                color = BuildStatusColors.NOT_BUILT.get()
            )
        }
        BuildStatusType.NO_BUILT_PROGRESS -> {
            JobStatusInProgress(
                color = BuildStatusColors.NOT_BUILT.get()
            )
        }
        BuildStatusType.NOT_BUILT -> {
            JobStatusTypeItem(
                color = BuildStatusColors.NOT_BUILT.get()
            )
        }
        BuildStatusType.NOT_BUILT_PROGRESS -> {
            JobStatusInProgress(
                color = BuildStatusColors.NOT_BUILT.get()
            )
        }
        else -> {
            JobStatusTypeItem(
                color = BuildStatusColors.NONE.get()
            )
        }
    }
}

@Composable
fun JobStatusContentItem(
    color: Color,
    content: @Composable () -> Unit,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .background(
                color = color,
                shape = CircleShape
            )
    ) {
        Column(
            modifier = Modifier
                .size(size = 18.dp)
        ) {
            content()
        }
    }
}

@Composable
fun BuildContentLoadingItem(
    content: @Composable () -> Unit,
) {
    Box(
        contentAlignment = Alignment.Center,
    ) {
        Column(
            modifier = Modifier
                .size(size = 18.dp)
        ) {
            content()
        }
    }
}

@Composable
fun JobStatusTypeItem(
    color: Color,
) {
    JobStatusContentItem(
        color = color,
        content = { }
    )
}

@Composable
fun JobStatusTypeItem(
    icon: ImageVector?,
    color: Color,
) {
    JobStatusContentItem(
        color = Color.Transparent,
        content = {
            icon?.let {
                Icon(
                    imageVector = it,
                    tint = contentColorFor(backgroundColor = color),
                    contentDescription = R.string.login_action_icon_content_description.getString()
                )
            }
        }
    )
}

@Composable
fun JobStatusInProgress(
    color: Color = Color.Transparent,
) {
    BuildContentLoadingItem {
        CircularLoading(
            color = color,
            backgroundColor = Color.Transparent
        )
    }
}