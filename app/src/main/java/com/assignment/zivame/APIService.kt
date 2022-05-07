package com.assignment.zivame

import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET

// API: https://my-json-server.typicode.com/nancymadan/assignment/db
interface APIService {

    @GET("/nancymadan/assignment/db")
    suspend fun getProducts(): Response<ResponseBody>

}