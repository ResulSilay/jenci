package com.x.presentation.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.pager.ExperimentalPagerApi
import com.x.presentation.scene.account.login.LoginScreen
import com.x.presentation.scene.account.register.RegisterScreen
import com.x.presentation.ui.composable.enterTransition
import com.x.presentation.ui.composable.exitTransition
import com.x.presentation.ui.composable.popEnterTransition
import com.x.presentation.ui.composable.popExitTransition

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@ExperimentalMaterial3Api
@ExperimentalFoundationApi
@ExperimentalPagerApi
@Composable
fun AccountNavHost(
    navController: NavHostController,
    startMainActivity: () -> Unit,
) {
    AnimatedNavHost(
        navController = navController,
        startDestination = Screen.Account.Login.LoginScreen.route
    ) {
        composable(
            route = Screen.Account.Login.LoginScreen.route,
            enterTransition = enterTransition(),
            exitTransition = exitTransition(),
            popEnterTransition = popEnterTransition(),
            popExitTransition = popExitTransition()
        ) {
            LoginScreen(
                navigateAccountRegister = {
                    navController.navigate(route = Screen.Account.Action.AccountRegisterScreen.route)
                },
                navigateAccountEditAction = { accountModel ->
                    navController.navigate(route = Screen.Account.Action.AccountEditScreen.route
                        .replace(Screen.Account.Action.Arguments.accountId, accountModel.accountId.toString())
                    )
                },
                startMainActivity = startMainActivity
            )
        }
        composable(
            route = Screen.Account.Action.AccountRegisterScreen.route,
            enterTransition = enterTransition(),
            exitTransition = exitTransition(),
            popEnterTransition = popEnterTransition(),
            popExitTransition = popExitTransition()
        ) {
            RegisterScreen(
                navigateUp = {
                    navController.navigateUp()
                }
            )
        }
        composable(
            route = Screen.Account.Action.AccountEditScreen.route,
            arguments = listOf(
                navArgument("accountId") { type = NavType.IntType },
            )
        ) {
            val accountId = it.arguments?.getInt("accountId")
            RegisterScreen(
                accountId = accountId,
                navigateUp = {
                    navController.navigateUp()
                }
            )
        }
    }
}