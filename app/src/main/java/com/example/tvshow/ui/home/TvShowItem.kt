package com.example.tvshow.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.ImageLoader
import coil.compose.rememberImagePainter
import com.example.tvshow.R
import com.example.tvshow.data.models.Show
import com.example.tvshow.ui.MainDestinations

@Composable
fun TvShowItem(
    show: Show,
    navController: NavController,
    imageLoader: ImageLoader,
    modifier: Modifier = Modifier
) {
    Image(painter = rememberImagePainter(
        data = show.imageThumbnailPath,
        builder = {
            error(R.drawable.place_holder)
        },
        imageLoader = imageLoader
    ),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = modifier
            .width(150.dp)
            .height(220.dp)
            .clickable { navController.navigate("${MainDestinations.DETAIL_ROUTE}/${show.id}") }
            .shadow(16.dp, RoundedCornerShape(percent = 10))
            .clip(RoundedCornerShape(percent = 10)))
}