package com.example.tvshow.data.models


import com.google.gson.annotations.SerializedName

data class SearchResult(
    @SerializedName("page")
    val page: Int,
    @SerializedName("pages")
    val pages: Int,
    @SerializedName("total")
    val total: String,
    @SerializedName("tv_shows")
    val tvShows: List<Show>
)