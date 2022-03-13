package com.x.presentation.scene.account.register

import com.x.presentation.scene.account.register.RegisterContract.Event
import com.x.presentation.scene.account.register.RegisterContract.Event.*

fun RegisterViewModel.reduceEvent(
    event: Event,
) {
    when (event) {
        is SaveAccount -> {
            save(accountModel = event.accountModel)
        }
        is GetAccount -> {
            getAccount(accountId = event.accountId)
        }
    }
}