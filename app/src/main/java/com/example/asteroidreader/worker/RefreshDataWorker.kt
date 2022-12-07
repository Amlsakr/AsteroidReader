package com.example.asteroidreader.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.asteroidreader.api.getDate
import com.example.asteroidreader.database.AsteroidDB
import com.example.asteroidreader.repository.Repository
import retrofit2.HttpException

class RefreshDataWorker(context:Context , params:WorkerParameters) :
    CoroutineWorker(context ,params) {

    companion object{
        val WORKER_NAME = "RefreshData"
    }

    override suspend fun doWork(): Result {
       val db = AsteroidDB.getDatabase(applicationContext )
        val repo = Repository(db)
        return try {
            repo.getAsteroids(getDate(), getDate(7))
            Result.success()
        }catch (exception:HttpException){
            Result.retry()
        }
    }
}