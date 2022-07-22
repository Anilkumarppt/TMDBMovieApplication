

package com.example.moviesapplication.ui.screens

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import coil.ImageLoader

import coil.request.ImageRequest
import com.example.moviesapplication.R
import com.example.moviesapplication.data.network.model.Movie
import com.example.moviesapplication.data.viewmodel.MoviesListViewModel
import com.example.moviesapplication.ui.common.ErrorItem
import com.example.moviesapplication.ui.common.LoadingItem
import com.example.moviesapplication.ui.common.LoadingView
import com.example.moviesapplication.ui.common.PosterImage
import com.example.moviesapplication.utils.AppConstants.thumbSize185
import com.example.moviesapplication.utils.AppConstants.thumbUrl
import com.skydoves.landscapist.coil.CoilImage

import kotlinx.coroutines.flow.Flow

@Composable
fun MainScreen(mainViewModel:MoviesListViewModel) {
	Scaffold(
		topBar = {
			MainTopAppBar()
		}
	) { paddingValues ->
		Column(
			modifier = Modifier
				.padding(bottom = paddingValues.calculateBottomPadding())
		)
		{ MovieList(movies = mainViewModel.movies) }
	}
}
val posterURL:String=thumbUrl+thumbSize185+"/nQRPSUmHGLrFRPK6v3BI1frAM1O.jpg"
val title:String="Shang Chi"
val Rating:Float=7.2F
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MovieList(movies: Flow<PagingData<Movie>>) {
	val lazyMovieItems = movies.collectAsLazyPagingItems()

		Column(modifier = Modifier.fillMaxSize()) {
			LazyVerticalGrid(cells = GridCells.Fixed(2), content =  {
				items(lazyMovieItems.itemSnapshotList) { value: Movie? ->
					MovieItem(movie = value!!, onMovieClicked = {})
				}
				lazyMovieItems.apply {
					when {
						loadState.refresh is LoadState.Loading -> {
							item { LoadingView(modifier = Modifier.fillParentMaxSize()) }
						}
						loadState.append is LoadState.Loading -> {
							item { LoadingItem() }
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

private val cardWidth = 150.dp
@Composable
fun MovieItem(
	movie: Movie,
	onMovieClicked: (Movie) -> Unit,
	modifier: Modifier = Modifier
) {
	Column(
		modifier = modifier
			.fillMaxWidth()
			.padding(10.dp)
	) {

		// Poster
		PosterImage(
			modifier = Modifier
				.requiredWidth(cardWidth)
				.requiredHeight(200.dp),
			movie = movie,
			onMovieClicked = onMovieClicked
		)

		// Title
		Text(
			text = movie.title,
			maxLines = 1,
			overflow = TextOverflow.Ellipsis,
			style = MaterialTheme.typography.body2,
			modifier = Modifier.padding(top = 4.dp)
		)

		// Rating
		Row(
			verticalAlignment = Alignment.CenterVertically
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
				color = MaterialTheme.colors.onSurface.copy(alpha = 0.5f),
				style = MaterialTheme.typography.overline,
				modifier = Modifier.padding(top = 2.dp)
			)

		}
	}
}
@Composable
fun MovieTestItem(){
	Card(elevation = 10.dp) {
		Row(modifier = Modifier.fillMaxSize(),Arrangement.SpaceEvenly) {
			MovieImage(imageUrl = posterURL)
			MovieTitle(title = title)
			MovieTitle(title = Rating.toString())
		}
	}
}


@Composable
@Preview
fun DisplayPre(){
	MovieTestItem()
}
/*@Composable
fun MovieItem(movie: Movie) {
	Card(elevation = 10.dp) {
		Row(
			modifier = Modifier
				.padding(start = 16.dp, top = 16.dp, end = 16.dp),
			horizontalArrangement = Arrangement.SpaceBetween,
			verticalAlignment = Alignment.Top
		) {
			MovieImage(
				movie.poster_path,
				modifier = Modifier
					.size(120.dp)
			)
			MovieTitle(
				movie.title!!,
				modifier = Modifier.weight(1f)
			)

		}
	}
}*/

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
		modifier = modifier,
		text = title,
		maxLines = 2,
		style = MaterialTheme.typography.h6,
		overflow = TextOverflow.Ellipsis
	)
}