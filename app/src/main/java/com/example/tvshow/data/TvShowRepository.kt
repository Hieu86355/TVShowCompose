package com.example.tvshow.data

import com.example.tvshow.data.models.DetailResult
import com.example.tvshow.data.models.PopularResult
import com.example.tvshow.data.models.SearchResult
import com.example.tvshow.utils.NetworkResult
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

@ViewModelScoped
class TvShowRepository @Inject constructor(
    private val api: TvShowApi
) {

    suspend fun getPopularShows(page: Int): NetworkResult<PopularResult> {
        val response = try {
            api.getPopularShows(page)
        } catch (e: Exception) {
            return NetworkResult.Error(e.message)
        }
        return NetworkResult.Success(response)
    }

    suspend fun getShowDetail(id: Int): NetworkResult<DetailResult> {
        val response = try {
            api.getShowDetails(id)
        } catch (e: Exception) {
            return NetworkResult.Error(e.message)
        }
        return NetworkResult.Success(response)
    }

    suspend fun getSearch(query: String, page: Int): NetworkResult<SearchResult> {
        val response = try {
            api.getSearch(query, page)
        } catch (e: Exception) {
            return NetworkResult.Error(e.message)
        }
        return NetworkResult.Success(response)
    }
}