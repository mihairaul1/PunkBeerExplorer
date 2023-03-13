package com.example.punkbeerexplorer.viewModel

import android.app.Application
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.*
import com.example.punkbeerexplorer.database.BeersDatabase.Companion.getDatabase
import com.example.punkbeerexplorer.models.BeerResponseItem
import com.example.punkbeerexplorer.repository.BeersRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException

const val TAG = "HomeViewModel"

class HomeViewModel(application: Application) : AndroidViewModel(application) {

    var beersRepository = BeersRepository(getDatabase(application))

    var beersLiveData: LiveData<List<BeerResponseItem>> = Transformations.map(beersRepository.beers) {
        it
    }

    init {
        refreshDataFromRepository(this.getApplication())
    }

    internal fun refreshDataFromRepository(context: Context): Boolean {
        viewModelScope.launch {
            try {
                withContext(Dispatchers.IO) {
                    beersRepository.refreshBeers()
                }
            } catch (networkError: IOException) {
                Log.e(TAG, "refreshDataFromRepository: $networkError")
            } catch (dbError: Exception) {
                Log.e(TAG, "refreshDataFromRepository: $dbError")
            }
            if (!beersRepository.isSuccessful) {
                Toast.makeText(context, "There has been an issue trying to pull the data from the API", Toast.LENGTH_SHORT).show()
            }
        }
        return beersRepository.isSuccessful
    }

    /**
     * Factory for constructing HomeViewModel with parameter
     */
    class Factory(private val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST") return HomeViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }

}
