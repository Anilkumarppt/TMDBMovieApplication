package com.example.moviesapplication.data.network

import com.example.moviesapplication.data.network.model.Movie
import com.example.moviesapplication.utils.APIExeception
import com.example.moviesapplication.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

@Suppress("UNCHECKED_CAST")
abstract class SafeApiRequest {

   suspend inline fun<T>  safeApiCall(crossinline safeCall:suspend ()->T): Resource<T> {
       return try {
            val result= withContext(Dispatchers.IO){
                safeCall()
            }
           Resource.Success(result)
        } catch (e: Exception) {
            Resource.Error(e.message)
        }

        //val response=safeCall.invoke()

       /* if(response.isSuccessful)
            return response as Resource<T>
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
        }*/
    }
}