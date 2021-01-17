package app.assessmentdubizzle.com.restAPI


import app.assessmentdubizzle.com.modules.models.responseModel.DynamoWriterResponseModel
import retrofit2.Response
import retrofit2.http.GET


interface ApiList {

    @GET("default/dynamodb-writer")
    suspend fun getDynamoDbWriter(): Response<DynamoWriterResponseModel>

    /*@GET
    suspend fun getDynamoDbWriterImages(@Url urlPath: String?): Response<ArrayList<String>>*/

}