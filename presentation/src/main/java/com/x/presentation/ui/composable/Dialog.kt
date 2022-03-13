package com.x.presentation.ui.composable

import androidx.annotation.StringRes
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import com.x.presentation.util.getString

@Composable
fun CustomAlertDialog(
    @StringRes title: Int,
    @StringRes message: Int,
    @StringRes confirmText: Int,
    @StringRes dismissText: Int,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit,
) {
    AlertDialog(
        title = {
            Text(text = title.getString())
        },
        text = {
            Text(text = message.getString())
        },
        onDismissRequest = {
            onDismiss()
        },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(text = confirmText.getString())
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text(text = dismissText.getString())
            }
        }
    )
}