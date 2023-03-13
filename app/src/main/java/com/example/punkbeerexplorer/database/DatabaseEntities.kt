package com.example.punkbeerexplorer.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.punkbeerexplorer.models.BeerResponseItem
import java.util.ArrayList

/**
 * Database entities go in this file. These are responsible for reading and writing from the
 * database.
 */


/**
 * DatabaseBeer represents a beer entity in the database.
 */
@Entity(tableName = "databaseBeer")
data class DatabaseBeer constructor(
    @PrimaryKey
    val id: Int,
    val abv: Double,
    val attenuation_level: Double,
    val brewers_tips: String?,
    val contributed_by: String?,
    val description: String?,
    val ebc: Int,
    val first_brewed: String?,
    val food_pairing: ArrayList<String>?,
    val ibu: Double,
    val image_url: String?,
    val name: String?,
    val ph: Double,
    val srm: Double,
    val tagline: String?,
    val target_fg: Int,
    val target_og: Double,
)

/**
 * Map DatabaseBeers to domain entities
 */
fun List<DatabaseBeer>.asDomainModel(): List<BeerResponseItem> {
    return map {
        BeerResponseItem(
            abv = it.abv,
            attenuation_level = it.attenuation_level,
            brewers_tips = it.brewers_tips,
            contributed_by = it.contributed_by,
            description = it.description,
            ebc = it.ebc,
            first_brewed = it.first_brewed,
            food_pairing = it.food_pairing,
            ibu = it.ibu,
            id = it.id,
            image_url = it.image_url,
            name = it.name,
            ph = it.ph,
            srm = it.srm,
            tagline = it.tagline,
            target_fg = it.target_fg,
            target_og = it.target_og
        )
    }
}

/**
 * Map domain entities to DatabaseBeers
 */
fun List<BeerResponseItem>.asDatabaseModel(): List<DatabaseBeer> {
    return map {
        DatabaseBeer(
            abv = it.abv,
            attenuation_level = it.attenuation_level,
            brewers_tips = it.brewers_tips,
            contributed_by = it.contributed_by,
            description = it.description,
            ebc = it.ebc,
            first_brewed = it.first_brewed,
            food_pairing = it.food_pairing,
            ibu = it.ibu,
            id = it.id,
            image_url = it.image_url,
            name = it.name,
            ph = it.ph,
            srm = it.srm,
            tagline = it.tagline,
            target_fg = it.target_fg,
            target_og = it.target_og
        )
    }
}
