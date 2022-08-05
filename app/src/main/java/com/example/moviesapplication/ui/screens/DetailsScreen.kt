package com.example.moviesapplication.ui.screens

import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import coil.size.OriginalSize
import com.example.moviesapplication.data.network.model.Movie
import com.example.moviesapplication.data.viewmodel.MovieDetailsViewModel
import com.example.moviesapplication.ui.common.LoadingIndicator
import com.example.moviesapplication.ui.theme.Shapes
import com.example.moviesapplication.ui.theme.backGroundColor

private const val TAG = "DetailsScreen"
@Composable
fun SampleDetails(movie_id: Int?, navController: NavController,detailsViewModel: MovieDetailsViewModel) {

    val scaffoldState = rememberScaffoldState()
    val snackbarCoroutineScope = rememberCoroutineScope()

    val movie= detailsViewModel.getMovieById(movieId = movie_id).collectAsState(initial = null).value
    if(movie!=null) {
        Log.d(TAG, "SampleDetails: Movie Details ${movie.poster_path}")
        Scaffold(topBar = {
            DetailsScreenAppBar(
                toggleFavorite = { /*TODO*/ },
                favorite = 1,
                navController = navController,
                scaffoldState = scaffoldState,
                snackbarCoroutineScope = snackbarCoroutineScope
            )
        }, backgroundColor = backGroundColor,
            content = {

                DetailsBodyContent(
                    modifier = Modifier
                        .padding(it), movie = movie
                )
            })

    }
    Log.d(TAG, "SampleDetails: ")
    /*val detailsViewModel:MovieDetailsViewModel = viewModel()
    val movie=detailsViewModel.getMovieById(movieId = movie_id).collectAsState(initial = null).value
        */



}

val overview =
    "Over the course of one fateful day, a corrupt businessman and his socialite wife race to save their daughter from a notorious crime lord."

@Composable
fun DetailsBodyContent(modifier: Modifier, movie: Movie?) {
    val scrollState = rememberScrollState()

    Column(modifier = Modifier.verticalScroll(scrollState)) {
       Box(  modifier = Modifier.wrapContentWidth(),
           Alignment.Center) {
           BackDropPoster(posterUrl = movie!!.poster_path)
       }
        Card(
            elevation = 10.dp,
            modifier = Modifier.padding(2.dp, 4.dp)
        ) {

            MovieTitleRow(
                title = movie!!.title,
                releaseDate = movie.release_date,
                rating = movie.vote_average.toFloat()
            )
        }
        Card(backgroundColor = backGroundColor) {
            MovieOverView(movie!!.overview)
        }
        TrailersList(movie!!.poster_path)
        CastList()
    }
}

@Composable
fun TrailersList(posterUrl: String) {
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.End,
        modifier = Modifier.padding(start = 4.dp, top = 4.dp,end=4.dp, bottom = 4.dp)

    ) {
        Text(
            text = "Trailer",
            color = Color.White,
            style = MaterialTheme.typography.body2
        )
        //MovieOverView(overview = "Trailer")
    }
    Column(horizontalAlignment = Alignment.CenterHorizontally) {

        Card(
            elevation = 10.dp,
            shape = Shapes.large,
            modifier = Modifier.padding(8.dp, 16.dp)
        ) {
            Row() {
                Box(
                    modifier = Modifier.wrapContentWidth(),
                    Alignment.Center
                ) {
                    BackDropPoster(posterUrl =posterUrl )

                    Box(
                        modifier = Modifier
                            .size(64.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colors.background.copy(alpha = 0.5F)),
                        contentAlignment = Alignment.Center
                    ) {
                        IconButton(onClick = { /*TODO*/ })
                        {
                            Icon(
                                Icons.Default.PlayArrow,
                                contentDescription = "Movie play",
                                modifier = Modifier.size(96.dp)

                            )
                        }
                    }

                }

            }


        }

    }
}

@Composable
fun CastList(){
    Column(
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.End,
        modifier = Modifier.padding(start = 4.dp, top = 4.dp,end=4.dp, bottom = 4.dp)

    ) {
        Text(
            text = "Cast",
            color = Color.White,
            style = MaterialTheme.typography.body2
        )
        //MovieOverView(overview = "Trailer")
    }
}

