package app.assessmentdubizzle.com.di

import okhttp3.mockwebserver.MockWebServer
import org.koin.dsl.module


val MockWebServerInstrumentationTest = module {

    factory {
        MockWebServer()
    }

}