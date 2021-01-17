package app.assessmentdubizzle.com.di

import app.assessmentdubizzle.com.restAPI.ApiList
import com.google.gson.GsonBuilder
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


fun configureAppModuleForTest(baseApi: String)
        = module{
    single {
        Retrofit.Builder()
            .baseUrl(baseApi)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build()
    }
    factory{ get<Retrofit>().create(ApiList::class.java) }
}