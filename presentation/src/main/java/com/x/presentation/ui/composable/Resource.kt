package com.x.presentation.ui.composable

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.x.common.resource.Resource
import com.x.common.resource.ResourceStatus.*
import com.x.presentation.util.NetworkExtension

@Composable
fun <T> Resource(
    resource: Resource<T>?,
    onSuccess: @Composable (T?) -> Unit,
    notFound: @Composable () -> Unit,
    onLoading: @Composable () -> Unit,
    onNetwork: @Composable () -> Unit,
    onFailure: @Composable () -> Unit,
) {
    val context: Context = LocalContext.current

    resource?.apply {
        when (status) {
            SUCCESS -> {
                when {
                    resource.data != null -> {
                        onSuccess(resource.data)
                    }
                    else -> {
                        notFound()
                    }
                }
            }
            FAILURE -> {
                if (!NetworkExtension.isNetworkAvailable(context = context)) onNetwork() else onFailure()
            }
            LOADING -> {
                onLoading()
            }
            COMPLETED -> {
            }
        }
    }
}