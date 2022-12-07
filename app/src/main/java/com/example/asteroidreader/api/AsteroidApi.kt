package com.example.asteroidreader.api

import com.example.asteroidreader.model.PictureOfDay
import com.example.asteroidreader.utils.Constants
import kotlinx.coroutines.Deferred

import okhttp3.ResponseBody
import retrofit2.Response

import retrofit2.http.GET
import retrofit2.http.Query

interface AsteroidApi {

    @GET("neo/rest/v1/feed")
    fun getAsteroids(
        @Query("start_date") startDate: String,
        @Query("end_date") endDate: String,
        @Query("api_key") apiKey: String
    ): Deferred<ResponseBody>

    @GET("planetary/apod")
   suspend fun getPictureOfDay(
        @Query("api_key") apiKey: String = Constants.API_KEY
    ): Response<PictureOfDay>
}