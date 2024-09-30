package app.mtt.aggrabandhu.dashboard.pages.profile

import androidx.compose.runtime.collectAsState
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.mtt.aggrabandhu.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val repository : Repository,
    private val savedStateHandle: SavedStateHandle) : ViewModel(){

//    val editProfileResponseCode : MutableStateFlow(0)

    var getName : String = savedStateHandle.get<String>("name")!!
    var phone : String = savedStateHandle.get<String>("phone")!!
    var profileUri : String = savedStateHandle.get<String>("profileUri")!!
    var father : String= savedStateHandle.get<String>("father")!!
    var mother : String = savedStateHandle.get<String>("mother")!!
    var gotra : String = savedStateHandle.get<String>("gotra")!!
    var maritalStatus : String = savedStateHandle.get<String>("maritalStatus")!!
    var dob : String = savedStateHandle.get<String>("dob")!!
    var profession : String = savedStateHandle.get<String>("profession")!!
    var spouseName : String = savedStateHandle.get<String>("spouse")!!

    // MutableLiveData to hold the current text state
    private var _fullNameState = MutableStateFlow("")
    val fullNameFieldState: StateFlow<String>
        get() = _fullNameState

    // MutableLiveData to hold the current text state
    private val _phoneTextState = MutableStateFlow("")
    val phoneTextState: StateFlow<String> = _phoneTextState

    // MutableLiveData to hold the current text state
    private val _fatherNameState = MutableStateFlow("")
    val fatherNameFieldState: StateFlow<String> = _fatherNameState

    // MutableLiveData to hold the current text state
    private val _motherNameState = MutableStateFlow("")
    val motherNameFieldState: StateFlow<String> = _motherNameState

    // MutableLiveData to hold the current text state
    private val _password = MutableStateFlow("")
    val passwordTextState: StateFlow<String> = _password

    fun getIntent() {
        if(_fullNameState.value.isEmpty()) {
            _fullNameState.value = savedStateHandle.get<String>("name")!!
        }
        if(_phoneTextState.value.isEmpty()) {
            _phoneTextState.value = savedStateHandle.get<String>("phone")!!
        }
        if(_phoneTextState.value.isEmpty()) {
            _phoneTextState.value = savedStateHandle.get<String>("phone")!!
        }
    }

    fun onNameTextChanged(newText: String) {
        _fullNameState.value = newText
    }
    fun onPhoneTextChanged(newText: String) {
        _phoneTextState.value = newText
    }
    fun onFatherNameTextChanged(newText: String) {
        _fatherNameState.value = newText
    }
    fun onMotherNameTextChanged(newText: String) {
        _motherNameState.value = newText
    }
    fun onPasswordTextChanged(newText: String) {
        _password.value = newText
    }

}