package com.example.tvshow.data

import com.example.tvshow.data.models.DetailResult
import com.example.tvshow.data.models.PopularResult
import com.example.tvshow.data.models.SearchResult
import retrofit2.http.GET
import retrofit2.http.Query

interface TvShowApi {

    @GET("/api/most-popular")
    suspend fun getPopularShows(
        @Query("page") page: Int
    ): PopularResult

    @GET(" /api/show-details")
    suspend fun getShowDetails(
        @Query("q") id: Int
    ): DetailResult

    @GET("/api/search")
    suspend fun getSearch(
        @Query("q") query: String,
        @Query("page") page: Int
    ): SearchResult

}