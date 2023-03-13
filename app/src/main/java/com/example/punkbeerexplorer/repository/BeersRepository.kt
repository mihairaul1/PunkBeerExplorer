package com.example.punkbeerexplorer.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.example.punkbeerexplorer.database.BeersDatabase
import com.example.punkbeerexplorer.database.asDatabaseModel
import com.example.punkbeerexplorer.database.asDomainModel
import com.example.punkbeerexplorer.models.BeerResponseItem
import com.example.punkbeerexplorer.retrofit.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

const val TAG = "BeersRepository"

class BeersRepository(private val database: BeersDatabase) {

    var beers: MutableLiveData<List<BeerResponseItem>> = Transformations.map(database.beerDao().getBeers()) {
        it.asDomainModel()
    } as MutableLiveData<List<BeerResponseItem>>
    var isSuccessful: Boolean = false

    suspend fun refreshBeers(): Boolean {
        try {
            withContext(Dispatchers.IO) {
                val response = RetrofitInstance.getApi().getBeers().execute()
                Log.d(TAG, "refreshBeers: I've pulled the data again!")
                isSuccessful = if (response.isSuccessful && response.body() != null) {
                    response.body()?.let { database.beerDao().insertAll(it.asDatabaseModel()) }
                    true
                } else {
                    false
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            Log.e(TAG, e.message.toString())
        }
        return isSuccessful
    }

}