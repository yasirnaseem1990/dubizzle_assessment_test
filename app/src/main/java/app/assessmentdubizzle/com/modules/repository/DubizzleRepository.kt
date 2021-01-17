package app.assessmentdubizzle.com.modules.repository


import app.assessmentdubizzle.com.modules.models.responseModel.DynamoWriterResponseModel
import app.assessmentdubizzle.com.restAPI.ApiList
import app.assessmentdubizzle.com.restAPI.request
import com.sportvalue.uk.restAPI.Result
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.koin.core.KoinComponent
import org.koin.core.inject


class DubizzleRepository(private val scope: CoroutineScope) : KoinComponent {

    private val api by inject<ApiList>()

    @ExperimentalCoroutinesApi
    fun getDynamoDbWriter(response: (DynamoWriterResponseModel?, String?, Int?) -> Unit) {
        request(scope, { api.getDynamoDbWriter() }, { res ->
            when (res) {
                is Result.Success -> response(res.data, null, null)
                is Result.Failure -> response(
                    null,
                    res.error,
                    res.statusCode
                )
            }
        })
    }


}