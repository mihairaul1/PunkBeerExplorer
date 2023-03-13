package com.example.punkbeerexplorer.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.punkbeerexplorer.testUtils.TestUtils
import kotlinx.coroutines.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BeersDaoTest {

    private lateinit var database: BeersDatabase
    private lateinit var beerDao: BeerDao

    @Before
    fun setUp() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(
            context, BeersDatabase::class.java
        ).build()

        beerDao = database.beerDao()
    }

    @After
    fun tearDown() {
        database.close()
    }

    @Test
    fun insertingBeersShouldAddThemToTheDatabase() = runBlocking {
        val beersToInsert = listOf(
            TestUtils.createBeerWithIdAndName(1, "Test1"),
            TestUtils.createBeerWithIdAndName(2, "Test2"),
        ).asDatabaseModel()
        beerDao.insertAll(beersToInsert)

        val beersFromDb = beerDao.getBeersForTesting()
        assertEquals(beersToInsert.size, beersFromDb.size)
        assertEquals(beersToInsert[0].id, beersFromDb[0].id)
        assertEquals(beersToInsert[0].name, beersFromDb[0].name)
        assertEquals(beersToInsert[1].id, beersFromDb[1].id)
        assertEquals(beersToInsert[1].name, beersFromDb[1].name)
    }

    @Test
    fun gettingBeerByIdShouldReturnCorrectBeer() = runBlocking {
        val beersToInsert = listOf(
            TestUtils.createBeerWithIdAndName(1, "Test1"),
            TestUtils.createBeerWithIdAndName(2, "Test2")
        ).asDatabaseModel()
        beerDao.insertAll(beersToInsert)

        val beerFromDb1 = beerDao.getBeerById(1)
        assertEquals(beersToInsert[0].id, beerFromDb1.id)
        assertEquals(beersToInsert[0].name, beerFromDb1.name)

        val beerFromDb2 = beerDao.getBeerById(2)
        assertEquals(beersToInsert[1].id, beerFromDb2.id)
        assertEquals(beersToInsert[1].name, beerFromDb2.name)
    }
}