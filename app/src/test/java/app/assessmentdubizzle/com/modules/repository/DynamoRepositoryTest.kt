package app.assessmentdubizzle.com.modules.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.assessmentdubizzle.com.base.BaseUTTest
import app.assessmentdubizzle.com.di.configureTestAppComponent
import app.assessmentdubizzle.com.restAPI.ApiList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.koin.core.context.startKoin
import org.koin.test.inject
import java.net.HttpURLConnection

@RunWith(JUnit4::class)
class DynamoRepositoryTest : BaseUTTest(){


    private lateinit var mRepo: DubizzleRepository
    val mAPIService : ApiList by inject()

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()


    @Before
    fun start(){
        super.setUp()

        startKoin{ modules(configureTestAppComponent(getMockWebServerUrl()))}
    }

    @Test
    fun test_dubizzle_repo_retrieves_expected_data() =  runBlocking {

        mockNetworkResponseWithFileContent("success_resp_list.json", HttpURLConnection.HTTP_OK)
        mRepo = DubizzleRepository(scope = CoroutineScope(coroutineContext))
        val dataReceived = mAPIService.getDynamoDbWriter()

        assertNotNull(dataReceived)
        Assert.assertEquals(dataReceived.body()?.results?.size, 19)
        Assert.assertEquals(dataReceived.body()?.results?.get(0)?.price, "AED 5")
    }
}