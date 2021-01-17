package app.assessmentdubizzle.com.di.appModule

import app.assessmentdubizzle.com.BuildConfig
import app.assessmentdubizzle.com.restAPI.ApiList
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


val appModules = module {

    single {
        getClient()
    }
}

        private val retrofitWithOutHeaders: ApiList by lazy {
            val okHttpClientBuild = OkHttpClient().newBuilder()
            okHttpClientBuild.connectTimeout(60, TimeUnit.SECONDS).readTimeout(60, TimeUnit.SECONDS).writeTimeout(
                60,
                TimeUnit.SECONDS
            ).addInterceptor(
                httpLoggingInterceptor()
            )
            okHttpClientBuild.addInterceptor(getHeaderInterceptor())
            val okHttpClient = okHttpClientBuild.build()

            Retrofit.Builder().baseUrl(BuildConfig.API_BASE_URL).client(okHttpClient).addConverterFactory(
                GsonConverterFactory.create()
            ).build().create(ApiList::class.java)
        }

        private fun getHeaderInterceptor(): Interceptor {
            return Interceptor { chain ->
                val builder = chain.request().newBuilder()
                builder.addHeader("Accept", "application/json")
                chain.proceed(builder.build())

            }
        }

        private fun httpLoggingInterceptor(): HttpLoggingInterceptor {
            val logging = HttpLoggingInterceptor()
            logging.level = HttpLoggingInterceptor.Level.BASIC
            return logging
        }

        fun getClient(): ApiList {
            return retrofitWithOutHeaders
        }

