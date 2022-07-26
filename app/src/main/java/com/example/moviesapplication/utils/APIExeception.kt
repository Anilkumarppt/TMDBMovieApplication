package com.example.moviesapplication.utils

import com.example.moviesapplication.data.network.ErrorObject
import okio.IOException

class APIExeception(errorObject: String):IOException(errorObject) {
}