package com.example.punkbeerexplorer.models

data class BeerResponseItem(
    val abv: Double,
    val attenuation_level: Double,
    val brewers_tips: String?,
    val contributed_by: String?,
    val description: String?,
    val ebc: Int,
    val first_brewed: String?,
    val food_pairing: ArrayList<String>?,
    val ibu: Double,
    val id: Int,
    val image_url: String?,
    val name: String?,
    val ph: Double,
    val srm: Double,
    val tagline: String?,
    val target_fg: Int,
    val target_og: Double
)