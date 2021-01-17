package app.assessmentdubizzle.com.modules.view.viewModels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import app.assessmentdubizzle.com.base.BaseUTTest
import app.assessmentdubizzle.com.di.configureTestAppComponent
import app.assessmentdubizzle.com.modules.models.responseModel.DynamoWriterResponseModel
import app.assessmentdubizzle.com.modules.repository.DubizzleRepository
import com.google.gson.Gson
import io.mockk.coEvery
import org.junit.*
import org.koin.core.context.startKoin
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class DynamoWriterVMTest : BaseUTTest() {

    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var mockDataRepository: DubizzleRepository

    lateinit var dynamoWriterViewModel: DynamoWriterVM


    @Before
    fun start() {
        super.setUp()
        MockitoAnnotations.initMocks(this)
        startKoin{ modules(configureTestAppComponent(getMockWebServerUrl()))}
    }

    @Test
    fun test_login_view_model_data_populates_expected_value(){

        dynamoWriterViewModel = DynamoWriterVM()

        val sampleResponse = getJsonFile("success_resp_list.json")
        val jsonObj = Gson().fromJson(sampleResponse, DynamoWriterResponseModel::class.java)

        dynamoWriterViewModel.callApi()
        dynamoWriterViewModel.dataReceived.observeForever {}


        assert(dynamoWriterViewModel.dataReceived.value != null)

        assert(dynamoWriterViewModel.dataReceived.value!!.results?.size!! > 0)


        val testResult = dynamoWriterViewModel.dataReceived.value as DynamoWriterResponseModel
        Assert.assertEquals(testResult.results?.get(0)?.name, "Notebook")
    }
}