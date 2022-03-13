package com.x.presentation.navigation

import androidx.activity.ComponentActivity
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
import com.google.accompanist.systemuicontroller.SystemUiController
import com.x.common.extension.toParamName
import com.x.common.extension.urlDecode
import com.x.common.extension.urlEncode
import com.x.common.type.DashboardStatsType
import com.x.common.type.JobType
import com.x.presentation.navigation.Screen.Main
import com.x.presentation.scene.main.dashboard.DashboardScreen
import com.x.presentation.scene.main.job.build.detail.JobBuildDetailScreen
import com.x.presentation.scene.main.job.detail.JobDetailScreen
import com.x.presentation.scene.main.job.list.JobListScreen
import com.x.presentation.scene.main.node.NodeScreen
import com.x.presentation.scene.main.node.detail.NodeDetailScreen
import com.x.presentation.scene.main.people.PeopleScreen
import com.x.presentation.scene.main.profile.ProfileScreen
import com.x.presentation.scene.main.queue.QueueScreen
import com.x.presentation.scene.main.settings.SettingsScreen
import com.x.presentation.scene.main.view.ViewScreen
import com.x.presentation.ui.composable.Navigation.StatusBarColor
import com.x.presentation.ui.composable.enterTransition
import com.x.presentation.ui.composable.exitTransition
import com.x.presentation.ui.composable.popEnterTransition
import com.x.presentation.ui.composable.popExitTransition
import com.x.presentation.ui.theme.Theme
import com.x.presentation.util.restart

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@ExperimentalMaterial3Api
@ExperimentalFoundationApi
@ExperimentalPagerApi
@Composable
fun MainNavHost(
    componentActivity: ComponentActivity,
    navController: NavHostController,
    systemUiController: SystemUiController,
) {
    AnimatedNavHost(
        navController = navController,
        startDestination = Main.DashboardScreen.route
    ) {
        composable(
            route = Main.DashboardScreen.route,
            enterTransition = enterTransition(),
            exitTransition = exitTransition(),
            popEnterTransition = popEnterTransition(),
            popExitTransition = popExitTransition()
        ) {
            StatusBarColor(
                color = Theme.colors.surface,
                systemUiController = systemUiController
            )
            DashboardScreen(
                navigateStatsClick = { dashboardStatsType ->
                    when (dashboardStatsType) {
                        DashboardStatsType.NODES -> {
                            with(navController) { navigate(route = Main.Node.NodeScreen.route) }
                        }
                        DashboardStatsType.VIEWS -> {
                            with(navController) { navigate(route = Main.ViewScreen.route) }
                        }
                        DashboardStatsType.JOBS -> {
                            with(navController) {
                                navigate(route = Main.Job.JobDetailScreen.route.replace(Main.Job.Arguments.jobUrl,
                                    Main.Node.Values.master))
                            }
                        }
                        DashboardStatsType.QUEUES -> {
                            with(navController) { navigate(route = Main.QueueScreen.route) }
                        }
                    }
                },
                navigateQueueDetail = {
                    with(navController) { navigate(route = Main.QueueScreen.route) }
                },
                navigateProfileClick = {
                    with(navController) { navigate(route = Main.ProfileScreen.route) }
                }
            )
        }
        composable(
            route = Main.Node.NodeScreen.route,
            enterTransition = enterTransition(),
            exitTransition = exitTransition(),
            popEnterTransition = popEnterTransition(),
            popExitTransition = popExitTransition()
        ) {
            StatusBarColor(
                color = Theme.colors.onSecondary,
                systemUiController = systemUiController
            )
            NodeScreen(
                navigateNodeDetail = {
                    with(navController) { navigate(route = Main.Node.NodeDetailScreen.route.replace(Main.Node.Arguments.nodeLabel, it)) }
                }
            )
        }
        composable(
            route = Main.Node.NodeDetailScreen.route,
            arguments = listOf(
                navArgument(Main.Node.Arguments.nodeLabel.toParamName()) { type = NavType.StringType }
            ),
            enterTransition = enterTransition(),
            exitTransition = exitTransition(),
            popEnterTransition = popEnterTransition(),
            popExitTransition = popExitTransition()
        ) {
            val nodeLabelArgument = it.arguments?.get(Main.Node.Arguments.nodeLabel.toParamName())

            StatusBarColor(
                color = Theme.colors.onSecondary,
                systemUiController = systemUiController
            )
            NodeDetailScreen(
                nodeLabel = nodeLabelArgument.toString(),
                navigateUp = {
                    with(navController) { navigateUp() }
                }
            )
        }
        composable(
            route = Main.ViewScreen.route,
            enterTransition = enterTransition(),
            exitTransition = exitTransition(),
            popEnterTransition = popEnterTransition(),
            popExitTransition = popExitTransition()
        ) {
            StatusBarColor(
                color = Theme.colors.onSecondary,
                systemUiController = systemUiController
            )
            ViewScreen(
                navigateJob = { jobUrl, jobItemType ->
                    when (jobItemType) {
                        JobType.FOLDER -> {
                            with(navController) {
                                navigate(route = Main.Job.JobListScreen.route.replace(Main.Job.Arguments.jobUrl,
                                    jobUrl.urlEncode()))
                            }
                        }
                        else -> {
                            with(navController) {
                                navigate(route = Main.Job.JobDetailScreen.route.replace(Main.Job.Arguments.jobUrl,
                                    jobUrl.urlEncode()))
                            }
                        }
                    }
                }
            )
        }
        composable(
            route = Main.Job.JobListScreen.route,
            arguments = listOf(
                navArgument(Main.Job.Arguments.jobUrl.toParamName()) { type = NavType.StringType }
            ),
            enterTransition = enterTransition(),
            exitTransition = exitTransition(),
            popEnterTransition = popEnterTransition(),
            popExitTransition = popExitTransition()
        ) {
            val jobUrlArgument = it.arguments?.getString(Main.Job.Arguments.jobUrl.toParamName())

            StatusBarColor(
                color = Theme.colors.onSecondary,
                systemUiController = systemUiController
            )
            JobListScreen(
                jobUrl = jobUrlArgument.urlDecode(),
                navigateJob = { jobUrl, type ->
                    when (type) {
                        JobType.FOLDER -> {
                            with(navController) {
                                navigate(route = Main.Job.JobListScreen.route.replace(Main.Job.Arguments.jobUrl,
                                    jobUrl.urlEncode()))
                            }
                        }
                        else -> {
                            with(navController) {
                                navigate(route = Main.Job.JobDetailScreen.route.replace(Main.Job.Arguments.jobUrl,
                                    jobUrl.urlEncode()))
                            }
                        }
                    }
                },
                navigateUp = {
                    with(navController) { navigateUp() }
                }
            )
        }
        composable(
            route = Main.Job.JobDetailScreen.route,
            arguments = listOf(
                navArgument(Main.Job.Arguments.jobUrl.toParamName()) { type = NavType.StringType }
            ),
            enterTransition = enterTransition(),
            exitTransition = exitTransition(),
            popEnterTransition = popEnterTransition(),
            popExitTransition = popExitTransition()
        ) {
            val jobUrlArgument = it.arguments?.getString(Main.Job.Arguments.jobUrl.toParamName())

            StatusBarColor(
                color = Theme.colors.surface,
                systemUiController = systemUiController
            )
            JobDetailScreen(
                jobUrl = jobUrlArgument.urlDecode(),
                navigateJobDetail = { jobUrl, buildId ->
                    with(navController) {
                        navigate(route = Main.Job.JobBuildScreen.route
                            .replace(Main.Job.Arguments.jobUrl, jobUrl.urlEncode())
                            .replace(Main.Job.Arguments.buildId, buildId)
                        )
                    }
                }
            ) {
                with(navController) { navigateUp() }
            }
        }
        composable(
            route = Main.Job.JobBuildScreen.route,
            arguments = listOf(
                navArgument(Main.Job.Arguments.jobUrl.toParamName()) { type = NavType.StringType },
                navArgument(Main.Job.Arguments.buildId.toParamName()) { type = NavType.StringType }
            ),
            enterTransition = enterTransition(),
            exitTransition = exitTransition(),
            popEnterTransition = popEnterTransition(),
            popExitTransition = popExitTransition()
        ) {
            val jobUrlArgument = it.arguments?.getString(Main.Job.Arguments.jobUrl.toParamName())
            val jobBuildIdArgument = it.arguments?.getString(Main.Job.Arguments.buildId.toParamName())

            StatusBarColor(
                color = Theme.colors.onSecondary,
                systemUiController = systemUiController
            )
             JobBuildDetailScreen(
                jobUrl = jobUrlArgument.urlDecode(),
                buildId = jobBuildIdArgument.toString(),
                navigateUp = {
                    with(navController) { navigateUp() }
                }
            )
        }
        composable(
            route = Main.QueueScreen.route,
            enterTransition = enterTransition(),
            exitTransition = exitTransition(),
            popEnterTransition = popEnterTransition(),
            popExitTransition = popExitTransition()
        ) {
            StatusBarColor(
                color = Theme.colors.onSecondary,
                systemUiController = systemUiController
            )
            QueueScreen(
                navigateQueueDetail = {

                },
                navigateUp = {
                    with(navController) { navigateUp() }
                }
            )
        }
        composable(
            route = Main.SettingsScreen.route,
            enterTransition = enterTransition(),
            exitTransition = exitTransition(),
            popEnterTransition = popEnterTransition(),
            popExitTransition = popExitTransition()
        ) {
            StatusBarColor(
                color = Theme.colors.onSecondary,
                systemUiController = systemUiController
            )
            SettingsScreen(
                navigatePeople = {
                    with(navController) { navigate(route = Main.PeopleScreen.route) }
                },
                navigateProfile = {
                    with(navController) { navigate(route = Main.ProfileScreen.route) }
                },
                onRestart = {
                    with(componentActivity) { restart() }
                }
            )
        }
        composable(
            route = Main.ProfileScreen.route,
            enterTransition = enterTransition(),
            exitTransition = exitTransition(),
            popEnterTransition = popEnterTransition(),
            popExitTransition = popExitTransition()
        ) {
            StatusBarColor(
                color = Theme.colors.onSecondary,
                systemUiController = systemUiController
            )
            ProfileScreen(
                navigateUp = {
                    with(navController) { navigateUp() }
                }
            )
        }
        composable(
            route = Main.PeopleScreen.route,
            enterTransition = enterTransition(),
            exitTransition = exitTransition(),
            popEnterTransition = popEnterTransition(),
            popExitTransition = popExitTransition()
        ) {
            StatusBarColor(
                color = Theme.colors.onSecondary,
                systemUiController = systemUiController
            )
            PeopleScreen(
                navigateUp = {
                    with(navController) { navigateUp() }
                }
            )
        }
    }
}