package com.example.tvshow.ui.detail

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tvshow.data.models.Episode
import com.example.tvshow.ui.theme.Poppins


@Composable
fun EpisodeItem(season: Int, episodes: List<Episode>) {
    var expandedState by remember { mutableStateOf(false) }
    var selectedBackground by remember { mutableStateOf(Color.Gray) }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
            .animateContentSize(
                animationSpec = tween(
                    delayMillis = 200,
                    easing = LinearOutSlowInEasing
                )
            )
    ) {
        Spacer(modifier = Modifier.height(4.dp))
        SeasonHeader(
            season = season,
            background = selectedBackground,
            onHeaderClick = { expandedState = !expandedState }
        )
        if (expandedState) {
            selectedBackground = Color.Red
            episodes.forEach {
                EpisodeRow(season = season, episode = it)
            }
        } else {
            selectedBackground = Color.Gray
        }
    }
}

@Composable
fun SeasonHeader(
    season: Int,
    background: Color,
    onHeaderClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.DarkGray)
            .clickable {
                onHeaderClick()
            }
    ) {
        Box(
            modifier = Modifier
                .width(100.dp)
                .height(50.dp)
                .background(background),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "$season",
                fontFamily = Poppins,
                fontWeight = FontWeight.Bold,
                fontSize = 22.sp,
                color = Color.White
            )
        }

        Text(
            text = "Season $season",
            fontFamily = Poppins,
            fontWeight = FontWeight.Normal,
            fontSize = 16.sp,
            color = Color.LightGray,
            modifier = Modifier.padding(start = 16.dp)
        )

    }
}

@Composable
fun EpisodeRow(season: Int, episode: Episode) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .width(100.dp)
                .height(50.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "$season - ${episode.episode}",
                fontFamily = Poppins,
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
                color = Color.LightGray
            )
        }

        Box(modifier = Modifier
            .height(35.dp)
            .width(1.dp)
            .background(Color.LightGray))

        Column(
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "${episode.name}",
                fontFamily = Poppins,
                fontWeight = FontWeight.Light,
                fontSize = 14.sp,
                color = Color.LightGray,
                modifier = Modifier.padding(start = 16.dp)
            )
            Text(
                text = "${episode.airDate.subSequence(0, 10)}",
                fontFamily = Poppins,
                fontWeight = FontWeight.Light,
                fontSize = 12.sp,
                color = Color.DarkGray,
                modifier = Modifier.padding(start = 16.dp)
            )
        }
    }
}
