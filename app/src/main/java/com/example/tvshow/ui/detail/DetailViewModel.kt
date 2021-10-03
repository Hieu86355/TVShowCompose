package com.example.tvshow.ui.detail

import androidx.lifecycle.ViewModel
import coil.ImageLoader
import com.example.tvshow.data.TvShowRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val repository: TvShowRepository,
    val imageLoader: ImageLoader
): ViewModel() {

    suspend fun getShowDetail(id: Int) = repository.getShowDetail(id)
}