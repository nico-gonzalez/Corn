package com.ng.tvshowsdb.shared.movie.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.ng.tvshowsdb.shared.movies.domain.model.Movie
import corn.shared.generated.resources.Res
import corn.shared.generated.resources.star_filled
import org.jetbrains.compose.resources.painterResource


@Composable
fun MovieCardItem(
    movie: Movie,
    onNavigateToMovieDetail: (Movie) -> Unit,
) {
    Card(
        modifier = Modifier
            .height(240.dp)
            .aspectRatio(3f / 4f)
            .clickable {
                onNavigateToMovieDetail(movie)
            },
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            AsyncImage(
                contentScale = ContentScale.Crop,
                modifier = Modifier.fillMaxSize(),
                model = movie.imageUrl,
                contentDescription = null,
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .align(Alignment.BottomStart)
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.Transparent,
                                Color.Black.copy(alpha = 0.5f),
                                Color.Black
                            )
                        )
                    )
                    .padding(all = 8.dp),
                verticalArrangement = Arrangement.Bottom
            ) {
                Text(
                    text = movie.title,
                    color = Color.White,
                    style = MaterialTheme.typography.body1
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        modifier = Modifier.size(16.dp),
                        painter = painterResource(Res.drawable.star_filled),
                        contentDescription = null,
                        tint = MaterialTheme.colors.onBackground
                    )
                    Text(
                        text = movie.displayRating,
                        color = Color.White,
                        style = MaterialTheme.typography.body2
                    )
                }

            }
        }
    }
}