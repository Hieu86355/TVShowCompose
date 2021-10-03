package com.example.tvshow.ui

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import coil.annotation.ExperimentalCoilApi
import com.example.tvshow.ui.MainDestinations.DETAIL_ROUTE
import com.example.tvshow.ui.MainDestinations.HOME_ROUTE
import com.example.tvshow.ui.MainDestinations.SEARCH_ROUTE
import com.example.tvshow.ui.detail.DetailScreen
import com.example.tvshow.ui.search.SearchScreen

object MainDestinations {
    const val HOME_ROUTE = "home"
    const val DETAIL_ROUTE = "detail"
    const val SEARCH_ROUTE = "search"
}

@ExperimentalCoilApi
@ExperimentalAnimationApi
@Composable
fun TVShowNavGraph(
    navController: NavHostController
) {
    NavHost(navController = navController, startDestination = HOME_ROUTE) {

        composable(HOME_ROUTE) {
            HomeScreen(navController = navController)
        }

        composable(
            route = "${DETAIL_ROUTE}/{tvShowId}",
            arguments = listOf(
                navArgument("tvShowId") {
                    type = NavType.IntType
                }
            )
        ) {
            val tvShowId = remember {
                it?.arguments?.getInt("tvShowId")
            }
            DetailScreen(
                showId = tvShowId!!,
                navController = navController
            )
        }


        composable(SEARCH_ROUTE) {
            SearchScreen(navController)
        }
    }
}