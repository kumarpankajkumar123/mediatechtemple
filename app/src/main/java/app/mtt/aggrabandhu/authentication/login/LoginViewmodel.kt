package app.mtt.aggrabandhu.authentication.login

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.mtt.aggrabandhu.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewmodel @Inject constructor(
    private val repository: Repository) : ViewModel() {

     val loginResponse : StateFlow<LoginResponse>
         get() = repository.loginResponse

    private val _loginResponseCode = MutableStateFlow(0)  // Default to 0
    val loginResponseCode: StateFlow<Int> get() = _loginResponseCode

    var isLogin by mutableStateOf(false)

    // MutableLiveData to hold the current text state
    private val _mobileNumber = MutableStateFlow("")
    val mobileNumber: StateFlow<String> = _mobileNumber

    // MutableLiveData to hold the current text state
    private val _password = MutableStateFlow("")
    val password: StateFlow<String> = _password


    // Function to update the text state
    fun onPhoneTextChanged(newText: String) {
        _mobileNumber.value = newText
    }
    fun onPassTextChanged(newText: String) {
        _password.value = newText
    }

    fun login (context: Context) {
        viewModelScope.launch {
            val responseCode = repository.login(mobileNumber.value, password.value, context)
            _loginResponseCode.value = responseCode
        }
    }

}