@file:OptIn(ExperimentalAnimationApi::class)

package com.example.moviesapplication.ui.screens

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MovieCreation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.annotation.ExperimentalCoilApi
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import coil.size.Scale
import com.example.moviesapplication.R
import com.example.moviesapplication.data.network.model.Movie
import com.example.moviesapplication.ui.common.LoadingIndicator


@Composable
fun MovieScreen(){
    val scaffoldState= rememberScaffoldState()
    val context= LocalContext.current
    val snackbarCoroutineScope= rememberCoroutineScope()
    Scaffold(
        scaffoldState = scaffoldState,
        topBar = { MainTopAppBar(scaffoldState, snackbarCoroutineScope) },
        content = {innerPadding->
                Card(
                    modifier = Modifier
                        .wrapContentWidth()
                        .padding(horizontal = 5.dp, vertical = 5.dp),
                        elevation = 10.dp,
                        backgroundColor = MaterialTheme.colors.surface
                ) {
                  SampleImage( onItemSeleted ={movie, i ->
                      Toast.makeText(context,"Text",Toast.LENGTH_LONG).show()
                  } )
                }


        },
        /*floatingActionButton = {
            FloatingActionButton(onClick = { *//*TODO*//* }) {
            }
        },*/
    )
}
@Composable
fun SampleImage(onItemSeleted:(Movie,Int)->Unit){
    val painter = rememberImagePainter(R.drawable.test_poster)
    val state = painter.state
    Box(
        contentAlignment = Alignment.TopStart,
        modifier = Modifier.wrapContentWidth()
    ) {
        AnimatedVisibility(visible = (state is ImagePainter.State.Loading)) {
            CircularProgressIndicator()
        }
        Image(
            painter = painter,
            contentDescription = null,
            modifier = Modifier
                .size(130.dp)
                .padding(vertical = 5.dp, horizontal = 5.dp)
                .clickable { onItemSeleted }
        )
    }
}
@Composable
fun MovieItemCard(movie: Movie, onMovieItemClicked: (Int) -> Unit, modifier: Modifier) {
    PosterItem(poster = movie.poster_path , title =movie.title , movieId =movie.id ,  onPosterClick =onMovieItemClicked)
}
@ExperimentalCoilApi
@Composable
fun PosterItem(
    poster: String?,
    title: String?,
    movieId: Int,
    onPosterClick: (Int) -> Unit
) {
    val posterPainter = rememberImagePainter(
        data = poster,
        builder = {
            crossfade(true)
            scale(Scale.FILL)})
    Box(
        Modifier
            .border(width = 1.dp, color = MaterialTheme.colors.secondary)
            .height(278.dp)
            .width(185.dp)
    ) {
        Image(
            painter = posterPainter,
            contentDescription = title,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = {
                    onPosterClick(movieId)
                })
        )
    }
    val imageState = posterPainter.state
    if (imageState is ImagePainter.State.Loading) {
        Box(
            Modifier
                .height(278.dp)
                .width(185.dp),
            Alignment.Center
        ) {
            LoadingIndicator()
        }
    }
    if (imageState is ImagePainter.State.Error) {
        Box(
            Modifier
                .height(278.dp)
                .width(185.dp)
                .border(
                    1.dp, MaterialTheme.colors.secondary.copy(alpha = 0.1F)
                ),
            Alignment.Center
        ) {
            Icon(
                Icons.Default.MovieCreation,
                title,
                modifier = Modifier
                    .size(120.dp)
                    .align(Alignment.Center),
                tint = Color.Black.copy(alpha = 0.2F)
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
@Composable
@Preview(showBackground = true)
fun DisplayPreview(){
    MovieScreen()
}
/*@Composable
fun MovieItem(movie: Movie) {
    Row(
        modifier = Modifier
            .padding(start = 16.dp, top = 16.dp, end = 16.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        MovieTitle(
            movie.title!!,
            modifier = Modifier.weight(1f)
        )
        MovieImage(
            BuildConfig.LARGE_IMAGE_URL + movie.backdrop_path,
            modifier = Modifier.padding(start = 16.dp).preferredSize(90.dp)
        )
    }
}*/

/*@Composable
fun MovieImage(
    imageUrl: String,
    modifier: Modifier = Modifier
) {
    CoilImage(
        data = imageUrl,
        modifier = modifier,
        fadeIn = true,
        contentScale = ContentScale.Crop,
        loading = {
            Image(vectorResource(id = R.drawable.ic_photo), alpha = 0.45f)
        },
        error = {
            Image(vectorResource(id = R.drawable.ic_broken_image), alpha = 0.45f)
        }
    )
}

@Composable
fun MovieTitle(
    title: String,
    modifier: Modifier = Modifier
) {
    Text(
        modifier = modifier,
        text = title,
        maxLines = 2,
        style = MaterialTheme.typography.h6,
        overflow = TextOverflow.Ellipsis
    )
}*/
