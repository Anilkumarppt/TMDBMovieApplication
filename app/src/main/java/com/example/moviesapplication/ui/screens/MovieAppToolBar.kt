package com.example.moviesapplication.ui.screens

import androidx.compose.runtime.Composable


import androidx.compose.foundation.layout.*
import androidx.compose.material.*

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.moviesapplication.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch


@Composable
fun MainTopAppBar(scaffoldState: ScaffoldState, snackbarCoroutineScope: CoroutineScope) =
    TopAppBar(
        title = {
            Text(
                textAlign = TextAlign.Center,
                text = "Hollywood Movies",
                fontSize = 18.sp
            )
        },
        backgroundColor = Color.DarkGray,
        contentColor = Color.White,
        navigationIcon = {
            IconButton(onClick = { snackbarCoroutineScope.launch {
                scaffoldState.snackbarHostState.showSnackbar("TODO implement Navigation Drawer")
            }}) {
                Icon(Icons.Filled.Menu, contentDescription = "")
            }
        },
        actions = {
            IconButton(onClick = {snackbarCoroutineScope.launch {
                scaffoldState.snackbarHostState.showSnackbar("TODO Sort movies ") } }) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_star),
                    "Sort movies",
                    tint = Color.White
                )
            }
            IconButton(onClick = { snackbarCoroutineScope.launch {
                scaffoldState.snackbarHostState.showSnackbar("TODO Implement Search") }})
            {
                Icon(
                    Icons.Filled.Search,
                    null, tint = Color.White
                )
            }
            IconButton(onClick = {snackbarCoroutineScope.launch {
                scaffoldState.snackbarHostState.showSnackbar("TODO Implement Favorite  ") } }) {

                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_heart),
                    "Favorite",
                    tint = Color.Red
                )
            }

        },
        elevation = 10.dp
    )



@Composable
@Preview
fun DefaultPreview() {
    //ScaffoldWithTopBar()
}