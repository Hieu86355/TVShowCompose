package com.example.tvshow.data.models


import com.google.gson.annotations.SerializedName

data class DetailResult(
    @SerializedName("tvShow")
    val tvShow: TvShow
)