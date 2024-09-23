package app.mtt.aggrabandhu.viewmodel

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.mtt.aggrabandhu.authentication.onboarding.ProfessionData
import app.mtt.aggrabandhu.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class Onboarding1Viewmodel @Inject constructor(
    private val repository: Repository,
    private val savedStateHandle: SavedStateHandle) : ViewModel() {

    val profession : StateFlow<List<ProfessionData>>
        get() = repository.professions

    val gotra : StateFlow<List<ProfessionData>>
        get() = repository.gotra

    init {
        viewModelScope.launch {
            repository.getProfession()
            repository.getGotra()
        }
    }

    val referenceID : String
        get() = savedStateHandle.get<String>("referenceID")!!
    val getName : String
        get() = savedStateHandle.get<String>("name")!!
    val phone : String
        get() = savedStateHandle.get<String>("phone")!!
    val password : String
        get() = savedStateHandle.get<String>("password")!!

    // MutableLiveData to hold the current text state
    private val _fatherNameState = MutableStateFlow("")
    val fatherNameFieldState: StateFlow<String> = _fatherNameState

    // MutableLiveData to hold the current text state
    private val _motherNameState = MutableStateFlow("")
    val motherNameFieldState: StateFlow<String> = _motherNameState

    private val _selectedGotra = MutableStateFlow("")
    val selectedGotra: StateFlow<String> = _selectedGotra

    // MutableLiveData to hold the current text state
    private val _dobTextState = MutableStateFlow("")
    val dobTextFieldState: StateFlow<String> = _dobTextState

    // MutableLiveData to hold the current text state
    private val _professionTextState = MutableStateFlow("")
    val professionState: StateFlow<String> = _professionTextState

    // MutableLiveData to hold the current text state
    private val _imageUri = MutableStateFlow<Uri?>(null)
    val imageUri: StateFlow<Uri?> = _imageUri

    /* --------------------- Get MarriageStatus Data -------------------*/
    val maritalStatusList = arrayListOf("Married","Unmarried")
    private val _selectedMaritalStatus = MutableStateFlow("")
    val selectedMaritalStatus: StateFlow<String> = _selectedMaritalStatus
    /* ----------------------- ------------- -----------------------*/


    // Function to update the text state
    fun onImageUriChanged(newUri : Uri) {
        _imageUri.value = newUri
    }

    // Function to update the text state
    fun onFatherNameTextChanged(newText: String) {
        _fatherNameState.value = newText
    }
    // Function to update the text state
    fun onMotherNameTextChanged(newText: String) {
        _motherNameState.value = newText
    }
    // Function to update the text state
    fun onSelectedGotraTextChanged(newText: String) {
        _selectedGotra.value = newText
    }
    // Function to update the text state
    fun selectedMaritalStatusChanged(newText: String) {
        _selectedMaritalStatus.value = newText
    }
    // Function to update the text state
    fun onDobTextChanged(newText: String) {
        _dobTextState.value = newText
    }
    // Function to update the text state
    fun onProfessionChanged(newText: String) {
        _professionTextState.value = newText
    }

}