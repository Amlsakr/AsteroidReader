package com.example.asteroidreader.repository

import com.example.asteroidreader.api.RetrofitInstance
import com.example.asteroidreader.api.getDate
import com.example.asteroidreader.api.parseAsteroidsJsonResult
import com.example.asteroidreader.database.AsteroidDB
import com.example.asteroidreader.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

class Repository (private val db : AsteroidDB){


    suspend fun getAsteroids(
        startDate:String = getDate(),
        endDate:String = getDate(),
        ){
        withContext(Dispatchers.IO){
            val response = RetrofitInstance.api.getAsteroids(startDate , endDate ,Constants.API_KEY).await()
            val asteroidList = parseAsteroidsJsonResult(JSONObject(response.string()))
            for (item in asteroidList) {
                db.asteroidDao().insert(item)
            }
        }

    }

    suspend fun getPictureOfDay() = RetrofitInstance.api.getPictureOfDay(Constants.API_KEY)

}