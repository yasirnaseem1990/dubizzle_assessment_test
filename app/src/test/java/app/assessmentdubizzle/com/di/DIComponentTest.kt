package app.assessmentdubizzle.com.di

/**
 * Main Koin DI component.
 * 1) Mockwebserver
 * 2) appModule
 * 3) mainModule
 */
fun configureTestAppComponent(baseApi: String)
        = listOf(
    MockWebServerDIPTest,
    configureAppModuleForTest(baseApi),
    RepoDIComponentTest
    )

