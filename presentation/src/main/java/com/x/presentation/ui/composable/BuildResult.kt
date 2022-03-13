package com.x.presentation.ui.composable

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.CropSquare
import androidx.compose.material.icons.filled.Error
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import com.x.common.type.BuildResultType
import com.x.presentation.R
import com.x.presentation.ui.theme.BuildResultStatusColors
import com.x.presentation.ui.theme.BuildResultStatusColors.Companion.get
import com.x.presentation.util.getString

@Composable
fun BuildResult(
    status: BuildResultType,
) {
    when (status) {
        BuildResultType.NONE -> {
            BuildResultItem(
                icon = Icons.Default.CropSquare,
                color = BuildResultStatusColors.NONE.get()
            )
        }
        BuildResultType.SUCCESS -> {
            BuildResultItem(
                icon = Icons.Default.CheckCircle,
                color = BuildResultStatusColors.SUCCESS.get()
            )
        }
        BuildResultType.FAILURE -> {
            BuildResultItem(
                icon = Icons.Default.Error,
                color = BuildResultStatusColors.FAILURE.get()
            )
        }
    }
}

@Composable
fun BuildResultItem(
    icon: ImageVector?,
    color: Color,
) {
    JobStatusContentItem(
        color = Color.Transparent,
        content = {
            icon?.let {
                Icon(
                    imageVector = it,
                    tint = color,
                    contentDescription = R.string.build_result_item_icon_content_description.getString()
                )
            }
        }
    )
}