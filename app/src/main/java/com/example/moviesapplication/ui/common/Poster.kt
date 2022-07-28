package com.example.moviesapplication.ui.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MovieCreation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.VectorProperty
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.widget.ConstraintLayout
import coil.ImageLoader
import coil.annotation.ExperimentalCoilApi
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import coil.request.ImageRequest
import coil.size.Scale
import coil.transform.RoundedCornersTransformation
import com.example.moviesapplication.R
import com.example.moviesapplication.data.network.model.Movie
import com.example.moviesapplication.ui.screens.posterURL
import com.example.moviesapplication.ui.screens.title
import com.skydoves.landscapist.CircularReveal
import com.skydoves.landscapist.coil.CoilImage


@Composable
fun Poster(
    modifier: Modifier = Modifier,
    movie: Movie,
    onMovieClicked: (Movie) -> Unit
) {
    Card(
        modifier = Modifier
            .clickable(onClick = { onMovieClicked(movie) })
            .fillMaxSize()
    ) {
        CoilImage(
            imageRequest = ImageRequest.Builder(LocalContext.current)
                .data(movie.poster_path)
                .crossfade(true).placeholder(R.drawable.poster_loading)
                .build(),
            imageLoader = {
                ImageLoader.Builder(LocalContext.current)
                    .availableMemoryPercentage(0.25)
                    .crossfade(true)
                    .error(R.drawable.poster_error)
                    .build()
            },
            contentScale = ContentScale.Crop,
            modifier = modifier.fillMaxSize(),
            //circularReveal = CircularReveal(duration = 300).takeIf { true },

            alignment = Alignment.Center,
        )
    }
}
@Composable
@Preview
fun DisplayPreview(){
    //PosterItem(poster = posterURL, title = title , movieId =22 , scrollId =3 , onPosterClick ={movieId,scrollId-> } )
}

@OptIn(ExperimentalCoilApi::class)
@Composable
fun PosterItem(
    poster: String?,
    title: String?,
    movieId: Int,
    scrollId: Int,
    onPosterClick: (Int, Int) -> Unit,
) {
    val posterPainter = rememberImagePainter(data = poster,
        builder = {
            crossfade(true)
            scale(Scale.FILL)
        })
    Box(
        Modifier
            .height(278.dp)
            .wrapContentWidth()
    ) {
        Image(
            painter = posterPainter,
            contentDescription = title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = { onPosterClick(movieId, scrollId) })
        )
    }
    val state=posterPainter.state
    when(state){
        is ImagePainter.State.Empty->{

        }
        is ImagePainter.State.Loading->{
            LoadingIndicator()
            /*Box(
                Modifier
                    .height(278.dp)
                    .width(185.dp),
                Alignment.Center
            ) {
                LoadingIndicator()
            }*/
        }
        is ImagePainter.State.Success->{}
        is ImagePainter.State.Error->{
            Box(
                Modifier
                    .height(278.dp)
                    .width(185.dp)
                    .border(1.dp, MaterialTheme.colors.secondary.copy(alpha = 0.5F))
                ,Alignment.Center){
                Icon(
                    Icons.Default.MovieCreation,
                    contentDescription = title,
                    modifier = Modifier
                        .size(120.dp)
                        .align(Alignment.Center),
                    tint = Color.Black.copy(0.5F)
                )
                if (title != null) {
                    Text(
                        title,
                        Modifier
                            .align(Alignment.BottomCenter)
                            .padding(bottom = 8.dp, start = 16.dp, end = 16.dp),
                        style = MaterialTheme.typography.body1
                    )
                }
            }
        }
    }
}

private val cardWidth = 150.dp

@Preview
@Composable
fun DefaultPreview() {

}
