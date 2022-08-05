package com.example.moviesapplication.ui.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import coil.ImageLoader

import coil.request.ImageRequest
import com.example.moviesapplication.R
import com.example.moviesapplication.data.network.model.Movie
import com.example.moviesapplication.data.viewmodel.MoviesListViewModel
import com.example.moviesapplication.ui.MovieAppScreen
import com.example.moviesapplication.ui.common.*
import com.example.moviesapplication.ui.theme.backGroundColor
import com.example.moviesapplication.utils.AppConstants.thumbSize185
import com.example.moviesapplication.utils.AppConstants.thumbUrl
import com.skydoves.landscapist.coil.CoilImage
import kotlinx.coroutines.CoroutineScope

import kotlinx.coroutines.flow.Flow

@Composable
fun MainScreen(navController: NavController,mainViewModel: MoviesListViewModel) {
    val scaffoldState = rememberScaffoldState()
    val snackbarCoroutineScope = rememberCoroutineScope()
    Scaffold(
        scaffoldState = scaffoldState,
        backgroundColor = backGroundColor,
        topBar = {
            MainTopAppBar(scaffoldState = scaffoldState, snackbarCoroutineScope)
        },
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(bottom = paddingValues.calculateBottomPadding()),
            content = {
                MovieList(
                    movies = mainViewModel.movies,
                    scaffoldState = scaffoldState,
                    snackbarCoroutineScope = snackbarCoroutineScope, navController = navController
                )
            }
        )
    }
}

val posterURL: String = thumbUrl + thumbSize185 + "/nQRPSUmHGLrFRPK6v3BI1frAM1O.jpg"
val title: String = "Shang Chi"
val Rating: Float = 7.2F

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MovieList(
    movies: Flow<PagingData<Movie>>,
    scaffoldState: ScaffoldState,
    snackbarCoroutineScope: CoroutineScope,
    navController: NavController
) {
    val lazyMovieItems = movies.collectAsLazyPagingItems()
    val context = LocalContext.current
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        LazyVerticalGrid(cells = GridCells.Fixed(2),
            content = {
                items(lazyMovieItems.itemCount) { it: Int ->
                    MovieItem(
                        movie = lazyMovieItems.get(it)!!,
                        onPosterClick = { movieId, scrolId ->
                            navController.navigate(MovieAppScreen.DetailsScreen.withNavArgs(movieId))
                            //Toast.makeText(context, movieId, Toast.LENGTH_LONG).show()
                        })
                }
                lazyMovieItems.apply {
                    when {
                        loadState.refresh is LoadState.Loading -> {
                            item {
                                LoadingView()
                            }
                        }
                        loadState.append is LoadState.Loading -> {
                            item {
                                LoadingItem()
                                /* Box(
                                     Modifier
                                         .height(278.dp)
                                         .width(185.dp),
                                     Alignment.Center
                                 ) {
                                     LoadingItem()
                                 }*/
                            }
                        }
                        loadState.refresh is LoadState.Error -> {
                            val e = lazyMovieItems.loadState.refresh as LoadState.Error
                            item {
                                ErrorItem(
                                    message = e.error.localizedMessage!!,
                                    modifier = Modifier.fillParentMaxSize(),
                                    onClickRetry = { retry() }
                                )
                            }
                        }
                        loadState.append is LoadState.Error -> {
                            val e = lazyMovieItems.loadState.append as LoadState.Error
                            item {
                                ErrorItem(
                                    message = e.error.localizedMessage!!,
                                    onClickRetry = { retry() }
                                )
                            }
                        }
                    }
                }
            })
    }
}


@Composable
fun MovieItem(
    movie: Movie,
    onPosterClick: (Int, Int) -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 2.dp, vertical = 2.dp),
        elevation = 10.dp
    ) {
        Column(
            modifier = modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceEvenly
        ) {

            // Poster
            /*Poster(
                modifier = Modifier
                    .wrapContentWidth(),
                movie = movie,
                onMovieClicked = onMovieClicked
            )*/
            PosterItem(
                poster = movie.poster_path,
                title = movie.title,
                movieId = movie.id,
                scrollId = 1,
                onPosterClick )

            // Title
            Text(
                text = movie.title,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.body2,
                modifier = Modifier
                    .padding(top = 4.dp, start = 5.dp)
                    .align(Alignment.Start)
            )

            // Rating
            Row(
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 5.dp)

            ) {
                // Star
                Icon(
                    modifier = Modifier
                        .padding(end = 4.dp)
                        .requiredSize(12.dp),
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_rating),
                    tint = MaterialTheme.colors.onSurface.copy(alpha = 0.5f),
                    contentDescription = null
                )

                // Rating
                Text(
                    text = movie.vote_average.toString(),
                    color = Color.Black,
                    fontSize = 12.sp,
                    style = MaterialTheme.typography.overline,
                    modifier = Modifier.padding(top = 2.dp)
                )


            }
        }
    }
}

@Composable
fun MovieTestItem() {
    Card(elevation = 10.dp) {
        Row(modifier = Modifier.fillMaxSize(), Arrangement.SpaceEvenly) {
            MovieImage(imageUrl = posterURL)
            MovieTitle(title = title)
            MovieTitle(title = Rating.toString())
        }
    }
}


@Composable
@Preview
fun DisplayPre() {
    MovieTestItem()
}

@Composable
fun MovieImage(
    imageUrl: String,
    modifier: Modifier = Modifier
) {
    CoilImage(
        imageRequest = ImageRequest.Builder(LocalContext.current)
            .data(imageUrl)
            .crossfade(true)
            .build(),
        imageLoader = {
            ImageLoader.Builder(LocalContext.current)
                .availableMemoryPercentage(0.25)
                .crossfade(true)
                .build()
        },
        contentScale = ContentScale.Inside,
        modifier = modifier,
        alignment = Alignment.TopStart,
    )

}

@Composable
fun MovieTitle(
    title: String,
    modifier: Modifier = Modifier
) {
    Text(
        modifier = modifier.wrapContentHeight(),
        text = title,
        maxLines = 2,
        style = MaterialTheme.typography.h6,
        overflow = TextOverflow.Ellipsis
    )
}