@Composable
fun MovieOverView(overview: String) {
    var isExpanded by remember { mutableStateOf(false) }
    var badgeCount by remember { mutableStateOf(0) }

    Text(
        text = overview,
        color = Color.White,
        modifier = Modifier
            .padding(horizontal = 8.dp, vertical = 16.dp),
        textAlign = TextAlign.Start,
       /* maxLines = if(isExpanded)Int.MAX_VALUE else 3,
        overflow = if(isExpanded) TextOverflow.Visible else TextOverflow.Ellipsis*/
    )
}

@OptIn(ExperimentalCoilApi::class)
@Composable
fun BackDropPoster(posterUrl: String) {
    val backDropPainter = rememberImagePainter(
        data = posterUrl,
        builder = { size(OriginalSize) }
    )
    Card(elevation = 10.dp, shape = Shapes.small) {
        Image(
            painter = backDropPainter,
            contentDescription = "back Drop Image",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .fillMaxWidth()
                .height(270.dp)

        )
    }
    when (backDropPainter.state) {
        ImagePainter.State.Empty -> {
            Log.d("Detail Screen", "BackDropPoster: Image Empty")
        }
        is ImagePainter.State.Loading -> {
            Box(
                Modifier
                    .height(280.dp)
                    .fillMaxWidth(), Alignment.Center
            ) {
                Log.d("Detail Screen", "BackDropPoster: Image Loading")
                LoadingIndicator()
            }
        }
        is ImagePainter.State.Success -> {
            Log.d("Detail Screen", "BackDropPoster: Image Loading Success")
        }
        is ImagePainter.State.Error -> {

        }
    }
}

@Composable
fun MovieTitleRow(title: String, releaseDate: String, rating: Float) {
    Row(
        modifier = Modifier
            .background(backGroundColor),
    ) {
        Column(
            modifier = Modifier
                .weight(6F, true)
                .fillMaxHeight(),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.h5,
                modifier = Modifier
                    .padding(10.dp),
                color = Color.White,
                textAlign = TextAlign.Center
            )
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp, 16.dp)
            ) {
                ReleaseDate(
                    movie = releaseDate,
                    tint = MaterialTheme.colors.onSecondary.copy(alpha = 0.6f)
                )
                MovieRating(
                    movie = rating,
                    tint = MaterialTheme.colors.onSecondary.copy(alpha = 0.6f)
                )
            }
        }
    }

}
@Composable
fun ReleaseDate(movie: String?, tint: Color) {
    Row(horizontalArrangement = Arrangement.Center) {
        Icon(
            Icons.Filled.DateRange,
            contentDescription = "${movie}",
            tint = tint
        )
        Text(
            "${movie}",
            Modifier.padding(start = 8.dp),
            color = tint
        )
    }
}

@Composable
fun MovieRating(movie: Float?, tint: Color) {
    Row(horizontalArrangement = Arrangement.Center) {
        Icon(
            Icons.Filled.Star,
            contentDescription = "${movie}"

        )
        Text(
            "${movie} / 10",
            Modifier.padding(start = 8.dp)

        )
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun PreviewDetails() {
   // DetailsBodyContent(modifier = Modifier.padding(16.dp), movie = null)
    // MovieTitleRow(title = "Dragon Ball Super: Super Hero", movieData = "2022-06-01", rating = 8.3F)
    var showMore by remember { mutableStateOf(false) }
    val text =
        "Space Exploration Technologies Corp. (doing business as SpaceX) is an American aerospace manufacturer, space transportation services and communications corporation headquartered in Hawthorne, California. SpaceX was founded in 2002 by Elon Musk with the goal of reducing space transportation costs to enable the colonization of Mars. SpaceX manufactures the Falcon 9 and Falcon Heavy launch vehicles, several rocket engines, Cargo Dragon, crew spacecraft and Starlink communications satellites."

    Column(modifier = Modifier.padding(20.dp)) {
        Column(modifier = Modifier
            .animateContentSize(animationSpec = tween(100))
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null
            ) { showMore = !showMore }) {

            if (showMore) {
                Text(text = text)
            } else {
                Text(text = text, maxLines = 3, overflow = TextOverflow.Ellipsis)
            }
        }
    }
}