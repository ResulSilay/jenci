package com.x.presentation.scene.splash

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.x.domain.model.AccountModel
import com.x.presentation.base.BaseActivity
import com.x.presentation.navigation.AccountNavHost
import com.x.presentation.scene.main.MainActivity
import com.x.presentation.ui.theme.JenciTheme
import com.x.presentation.ui.theme.Theme
import com.x.presentation.util.start
import dagger.hilt.android.AndroidEntryPoint

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@ExperimentalMaterial3Api
@ExperimentalFoundationApi
@ExperimentalPagerApi
@AndroidEntryPoint
@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)

        splashScreen.setKeepOnScreenCondition { false }

        val accountModel = cacheManager().getModel(AccountModel::class)
        if (accountModel?.authToken?.uuid != null) {
            start(
                activityClass = MainActivity::class.java,
                finish = {
                    finish()
                }
            )
        }

        setContent {
            val systemUiController = rememberSystemUiController()
            val navController = rememberAnimatedNavController()

            systemUiController.setNavigationBarColor(Theme.colors.background)

            JenciTheme {
                ProvideWindowInsets {
                    Scaffold(
                        content = {
                            AccountNavHost(
                                navController = navController,
                                startMainActivity = {
                                    start(
                                        activityClass = MainActivity::class.java,
                                        finish = {
                                            finish()
                                        }
                                    )
                                }
                            )
                        }
                    )
                }
            }
        }
    }
}