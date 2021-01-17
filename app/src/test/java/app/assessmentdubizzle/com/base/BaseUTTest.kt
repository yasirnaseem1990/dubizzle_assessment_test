package app.assessmentdubizzle.com.base

import android.content.Context
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.koin.core.context.stopKoin
import org.koin.test.KoinTest
import java.io.File


abstract class BaseUTTest : KoinTest {

    //  instance of MockWebserver
    private lateinit var mMockServerInstance: MockWebServer

    private lateinit var context: Context


    private var mShouldStart = false

    @Before
    open fun setUp(){
        startMockServer(true)
    }

    //  Read input file return the data in mocked call
    fun mockNetworkResponseWithFileContent(fileName: String, responseCode: Int) = mMockServerInstance.enqueue(
        MockResponse()
            .setResponseCode(responseCode)
            .setBody(getJsonFile(fileName))
    )

    fun getJsonFile(path: String) : String {
        val uri = javaClass.classLoader!!.getResource(path)
        val file = File(uri.path)
        return String(file.readBytes())
    }

    //  start the mock server
    private fun startMockServer(shouldStart: Boolean){
        if (shouldStart){
            mShouldStart = shouldStart
            mMockServerInstance = MockWebServer()
            mMockServerInstance.start()
        }
    }


    fun getMockWebServerUrl() = mMockServerInstance.url("/").toString()

   // stop the mock server
    private fun stopMockServer() {
        if (mShouldStart){
            mMockServerInstance.shutdown()
        }
    }

    @After
    open fun tearDown(){
        //Stop Mock server
        stopMockServer()
        //Stop Koin as well
        stopKoin()
    }
}