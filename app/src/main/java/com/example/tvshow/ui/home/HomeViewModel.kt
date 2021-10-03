package com.example.tvshow.ui.home

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.ImageLoader
import com.example.tvshow.data.TvShowRepository
import com.example.tvshow.data.models.PopularResult
import com.example.tvshow.data.models.Show
import com.example.tvshow.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: TvShowRepository,
    val imageLoader: ImageLoader
) : ViewModel() {

    var homeScreenLoading = mutableStateOf(true)

    var popularShows = mutableStateOf<List<Show>>(listOf())
    var isLoading = mutableStateOf(false)
    var error = mutableStateOf("")
    var isLastPage = mutableStateOf(false)
    var currentPage = 1

    init {
        homeScreenLoading.value = true
        getPopularShows()
    }

    fun getPopularShows() {
        viewModelScope.launch {
            isLoading.value = true
            val response = repository.getPopularShows(currentPage)
            when (response) {
                is NetworkResult.Success -> {
                    responseSuccess(response)
                }

                is NetworkResult.Error -> {
                    responseError(response)
                }

                is NetworkResult.Loading -> {
                    isLoading.value = true
                }
            }
            homeScreenLoading.value = false
        }
    }


    private fun responseSuccess(response: NetworkResult.Success<PopularResult>) {
        isLastPage.value = response.data?.page == response.data?.pages
        if (!isLastPage.value) {
            currentPage++
        }
        isLoading.value = false
        error.value = ""
        popularShows.value += response.data?.shows!!
    }

    private fun responseError(response: NetworkResult.Error<PopularResult>) {
        isLoading.value = false
        error.value = response.message ?: "An error has occurred"
    }


}