package app.assessmentdubizzle.com.network

import app.assessmentdubizzle.com.BuildConfig
import io.restassured.RestAssured.given
import io.restassured.builder.RequestSpecBuilder
import io.restassured.filter.log.RequestLoggingFilter
import io.restassured.filter.log.ResponseLoggingFilter
import io.restassured.http.ContentType
import io.restassured.specification.RequestSpecification
import org.junit.After
import org.junit.Test

class ServiceTest {

    private var spec: RequestSpecification = RequestSpecBuilder().
        setContentType(ContentType.JSON).
        setBaseUri(BuildConfig.API_BASE_URL).
        addFilter(ResponseLoggingFilter()).
        addFilter(RequestLoggingFilter()).build()


    /**
     * Unit Test Case for Checking the API Response
     */
    @Test
    fun dynamoListApiSuccess(){
        given()
            .spec(spec)
            .`when`()
            .get("default/dynamodb-writer")
            .then()
            .statusCode(200)
    }

    @Test
    fun dynamoListApiFail(){
        given()
            .spec(spec)
            .`when`()
            .get("default/dynamodb-writer")
            .then()
            .statusCode(400)
    }
}