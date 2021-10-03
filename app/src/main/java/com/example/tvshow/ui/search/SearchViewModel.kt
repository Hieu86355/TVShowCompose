package com.example.tvshow.ui.search

import android.util.Log
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.ImageLoader
import com.example.tvshow.data.TvShowRepository
import com.example.tvshow.data.models.SearchResult
import com.example.tvshow.data.models.Show
import com.example.tvshow.utils.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: TvShowRepository,
    val imageLoader: ImageLoader
) : ViewModel() {

    var query: String = ""
    var searchResults = mutableStateOf<List<Show>>(listOf())
    var isLoading = mutableStateOf(false)
    var error = mutableStateOf("")
    var isLastPage = mutableStateOf(false)
    var currentPage = 1

    fun getSearch() {
        viewModelScope.launch {
            isLoading.value = true
            val searchResponse = repository.getSearch(query, currentPage)
            when (searchResponse) {
                is NetworkResult.Success -> {
                    responseSuccess(searchResponse)
                }

                is NetworkResult.Error -> {
                    responseError(searchResponse)
                }

                is NetworkResult.Loading -> {
                    isLoading.value = true
                }
            }
        }
    }

    private fun responseSuccess(searchResponse: NetworkResult.Success<SearchResult>) {
        Log.d("AAA", "responseSuccess: ${searchResponse.data.toString()}")
        isLastPage.value = searchResponse.data?.page == searchResponse.data?.pages
        if (!isLastPage.value) {
            currentPage++
        }
        isLoading.value = false
        error.value = ""
        searchResults.value += searchResponse.data?.tvShows!!
    }

    private fun responseError(response: NetworkResult.Error<SearchResult>) {
        isLoading.value = false
        error.value = response.message ?: "An error has occurred"
    }

    fun clearSearchResult() {
        searchResults.value = emptyList()
        currentPage = 1
    }
}