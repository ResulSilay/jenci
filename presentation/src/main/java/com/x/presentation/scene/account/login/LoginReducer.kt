package com.x.presentation.scene.account.login

import com.x.presentation.scene.account.login.LoginContract.*
import com.x.presentation.scene.account.login.LoginContract.Event.*

fun LoginViewModel.reduceEvent(
    event: Event,
) {
    when (event) {
        is LoginEvent -> {
            onLogin(event.account)
        }
        is GetAccountListEvent -> {
            getAccounts()
        }
        is AccountRemoveEvent -> {
            accountRemove(accountId = event.accountId)
        }
    }
}