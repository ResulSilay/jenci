package com.x.presentation.scene.main

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.x.presentation.base.BaseActivity
import com.x.presentation.navigation.MainNavHost
import com.x.presentation.navigation.NavigationBarItem
import com.x.presentation.ui.composable.BottomNavigationBar
import com.x.presentation.ui.theme.JenciTheme
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@ExperimentalMaterial3Api
@ExperimentalFoundationApi
@ExperimentalPagerApi
@AndroidEntryPoint
class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val systemUiController = rememberSystemUiController()
            val navController = rememberAnimatedNavController()

            JenciTheme {
                ProvideWindowInsets {
                    Scaffold(
                        bottomBar = {
                            BottomNavigationBar(
                                barItems = listOf(
                                    NavigationBarItem.Home,
                                    NavigationBarItem.Node,
                                    NavigationBarItem.View,
                                    NavigationBarItem.Profile
                                ),
                                navController = navController
                            )
                        }, content = { paddingValues ->
                            Box(
                                modifier = Modifier.padding(
                                    paddingValues = paddingValues
                                )
                            ) {
                                MainNavHost(
                                    componentActivity = this@MainActivity,
                                    navController = navController,
                                    systemUiController = systemUiController
                                )
                            }
                        }
                    )
                }
            }
        }
    }
}