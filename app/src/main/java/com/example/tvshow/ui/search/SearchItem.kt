package com.example.tvshow.ui.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.ImageLoader
import coil.compose.rememberImagePainter
import com.example.tvshow.R
import com.example.tvshow.data.models.Show
import com.example.tvshow.ui.MainDestinations
import com.example.tvshow.ui.theme.Poppins

@Composable
fun SearchItem(
    show: Show,
    imageLoader: ImageLoader,
    navController: NavController
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .clickable { navController.navigate("${MainDestinations.DETAIL_ROUTE}/${show.id}") },
    ) {
        Image(
            painter = rememberImagePainter(
                data = show.imageThumbnailPath,
                builder = {
                    error(R.drawable.place_holder)
                },
                imageLoader = imageLoader
            ),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .width(100.dp)
                .height(140.dp)
                .shadow(16.dp, RoundedCornerShape(percent = 10))
                .clip(RoundedCornerShape(percent = 10))
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 10.dp, top = 10.dp, bottom = 10.dp)
        ) {
            Text(
                text = show.name,
                fontFamily = Poppins,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                color = Color.White,
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Country: ${show.country}",
                fontFamily = Poppins,
                fontWeight = FontWeight.Light,
                fontSize = 12.sp,
                color = Color.LightGray,
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Network: ${show.network}",
                fontFamily = Poppins,
                fontWeight = FontWeight.Light,
                fontSize = 12.sp,
                color = Color.LightGray,
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Status: ${show.status}",
                fontFamily = Poppins,
                fontWeight = FontWeight.Light,
                fontSize = 12.sp,
                color = Color.LightGray,
            )
        }
    }
}

@Preview
@Composable
fun SearchItemPreview() {

}