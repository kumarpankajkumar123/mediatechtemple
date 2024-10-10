package app.mtt.aggrabandhu.dashboard.pages.profile

import android.util.Log
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

    val editProfileResponseCode : StateFlow<Int>
        get() = repository.editProfileResponseCode

    var getName : String = savedStateHandle.get<String>("name")!!
    var phone : String = savedStateHandle.get<String>("phone")!!
//    var profileUri : String = savedStateHandle.get<String>("profileUri")!!
    var father : String= savedStateHandle.get<String>("father")!!
    var mother : String = savedStateHandle.get<String>("mother")!!
    var pinCode : String = savedStateHandle.get<String>("pinCode")!!
    var city : String = savedStateHandle.get<String>("city")!!
    var state : String = savedStateHandle.get<String>("state")!!
    var address : String = savedStateHandle.get<String>("address")!!
    var nominee : String= savedStateHandle.get<String>("nominee")!!
    var relation : String = savedStateHandle.get<String>("relation")!!
    var nominee2 : String= savedStateHandle.get<String>("nominee2")!!
    var relation2 : String = savedStateHandle.get<String>("relation2")!!
//    var gotra : String = savedStateHandle.get<String>("gotra")!!
//    var maritalStatus : String = savedStateHandle.get<String>("maritalStatus")!!
//    var dob : String = savedStateHandle.get<String>("dob")!!
//    var profession : String = savedStateHandle.get<String>("profession")!!
//    var spouseName : String = savedStateHandle.get<String>("spouse")!!

    // MutableLiveData to hold the current text state
    private var _fullNameState = MutableStateFlow("")
    val fullNameField: StateFlow<String>
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

    fun getFields(){
        if(_fullNameState.value.isEmpty()){
            _fullNameState.value = getName
            Log.d("Name", getName)
        }
    }

    fun editProfile() {
        viewModelScope.launch {
//            repository.editProfile(
//
//            )
        }
    }

}