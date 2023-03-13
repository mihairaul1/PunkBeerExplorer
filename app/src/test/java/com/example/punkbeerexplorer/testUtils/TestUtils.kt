package com.example.punkbeerexplorer.testUtils

import com.example.punkbeerexplorer.models.BeerResponseItem

class TestUtils {

    companion object {
        val beerResponseItem = BeerResponseItem(
            id = 1,
            abv = 4.2,
            attenuation_level = 75.0,
            brewers_tips = "Test brewers tips",
            contributed_by = "Test contributor",
            description = "Test beer description",
            ebc = 10,
            first_brewed = "01/2020",
            food_pairing = arrayListOf("Test food pairing 1"),
            ibu = 20.0,
            image_url = "https://test.beer.image",
            name = "Test beer name",
            ph = 4.5,
            srm = 15.0,
            tagline = "Test beer tagline",
            target_fg = 1010,
            target_og = 1040.0
        )
    }

}