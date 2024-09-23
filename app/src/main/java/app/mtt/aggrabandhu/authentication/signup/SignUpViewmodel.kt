package app.mtt.aggrabandhu.authentication.signup

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