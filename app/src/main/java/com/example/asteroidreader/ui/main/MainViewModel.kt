package com.example.asteroidreader.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.asteroidreader.api.getDate
import com.example.asteroidreader.database.AsteroidDB
import com.example.asteroidreader.model.Asteroid
import com.example.asteroidreader.model.PictureOfDay
import com.example.asteroidreader.repository.Repository
import com.example.asteroidreader.utils.Constants
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MainViewModel (val db :AsteroidDB): ViewModel(){

    private val repo = Repository(db)

    private val _pictureOfDay = MutableLiveData<PictureOfDay>()
    val pictureOfDay: LiveData<PictureOfDay?>
        get() = _pictureOfDay



    private val _asteroidList = MutableLiveData<List<Asteroid>?>()
    val asteroidList: LiveData<List<Asteroid>?>
        get() = _asteroidList

    private val _navigateToAsteroidDetails = MutableLiveData<Asteroid>()
    val navigateToAsteroidDetails: LiveData<Asteroid>
        get() = _navigateToAsteroidDetails

    init {
        LoadDataFromInternet()
        getDataFromDB()

    }

    fun  LoadDataFromInternet (){
        viewModelScope.launch {
          repo.getAsteroids(getDate(), getDate(7))
            val response=     repo.getPictureOfDay()
            _pictureOfDay.postValue(response.body())
        }
    }

//    fun getPictureOfDay(){
//        viewModelScope.launch {
//       val response=     repo.getPictureOfDay()
//           _pictureOfDay.postValue(response.body())
//        }
//    }

    fun getDataFromDB(){
        viewModelScope.launch {
            db.asteroidDao().getAllAsteroids().collect{
                _asteroidList.postValue(it)
            }
        }
    }

    fun onAsteroidClicked(asteroid: Asteroid) {
        _navigateToAsteroidDetails.value = asteroid
    }

    fun onAsteroidDetailsNavigated() {
        _navigateToAsteroidDetails.value = null
    }


    fun getWeekAsteroids() {
        viewModelScope.launch {
            db.asteroidDao().getAllAsteroidsSortedByDate(getDate(), getDate(7))
                .collect {
                    _asteroidList.value = it
                }
        }
    }

    fun getTodayAsteroids() {
        viewModelScope.launch {
            db.asteroidDao().getAllAsteroidsSortedByDate(getDate(), getDate()).collect {
                _asteroidList.value = it
            }
        }
    }

    fun getSavedAsteroids() {
        viewModelScope.launch {
            db.asteroidDao().getAllAsteroids().collect {
                _asteroidList.value = it
            }
        }
    }
}