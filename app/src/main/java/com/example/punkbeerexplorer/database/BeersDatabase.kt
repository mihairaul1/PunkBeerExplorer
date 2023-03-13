package com.example.punkbeerexplorer.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.punkbeerexplorer.converters.Converters

@Dao
interface BeerDao {
    @Query("SELECT * FROM databaseBeer")
    fun getBeers(): LiveData<List<DatabaseBeer>>

    @Query("SELECT * FROM databaseBeer")
    fun getBeersForTesting(): List<DatabaseBeer>

    @Query("SELECT * FROM databaseBeer WHERE id=:id")
    fun getBeerById(id: Int): DatabaseBeer

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(beers: List<DatabaseBeer>)
}

@Database(entities = [DatabaseBeer::class], version = 3)
@TypeConverters(Converters::class)
abstract class BeersDatabase : RoomDatabase() {
    abstract fun beerDao(): BeerDao

    companion object {
        @Volatile
        private var INSTANCE: BeersDatabase? = null

        fun getDatabase(context: Context): BeersDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    BeersDatabase::class.java,
                    "beers_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}