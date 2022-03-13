package com.x.presentation.util

import android.app.Activity
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.activity.ComponentActivity
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.ExperimentalMaterial3Api
import com.google.accompanist.pager.ExperimentalPagerApi
import com.x.presentation.R
import com.x.presentation.scene.splash.SplashActivity

@ExperimentalAnimationApi
@ExperimentalPagerApi
@ExperimentalFoundationApi
@ExperimentalMaterial3Api
@ExperimentalMaterialApi
fun Activity.restart() {
    val intent = Intent(this, SplashActivity::class.java)
    startActivity(intent)
    finishAffinity()
}

fun ComponentActivity.start(
    intent: Intent,
) {
    startActivity(intent)
}

fun ComponentActivity.start(
    intent: Intent,
    finish: () -> Unit,
) {
    startActivity(intent)
    finish()
}

fun <T> ComponentActivity.start(
    activityClass: Class<T>,
) {
    val intent = Intent(this, activityClass)
    val options = ActivityOptions.makeSceneTransitionAnimation(this).toBundle()
    startActivity(intent, options)
}

fun <T> ComponentActivity.start(
    activityClass: Class<T>,
    finish: () -> Unit,
) {
    val intent = Intent(this, activityClass)
    startActivity(intent)
    finish()
}

fun Context.start(url: String) {
    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
    startActivity(intent)
}

fun Context.share(text: String) {
    val intent = Intent(Intent.ACTION_SEND)
    intent.type = "text/plain"
    intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name))
    intent.putExtra(Intent.EXTRA_TEXT, text)
    startActivity(Intent.createChooser(intent, getString(R.string.app_name)))
}