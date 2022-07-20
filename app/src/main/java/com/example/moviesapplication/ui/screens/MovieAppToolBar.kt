package com.example.moviesapplication.ui.screens

import androidx.compose.foundation.background
import androidx.compose.runtime.Composable

import com.example.moviesapplication.data.network.model.Movie

import androidx.compose.ui.Modifier


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


@Composable
fun MainTopAppBar() =
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
            IconButton(onClick = { }) {
                Icon(Icons.Filled.Menu, contentDescription = "")
            }
        },
        actions = {
            IconButton(onClick = {/* Do Something*/ }) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_star),
                    null, tint = Color.White
                )
            }
            IconButton(onClick = { }) {
                Icon(
                    Icons.Filled.Search,
                    null, tint = Color.White
                )
            }
            IconButton(onClick = {/* Do Something*/ }) {

                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_heart),
                    null,
                    tint = Color.Red
                )
            }

        },
        elevation = 10.dp
    )



@Composable
fun ScaffoldWithTopBar() {
    Scaffold(
        topBar = { MainTopAppBar() },
        content = {},
        floatingActionButton = {
            FloatingActionButton(onClick = { /*TODO*/ }) {
            }
        }
    )

}

@Composable
@Preview
fun DefaultPreview() {
    ScaffoldWithTopBar()
}