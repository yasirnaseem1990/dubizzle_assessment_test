package app.assessmentdubizzle.com

import android.content.Context
import androidx.multidex.MultiDexApplication
import app.assessmentdubizzle.com.di.appModule.appModules
import app.assessmentdubizzle.com.di.appModule.mainModule
import app.assessmentdubizzle.com.restAPI.ResponseHeaderInterceptor
import coil.ImageLoader
import coil.ImageLoaderFactory
import coil.request.CachePolicy
import okhttp3.Cache
import okhttp3.Dispatcher
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import java.io.File

class AppController : MultiDexApplication(), ImageLoaderFactory {

    companion object {
        lateinit var ApplicationContext: Context
    }

    override fun onCreate() {
        super.onCreate()

        //initialize on app level here
        ApplicationContext = this

        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidContext(ApplicationContext)
            modules(listOf(appModules, mainModule))
        }
    }

    override fun newImageLoader(): ImageLoader {
        return ImageLoader.Builder(this)
            .availableMemoryPercentage(0.25)
            .crossfade(true)
            .diskCachePolicy(CachePolicy.ENABLED)
            .allowHardware(false)
            .error(R.drawable.ic_error_white_24dp)
            .placeholder(R.drawable.ic_error_white_24dp)
            .memoryCachePolicy(CachePolicy.ENABLED)
            .okHttpClient {
                val cacheDirectory = File(filesDir, "image_cache").apply { mkdirs() }
                val cache = Cache(cacheDirectory, Long.MAX_VALUE)
                val cacheControlInterceptor =
                    ResponseHeaderInterceptor("Cache-Control", "max-age=31536000,public")
                val dispatcher = Dispatcher().apply { maxRequestsPerHost = maxRequests }
                OkHttpClient.Builder()
                    .cache(cache)
                    .dispatcher(dispatcher)
                    .addNetworkInterceptor(cacheControlInterceptor)
                    .build()
            }
            .build()
    }
}