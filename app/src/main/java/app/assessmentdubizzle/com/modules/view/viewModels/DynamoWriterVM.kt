package app.assessmentdubizzle.com.modules.view.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.assessmentdubizzle.com.modules.models.responseModel.DynamoWriterResponseModel
import app.assessmentdubizzle.com.modules.repository.DubizzleRepository
import org.koin.core.KoinComponent
import org.koin.core.inject
import org.koin.core.parameter.parametersOf

class DynamoWriterVM : ViewModel() , KoinComponent {

    private val showLoading = MutableLiveData<Boolean>()
    val dataReceived = MutableLiveData<DynamoWriterResponseModel>()
    private val errorOccurred = MutableLiveData<String>()

        private val dynamoWriterRepository by inject<DubizzleRepository> { parametersOf(viewModelScope) }

    init {
        callApi()
    }

    fun callApi() {
        showLoading.value = true
        dynamoWriterRepository.getDynamoDbWriter { dynamoWriterResponseModel, error, _ ->
            showLoading.value = false
            dynamoWriterResponseModel?.let { dataReceived.value = it }
            error?.let { errorOccurred.value = it }
        }
    }

    /**
     * Call this method inside the onViewCreated() when got Success
     */
    fun getData(): MutableLiveData<DynamoWriterResponseModel> {
        return dataReceived
    }

    /**
     * Call this method inside the onViewCreated() when got error
     */
    fun getErrorResult(): LiveData<String> = errorOccurred

    /**
     * Call this method inside the onViewCreated() when handle loading status
     */
    fun getShowLoadingResult(): LiveData<Boolean> = showLoading

}