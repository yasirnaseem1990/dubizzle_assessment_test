package app.assessmentdubizzle.com.di

fun generateTestAppComponent(baseApi: String)
        = listOf(
    configureAppModuleForInstrumentationTest(baseApi),
    MockWebServerInstrumentationTest,
    RepoDependency
)