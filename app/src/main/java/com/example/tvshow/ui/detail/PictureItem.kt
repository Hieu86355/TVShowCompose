package com.example.tvshow.ui.detail

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.ImageLoader
import coil.compose.rememberImagePainter
import com.example.tvshow.R

@Composable
fun PictureItem(
    imageUrl: String,
    imageLoader: ImageLoader,
    modifier: Modifier = Modifier,
) {
    Image(painter = rememberImagePainter(
        data = imageUrl,
        builder = {
            error(R.drawable.place_holder)
        },
        imageLoader = imageLoader
    ),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = modifier
            .width(220.dp)
            .height(150.dp)
            .shadow(16.dp, RoundedCornerShape(8.dp))
            .clip(RoundedCornerShape(8.dp)))
}