package com.x.presentation.ui.composable

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.navigation.NavBackStackEntry

private const val navAnimationIntoDurationMillis = 300
private const val navAnimationOutDurationMillis = 300

@ExperimentalAnimationApi
fun enterTransition(): AnimatedContentScope<NavBackStackEntry>.() -> EnterTransition? = {
    slideIntoContainer(AnimatedContentScope.SlideDirection.Up, animationSpec = tween(navAnimationIntoDurationMillis))
}

@ExperimentalAnimationApi
fun popEnterTransition(): AnimatedContentScope<NavBackStackEntry>.() -> EnterTransition? = {
    slideIntoContainer(AnimatedContentScope.SlideDirection.Up, animationSpec = tween(navAnimationIntoDurationMillis))
}

@ExperimentalAnimationApi
fun exitTransition(): AnimatedContentScope<NavBackStackEntry>.() -> ExitTransition? = {
    slideOutOfContainer(AnimatedContentScope.SlideDirection.Down, animationSpec = tween(navAnimationOutDurationMillis))
}

@ExperimentalAnimationApi
fun popExitTransition(): AnimatedContentScope<NavBackStackEntry>.() -> ExitTransition? = {
    slideOutOfContainer(AnimatedContentScope.SlideDirection.Down, animationSpec = tween(navAnimationOutDurationMillis))
}
