package app.assessmentdubizzle.com.restAPI

import android.util.Log
import app.assessmentdubizzle.com.AppController
import app.assessmentdubizzle.com.utils.NetworkUtils
import com.sportvalue.uk.restAPI.Result
import kotlinx.coroutines.*
import retrofit2.Response


fun <T> request(response: suspend() -> Response<T>,
                results: (Result<T>) -> Unit) {

    GlobalScope.launch(Dispatchers.Main) {
        handlingResponse(response, { results(it) })
    }
}

fun <T> request(coroutineScope: CoroutineScope, response: suspend() -> Response<T>, results:(Result<T>) -> Unit) {
    if (NetworkUtils(AppController.ApplicationContext).isConnected()){
        coroutineScope.launch {
            handlingResponse(response, { results(it) })
        }
    }else{
        results(Result.Failure("No Internet Connection", null))
    }

}

fun <T> requestBlock(response: suspend() -> Response<T>, results: (Result<T>) -> Unit) {
    runBlocking {
        handlingResponse(response, { results(it) })
    }
}

suspend fun <T> handlingResponse(response: suspend() -> Response<T>, results:(Result<T>) -> Unit) {
    try {
        val result = response()
        when {
            result.isSuccessful -> result.body()?.let { body ->
                results(Result.Success(body))
            }
            else -> {
                Log.d("Result", result.toString())
                results(Result.Failure(result.toString(), result.code()))
            }
        }
    } catch (throwable: Throwable) {
        results(Result.Failure(throwable.toString(), null))
    }
}