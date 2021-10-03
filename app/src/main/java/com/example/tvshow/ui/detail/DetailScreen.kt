package com.example.tvshow.ui.detail

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.example.tvshow.R
import com.example.tvshow.data.models.DetailResult
import com.example.tvshow.data.models.Episode
import com.example.tvshow.data.models.TvShow
import com.example.tvshow.ui.LoadAnimation
import com.example.tvshow.ui.ScrollToTopButton
import com.example.tvshow.ui.theme.Poppins
import com.example.tvshow.utils.NetworkResult
import kotlinx.coroutines.launch

@ExperimentalAnimationApi
@ExperimentalCoilApi
@Composable
fun DetailScreen(
    showId: Int,
    navController: NavController,
    detailViewModel: DetailViewModel = hiltViewModel()
) {
    Surface(
        color = Color.Black,
        modifier = Modifier.fillMaxSize()
    ) {
        val showDetail by produceState<NetworkResult<DetailResult>>(
            initialValue = NetworkResult.Loading()
        ) {
            value = detailViewModel.getShowDetail(showId)
        }

        when (showDetail) {
            is NetworkResult.Loading -> {
                DetailScreenLoading()
            }

            is NetworkResult.Error -> {
                DetailScreenError(navController)
            }

            is NetworkResult.Success -> {
                showDetail.data?.tvShow?.let {
                    DetailScreenSuccess(
                        navController = navController,
                        tvShow = it
                    )
                }
            }
        }
    }
}

@ExperimentalAnimationApi
@ExperimentalCoilApi
@Composable
fun DetailScreenSuccess(
    navController: NavController,
    tvShow: TvShow
) {
    Box(modifier = Modifier.fillMaxSize()) {
        val listState = rememberLazyListState()
        val scope = rememberCoroutineScope()
        LazyColumn(
            state = listState,
            modifier = Modifier.fillMaxSize()
        ) {
            item {
                TopSection(
                    tvShow = tvShow,
                    navController = navController,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(0.7f)
                )
            }

            item {
                InfoSection(tvShow)
                PictureSection(tvShow)
                EpisodeSection(tvShow)

                // Bottom space
                Spacer(modifier = Modifier.height(100.dp))
            }
        }
        val showButton = listState.firstVisibleItemIndex > 0
        AnimatedVisibility(
            visible = showButton,
            enter = fadeIn(),
            exit = fadeOut(),
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .offset(x = (-16).dp, y = (-16).dp)
        ) {
            ScrollToTopButton(onClick = {
                scope.launch {
                    listState.scrollToItem(0)
                }
            })
        }
    }
}

@Composable
fun DetailScreenLoading() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        LoadAnimation(
            resource = R.raw.loading,
            modifier = Modifier.size(200.dp),
            speed = 2.5f
        )
    }
}

@Composable
fun DetailScreenError(
    navController: NavController
) {
    Column(
        Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        LoadAnimation(
            resource = R.raw.error,
            modifier = Modifier.size(200.dp),
            restartOnPlay = false,
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "Close",
            fontFamily = Poppins,
            fontSize = 18.sp,
            color = White,
            modifier = Modifier.clickable {
                navController.popBackStack()
            }
        )
    }
}

@ExperimentalCoilApi
@Composable
fun TopSection(
    tvShow: TvShow,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier.fillMaxSize()
    ) {
        Image(
            painter = rememberImagePainter(
                data = tvShow.imagePath,
                builder = {
                    crossfade(200)
                    error(R.drawable.place_holder)
                }
            ),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.3f)
                .background(
                    Brush.verticalGradient(
                        listOf(
                            Color.Black,
                            Color.Transparent
                        )
                    )
                )
                .align(Alignment.TopCenter),
            contentAlignment = Alignment.TopEnd
        ) {}
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.3f)
                .background(
                    Brush.verticalGradient(
                        listOf(
                            Color.Transparent,
                            Color.Black
                        )
                    )
                )
                .align(Alignment.BottomCenter),
            contentAlignment = Alignment.BottomEnd
        ) {}

        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = null,
            tint = White,
            modifier = Modifier
                .align(Alignment.TopStart)
                .offset(x = 16.dp, y = 48.dp)
                .clickable {
                    navController.popBackStack()
                }
        )
    }
}

@Composable
fun InfoSection(tvShow: TvShow) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = tvShow.name,
            fontFamily = Poppins,
            fontWeight = FontWeight.Bold,
            fontSize = 22.sp,
            color = Color.White,
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "${tvShow.network} | ${tvShow.startDate}",
            fontFamily = Poppins,
            fontWeight = FontWeight.Light,
            fontSize = 12.sp,
            color = Color.LightGray,
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = tvShow.genres.joinToString(" | "),
            fontFamily = Poppins,
            fontWeight = FontWeight.Light,
            fontSize = 12.sp,
            color = Color.LightGray,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = " ${tvShow.description}",
            fontFamily = Poppins,
            fontWeight = FontWeight.Light,
            fontSize = 14.sp,
            color = Color.LightGray,
        )
    }
}

@Composable
fun PictureSection(
    tvShow: TvShow,
    detailViewModel: DetailViewModel = hiltViewModel()
) {
    LazyRow(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 24.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(tvShow.pictures) { imageUrl ->
            PictureItem(imageUrl, detailViewModel.imageLoader)
        }
    }
}

@Composable
fun EpisodeSection(tvShow: TvShow) {
    val result = tvShow.episodes.groupBy(Episode::season)
    result.forEach {
        EpisodeItem(season = it.key, it.value)
    }
}



