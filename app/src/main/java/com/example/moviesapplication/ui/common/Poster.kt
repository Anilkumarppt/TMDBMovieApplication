package com.example.moviesapplication.ui.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.requiredHeight
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.widget.ConstraintLayout
import coil.ImageLoader
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import coil.size.Scale
import com.example.moviesapplication.R
import com.example.moviesapplication.data.network.model.Movie
import com.skydoves.landscapist.coil.CoilImage

@Composable
fun PosterImage(
    modifier: Modifier = Modifier,
    movie: Movie,
    onMovieClicked: (Movie) -> Unit
) {
    Card(
        modifier = Modifier.clickable(onClick = { onMovieClicked(movie) })
    ) {
        Image(
            painter = rememberImagePainter(data = movie.poster_path, builder = {
                crossfade(true)
                scale(Scale.FILL)
            }),
            modifier = modifier,
            contentDescription = null
        )
    }
}

@Composable
fun Poster(
    modifier: Modifier = Modifier,
    movie: Movie,
    onMovieClicked: (Movie?) -> Unit
) {
    Card(
        modifier = Modifier.clickable(onClick = { onMovieClicked(movie) })
    ) {
        CoilImage(
            imageRequest = ImageRequest.Builder(LocalContext.current)
                .data(movie.poster_path)
                .crossfade(true)
                .build(),
            imageLoader = {
                ImageLoader.Builder(LocalContext.current)
                    .availableMemoryPercentage(0.25)
                    .crossfade(true)
                    .build()
            },
            contentScale = ContentScale.Crop,
            modifier = modifier,
            alignment = Alignment.Center,
        )
    }
}
private val cardWidth = 150.dp
@Preview
@Composable
fun DefaultPreview(){

}
