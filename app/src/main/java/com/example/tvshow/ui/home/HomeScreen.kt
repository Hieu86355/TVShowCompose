package com.example.tvshow.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.tvshow.R
import com.example.tvshow.data.models.Show
import com.example.tvshow.ui.MainDestinations.SEARCH_ROUTE
import com.example.tvshow.ui.home.HomeViewModel
import com.example.tvshow.ui.home.TvShowItem
import com.example.tvshow.ui.theme.Poppins
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@ExperimentalAnimationApi
@Composable
fun HomeScreen(
    navController: NavController,
    homeViewModel: HomeViewModel = hiltViewModel()
) {
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberScaffoldState()
    Scaffold(
        scaffoldState = scaffoldState,
        backgroundColor = Color.Black,
        modifier = Modifier.fillMaxSize()
    ) {
        val homeScreenLoading by remember { homeViewModel.homeScreenLoading }
        val popularShows by remember { homeViewModel.popularShows }
        val isLoading by remember { homeViewModel.isLoading }
        val isLastPage by remember { homeViewModel.isLastPage }
        val error by remember { homeViewModel.error }

        if (homeScreenLoading) {
            HomeScreenLoading()
        } else {
            HomeScreenSuccess(
                navController = navController,
                popularShows = popularShows,
                isLoading = isLoading,
                isLastPage = isLastPage,
                scope = scope,
                isSnackbarShowing = scaffoldState.snackbarHostState.currentSnackbarData != null
            )
        }
        if (error.isNotEmpty()) {
            scope.launch {
                val actionResult = scaffoldState.snackbarHostState.showSnackbar(
                    error,
                    duration = SnackbarDuration.Indefinite,
                    actionLabel = "Retry"
                )
                when (actionResult) {
                    SnackbarResult.ActionPerformed -> homeViewModel.getPopularShows()
                }
            }
        } else {
            scaffoldState.snackbarHostState.currentSnackbarData?.dismiss()
        }
    }
}

@ExperimentalAnimationApi
@Composable
fun HomeScreenSuccess(
    navController: NavController,
    popularShows: List<Show>,
    isLoading: Boolean,
    isLastPage: Boolean,
    scope: CoroutineScope,
    homeViewModel: HomeViewModel = hiltViewModel(),
    isSnackbarShowing: Boolean
) {
    Box(modifier = Modifier.fillMaxSize()) {
        val listState = rememberLazyListState()
        LazyColumn(
            state = listState,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            contentPadding = PaddingValues(bottom = 100.dp)
        ) {

            item {
                SearchSection(
                    navController = navController,
                    modifier = Modifier.padding(top = 48.dp, bottom = 8.dp)
                )

                Text(
                    text = "Top 10 TV Shows",
                    fontFamily = Poppins,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White,
                    modifier = Modifier.padding(start = 12.dp, top = 12.dp)
                )

                LazyRow(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.padding(16.dp)
                ) {
                    items(popularShows.take(10)) { show ->
                        TvShowItem(
                            show = show,
                            navController = navController,
                            imageLoader = homeViewModel.imageLoader
                        )
                    }
                }

                Text(
                    text = "Most popular TV Shows",
                    fontFamily = Poppins,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White,
                    modifier = Modifier.padding(start = 12.dp, top = 12.dp)
                )
            }

            val chunkedList = popularShows.drop(10).chunked(2)
            itemsIndexed(items = chunkedList) { rowIndex, rowItems ->
                if (rowIndex >= chunkedList.size - 1 && !isLoading && !isLastPage) {
                    LaunchedEffect(key1 = true) {
                        homeViewModel.getPopularShows()
                    }
                }

                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp, vertical = 6.dp)
                ) {
                    for (show in rowItems) {
                        TvShowItem(
                            show = show,
                            navController = navController,
                            imageLoader = homeViewModel.imageLoader
                        )
                    }
                }
            }

            if (isLoading && !isSnackbarShowing) {
                item {
                    LoadAnimation(
                        resource = R.raw.loading,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp)
                            .padding(vertical = 16.dp),
                        speed = 2.5f
                    )
                }
            }
        }

        /**
         * Scroll to top button
         */
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
fun HomeScreenLoading(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize(),
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
fun SearchSection(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White)
            .clickable {
                navController.navigate(SEARCH_ROUTE)
            }

    ) {
        Text(
            text = "Search",
            color = Color.DarkGray,
            fontSize = 14.sp,
            fontFamily = Poppins,
            fontWeight = FontWeight.Medium,
            modifier = Modifier
                .align(Alignment.CenterStart)
                .padding(start = 16.dp)
        )

        Icon(
            imageVector = Icons.Default.Search,
            contentDescription = null,
            tint = Color.DarkGray,
            modifier = Modifier
                .align(Alignment.CenterEnd)
                .padding(end = 8.dp, top = 10.dp, bottom = 10.dp)
        )
    }

}


