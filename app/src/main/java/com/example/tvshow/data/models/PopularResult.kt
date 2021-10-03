package com.example.tvshow.data.models


import com.google.gson.annotations.SerializedName

data class PopularResult(
    @SerializedName("page")
    val page: Int,
    @SerializedName("pages")
    val pages: Int,
    @SerializedName("total")
    val total: String,
    @SerializedName("tv_shows")
    val shows: List<Show>
)