package app.mtt.aggrabandhu.authentication.signup

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.mtt.aggrabandhu.repository.Repository
import app.mtt.aggrabandhu.utils.SharedPrefManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewmodel @Inject constructor(
    private val repository: Repository) : ViewModel() {

    // MutableLiveData to hold the current text state
    private val _referenceIdState = MutableStateFlow("")
    val referenceIdFieldState: StateFlow<String> = _referenceIdState

    // MutableLiveData to hold the current text state
    private val _fullNameState = MutableStateFlow("")
    val fullNameFieldState: StateFlow<String> = _fullNameState

    // MutableLiveData to hold the current text state
    private val _phoneTextState = MutableStateFlow("")
    val phoneTextState: StateFlow<String> = _phoneTextState

    // MutableLiveData to hold the current text state
    private val _password = MutableStateFlow("")
    val passwordTextState: StateFlow<String> = _password

    fun initSharedPrefs(context: Context){
        val sharedPrefManager = SharedPrefManager(context)
        if (_referenceIdState.value.isEmpty()) {
            _referenceIdState.value = sharedPrefManager.getReferenceID()!!
        }
        if (_fullNameState.value.isEmpty()) {
            _fullNameState.value = sharedPrefManager.getFullName()!!
        }
        if (_phoneTextState.value.isEmpty()) {
            _phoneTextState.value = sharedPrefManager.getPhone()!!
        }
        if (_password.value.isEmpty()) {
            _password.value = sharedPrefManager.getPassword()!!
        }
    }

    // Function to update the text state
    fun onReferenceTextChanged(newText: String) {
        _referenceIdState.value = newText
    }
    fun onNameTextChanged(newText: String) {
        _fullNameState.value = newText
    }
    fun onPhoneTextChanged(newText: String) {
        _phoneTextState.value = newText
    }
    fun onPasswordTextChanged(newText: String) {
        _password.value = newText
    }

}