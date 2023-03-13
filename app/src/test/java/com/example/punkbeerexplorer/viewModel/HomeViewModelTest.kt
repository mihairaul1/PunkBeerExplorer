import android.app.Application
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.example.punkbeerexplorer.models.BeerResponseItem
import com.example.punkbeerexplorer.repository.BeersRepository
import com.example.punkbeerexplorer.testUtils.TestUtils
import com.example.punkbeerexplorer.viewModel.HomeViewModel
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.*
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class HomeViewModelTest {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var mockBeersRepository: BeersRepository

    @Mock
    private lateinit var application: Application

    private lateinit var homeViewModel: HomeViewModel


    @Before
    fun setUp() {
        Dispatchers.setMain(StandardTestDispatcher())
        MockitoAnnotations.openMocks(this)
        whenever(application.applicationContext).thenReturn(Application())

        val mockResponse = MutableLiveData<List<BeerResponseItem>>()
        mockResponse.value = listOf(TestUtils.beerResponseItem)
        whenever(mockBeersRepository.beers).thenReturn(mockResponse)

        homeViewModel = HomeViewModel.Factory(application).create(HomeViewModel::class.java).apply {
            this.beersRepository = mockBeersRepository
            this.beersLiveData = mockBeersRepository.beers
        }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `refreshDataFromRepository should return true when successful`() = runBlocking {
        whenever(mockBeersRepository.isSuccessful).thenReturn(true)
        val result = homeViewModel.refreshDataFromRepository(Application())
        assertTrue(result)
    }

    @Test
    fun `refreshDataFromRepository should return false when unsuccessful`() = runBlocking {
        whenever(mockBeersRepository.isSuccessful).thenReturn(false)
        val result = homeViewModel.refreshDataFromRepository(Application())
        assertFalse(result)
    }

    @Test
    fun `beersLiveData should receive list of beers from repository`() = runBlocking {
        val beers = listOf(TestUtils.beerResponseItem)
        homeViewModel.refreshDataFromRepository(Application())
        assertEquals(beers, homeViewModel.beersLiveData.value)
    }
}


