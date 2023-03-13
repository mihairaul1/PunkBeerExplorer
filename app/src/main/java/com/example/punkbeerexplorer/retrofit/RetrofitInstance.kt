package com.example.punkbeerexplorer.retrofit

import com.example.punkbeerexplorer.util.Constants.Companion.BASE_URL
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    private var api: BeersAPI? = null

    fun setMockApi(api: BeersAPI) {
        this.api = api
    }

    fun getApi(): BeersAPI {
        var instance = api
        return if (instance == null) {
            instance = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(BeersAPI::class.java)
            api = instance
            instance
        } else {
            instance
        }
    }

}