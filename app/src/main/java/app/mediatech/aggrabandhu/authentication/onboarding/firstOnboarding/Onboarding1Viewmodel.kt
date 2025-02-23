package app.mediatech.aggrabandhu.authentication.onboarding.firstOnboarding

import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.mediatech.aggrabandhu.repository.Repository
import app.mediatech.aggrabandhu.utils.SharedPrefManager
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


    var fatherNameSP by mutableStateOf("")
    var motherNameSP by mutableStateOf("")

    private val _selectedGotra = MutableStateFlow("")
    val selectedGotra: StateFlow<String> = _selectedGotra

    // MutableLiveData to hold the current text state
    private val _dobTextState = MutableStateFlow("")
    val dobTextFieldState: StateFlow<String> = _dobTextState

    var ageYears = 0

    // MutableLiveData to hold the current text state
    private val _marriageDateTextState = MutableStateFlow("")
    val marriageDateTextFieldState: StateFlow<String> = _marriageDateTextState
    var marriageYears = 0

    // MutableLiveData to hold the current text state
    private val _professionTextState = MutableStateFlow("")
    val professionState: StateFlow<String> = _professionTextState

    // MutableLiveData to hold the current text state
    private val _imageUri = MutableStateFlow<Uri?>(null)
    val imageUri: StateFlow<Uri?> = _imageUri

    /* --------------------- Get Gender Data -------------------*/
    val genderList = arrayListOf("Male", "Female")
    private val _selectedGender = MutableStateFlow("")
    val selectedGender: StateFlow<String> = _selectedGender

    /* --------------------- Get MarriageStatus Data -------------------*/
    val maritalStatusList = arrayListOf("Married", "Single", "Divorced", "Widow", "Widower")
    private val _selectedMaritalStatus = MutableStateFlow("")
    val selectedMaritalStatus: StateFlow<String> = _selectedMaritalStatus

    // MutableLiveData to hold the current text state
    private val _spouseName = MutableStateFlow("")
    val spouseNameText: StateFlow<String> = _spouseName

    /* ----------------------- ------------- -----------------------*/

    fun initSharedPrefs(sharedPref: SharedPrefManager) {
//        if (sharedPref.getProfileImageUri()?.isNotEmpty()!!) {
//            val uri = sharedPref.getProfileImageUri()!!
//            Log.d("URI", "Retrieved URI : $uri")
//            _imageUri.value = Uri.parse(uri)
//        } else {
//            Log.d("URI", "No URI found in shared preferences")
//        }
        if (sharedPref.getFatherName()!!.isNotEmpty()) {
            fatherNameSP = sharedPref.getFatherName()!!
        }
        if (sharedPref.getMotherName()!!.isNotEmpty()) {
            motherNameSP = sharedPref.getMotherName()!!
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
            if (_dobTextState.value.isNotEmpty()) {
                ageYears = calculateAge(_dobTextState.value)
            }
        }
        if (_marriageDateTextState.value.isEmpty()) {
            _marriageDateTextState.value = sharedPref.getMarriageDate()!!
            if (_marriageDateTextState.value.isNotEmpty() && _marriageDateTextState.value != "No") {
                marriageYears = calculateAge(_marriageDateTextState.value)
            }
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
    fun onSelectedGotraTextChanged(newText: String) {
        _selectedGotra.value = newText
    }
    // Function to update the text state
    fun selectedMaritalStatusChanged(newText: String) {
        _selectedMaritalStatus.value = newText
    }
    // Function to update the text state
    fun selectedGenderChanged(newText: String) {
        _selectedGender.value = newText
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
    fun onMarriageDateTextChanged(newText: String) {
        _marriageDateTextState.value = newText
    }
    // Function to update the text state
    fun onProfessionChanged(newText: String) {
        _professionTextState.value = newText
    }

}