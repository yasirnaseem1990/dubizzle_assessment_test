package app.assessmentdubizzle.com.dynamoWriterScreen

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import app.assessmentdubizzle.com.base.BaseUTTest
import app.assessmentdubizzle.com.di.configureTestAppComponent
import app.assessmentdubizzle.com.modules.models.responseModel.ResultsItem
import app.assessmentdubizzle.com.modules.view.fragments.DynamoWriterListFragment
import app.assessmentdubizzle.com.modules.view.viewModels.DynamoWriterVM
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.koin.core.context.startKoin
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import java.net.HttpURLConnection

@RunWith(JUnit4::class)
class DynamoFragmentTest : BaseUTTest(){

    @Mock
    lateinit var viewModel: DynamoWriterVM
    @Mock
    lateinit var dynamoWriterVM: Observer<List<ResultsItem>>
    private lateinit var dynamoListFragment: DynamoWriterListFragment

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()


    @Before
    fun start(){
        super.setUp()
        MockitoAnnotations.initMocks(this)
        //Start Koin with required dependencies
        startKoin{ modules(configureTestAppComponent(getMockWebServerUrl()))}
        dynamoListFragment = DynamoWriterListFragment()
        viewModel = DynamoWriterVM()
    }

    @Test
    fun test_dynamo_fragment_result_WhenFailureOccurred_PassFailureInfoToView()= runBlocking {

        mockNetworkResponseWithFileContent("success_resp_list.json", HttpURLConnection.HTTP_OK)
        viewModel.callApi()

        viewModel.getData().observeForever {
            Assert.assertNotNull(it)
            Assert.assertEquals(it.results?.size, 19)
            Assert.assertEquals(it.results?.get(0)?.name, "Notebook")
        }
    }

}