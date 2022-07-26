package com.example.moviesapplication.data.network

import com.example.moviesapplication.utils.APIExeception
import retrofit2.Response

abstract class SafeApiRequest {

    suspend fun<T:Any> safeApiCall(safeCall:suspend ()->Response<T>):T{
        val response=safeCall.invoke()
        if(response.isSuccessful)
            return response.body()!!
        else{
            val message=StringBuilder()
            lateinit var  errorObject:ErrorObject
            try {
                 errorObject = response.errorBody() as ErrorObject

                errorObject.let { error ->
                    message.append(error.toString())
                }
            } catch (e: Exception) {
                message.append(e.message)
            }
            throw APIExeception(errorObject = message.toString())
        }
    }
}