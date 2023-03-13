package com.example.punkbeerexplorer.retrofit

import com.example.punkbeerexplorer.models.BeerResponseItem
import retrofit2.Call
import retrofit2.http.GET

interface BeersAPI {

    @GET("beers")
    fun getBeers(): Call<List<BeerResponseItem>>

}