package com.example.punkbeerexplorer.repository

import android.content.Context
import android.os.Build
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.punkbeerexplorer.retrofit.BeersAPI
import com.example.punkbeerexplorer.database.BeerDao
import com.example.punkbeerexplorer.database.BeersDatabase
import com.example.punkbeerexplorer.database.asDatabaseModel
import com.example.punkbeerexplorer.getOrAwaitValue
import com.example.punkbeerexplorer.models.BeerResponseItem
import com.example.punkbeerexplorer.retrofit.RetrofitInstance
import com.example.punkbeerexplorer.testUtils.TestUtils
import com.nhaarman.mockitokotlin2.*
import kotlinx.coroutines.*
import kotlinx.coroutines.test.*
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import retrofit2.Call
import retrofit2.Response

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [Build.VERSION_CODES.R])
class BeersRepositoryTest {

    @Mock
    private lateinit var mockApi: BeersAPI

    @Mock
    private lateinit var mockCall: Call<List<BeerResponseItem>>

    private lateinit var repository: BeersRepository
    private lateinit var database: BeersDatabase
    private lateinit var beerDao: BeerDao
    private lateinit var response: Response<List<BeerResponseItem>>

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(
            context, BeersDatabase::class.java
        ).build()

        beerDao = database.beerDao()
        repository = BeersRepository(database)

        mockApi = mock()
        RetrofitInstance.setMockApi(mockApi)
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun `refreshBeers should insert beers into the database`() = runBlocking {
        // Given
        response = Response.success(listOf(TestUtils.beerResponseItem))
        mockCall = mock {
            on { execute() } doReturn response
        }
        whenever(mockApi.getBeers()).thenReturn(mockCall)

        // When
        repository.refreshBeers()

        // Then
        val job = async(Dispatchers.IO) {
            assertEquals(
                listOf(TestUtils.beerResponseItem).asDatabaseModel(),
                database.beerDao().getBeers().getOrAwaitValue()
            )
        }
        job.cancelAndJoin()
    }

    @Test
    fun `refreshBeers fails to insert beers into the database`() = runBlocking {
        // Given
        // Create an error response body with the specified error message and HTTP status code
        val errorMessage = "Invalid request"
        val errorResponseBody = errorMessage.toResponseBody("text/plain".toMediaType())

        // Create a Retrofit response with the error response body and the appropriate HTTP status code
        val errorCode = 400
        response = Response.error(errorCode, errorResponseBody)
        mockCall = mock {
            on { execute() } doReturn response
        }
        whenever(mockApi.getBeers()).thenReturn(mockCall)

        //When
        repository.refreshBeers()

        //Then
        val job = async(Dispatchers.IO) {
            assertNotEquals(
                listOf(TestUtils.beerResponseItem).asDatabaseModel(),
                database.beerDao().getBeers().getOrAwaitValue()
            )
            assertTrue(database.beerDao().getBeers().getOrAwaitValue().isEmpty())
        }
        job.cancelAndJoin()
    }
}
