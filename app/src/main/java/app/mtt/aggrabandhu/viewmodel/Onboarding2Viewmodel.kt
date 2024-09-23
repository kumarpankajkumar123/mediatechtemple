package app.mtt.aggrabandhu.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import app.mtt.aggrabandhu.authentication.onboarding.DocValidationResponse
import app.mtt.aggrabandhu.authentication.onboarding.ProfessionData
import app.mtt.aggrabandhu.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import javax.inject.Inject

@HiltViewModel
class Onboarding2Viewmodel @Inject constructor(
    private val repository: Repository,
    private val savedStateHandle: SavedStateHandle) : ViewModel() {

    val validateID : StateFlow<DocValidationResponse>
        get() = repository.validateID

    val referenceID : String
        get() = savedStateHandle.get<String>("referenceID")!!
    val getName : String
        get() = savedStateHandle.get<String>("name")!!
    val phone : String
        get() = savedStateHandle.get<String>("phone")!!
    val password : String
        get() = savedStateHandle.get<String>("password")!!
    val profileUri : String
        get() = savedStateHandle.get<String>("profileUri")!!
    val father : String
        get() = savedStateHandle.get<String>("father")!!
    val mother : String
        get() = savedStateHandle.get<String>("mother")!!
    val gotra : String
        get() = savedStateHandle.get<String>("gotra")!!
    val maritalStatus : String
        get() = savedStateHandle.get<String>("maritalStatus")!!
    val dob : String
        get() = savedStateHandle.get<String>("dob")!!
    val profession : String
        get() = savedStateHandle.get<String>("profession")!!

    fun validateDoc(
        idNumber : String,
        idType : String,
        multiPartBody : MultipartBody.Part
    ){
        viewModelScope.launch {
            repository.validateDocument(idNumber,idType,multiPartBody)
        }
    }

}