package com.example.tvshow.ui

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.compose.rememberNavController
import coil.annotation.ExperimentalCoilApi

@ExperimentalComposeUiApi
@ExperimentalCoilApi
@ExperimentalAnimationApi
@Composable
fun TvShowApp() {
    val navController = rememberNavController()
    TVShowNavGraph(navController = navController)
}