package app.mtt.aggrabandhu.viewmodel

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.mtt.aggrabandhu.authentication.onboarding.firstOnboarding.ProfessionData
import app.mtt.aggrabandhu.repository.Repository
import app.mtt.aggrabandhu.utils.SharedPrefManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
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
    val maritalStatusList = arrayListOf("Married", "Single", "Divorced", "Widowed")
    private val _selectedMaritalStatus = MutableStateFlow("")
    val selectedMaritalStatus: StateFlow<String> = _selectedMaritalStatus

    // MutableLiveData to hold the current text state
    private val _spouseName = MutableStateFlow("")
    val spouseNameText: StateFlow<String> = _spouseName

    /* ----------------------- ------------- -----------------------*/

    fun initSharedPrefs(context: Context) {
        val sharedPref = SharedPrefManager(context)
        if (_imageUri.value == null) {
            val uri = sharedPref.getProfileImageUri()!!
            Log.d("URI", "Retrieved URI : $uri")
            _imageUri.value = Uri.parse(uri)
        }
        if (_fatherNameState.value.isEmpty()) {
            _fatherNameState.value = sharedPref.getFatherName()!!
        }
        if (_motherNameState.value.isEmpty()) {
            _motherNameState.value = sharedPref.getMotherName()!!
        }
        if (_selectedGotra.value.isEmpty()) {
            _selectedGotra.value = sharedPref.getGotra()!!
        }
        if (_selectedMaritalStatus.value.isEmpty()) {
            _selectedMaritalStatus.value = sharedPref.getMarital()!!
        }
        if (_spouseName.value.isEmpty()) {
            _spouseName.value = sharedPref.getSpouseName()!!
        }
        if (_dobTextState.value.isEmpty()) {
            _dobTextState.value = sharedPref.getDOB()!!
        }
        if (_professionTextState.value.isEmpty()) {
            _professionTextState.value = sharedPref.getProfession()!!
        }
    }

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
    fun spouseNameChanged(newText: String) {
        _spouseName.value = newText
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