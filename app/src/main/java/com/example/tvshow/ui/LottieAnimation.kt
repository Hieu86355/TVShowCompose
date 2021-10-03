package com.example.tvshow.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.airbnb.lottie.compose.*

@Composable
fun LoadAnimation(
    resource: Int,
    modifier: Modifier = Modifier,
    isPlaying: Boolean = true,
    restartOnPlay: Boolean = true,
    speed: Float = 1f,
    iterations: Int = LottieConstants.IterateForever

) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(resource))
    val progress by animateLottieCompositionAsState(
        composition = composition,
        iterations = iterations,
        isPlaying = isPlaying,
        restartOnPlay = restartOnPlay,
        speed = speed,
    )
    LottieAnimation(
        composition = composition,
        progress = progress,
        modifier = modifier
    )
}