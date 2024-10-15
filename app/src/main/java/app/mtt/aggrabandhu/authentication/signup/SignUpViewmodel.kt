package app.mtt.aggrabandhu.authentication.signup

import android.content.Context
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.mtt.aggrabandhu.repository.Repository
import app.mtt.aggrabandhu.utils.SharedPrefManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewmodel @Inject constructor(
    private val repository: Repository) : ViewModel() {

    val getOtp : StateFlow<String>
        get() = repository.sendOtp

    val verifyOtp : StateFlow<String>
        get() = repository.otpVerification

    val checkReference : StateFlow<Int>
        get() = repository.referenceCode

    fun getOtp(via : String, context: Context) {
        viewModelScope.launch {
            repository.sendOtp(via, context)
        }
    }

    var isOtpVerified by mutableStateOf(false)

    fun verifyOtp(via : String, otp : String, context: Context) {
        viewModelScope.launch {
            repository.verifyOtp(via, otp,  context)
        }
    }

    fun checkReferenceCode(referenceCode : String, context: Context) {
        viewModelScope.launch {
            repository.checkReferenceCode(referenceCode, context)
        }
    }

    var referenceIDSP by mutableStateOf("")
    var fullNameSP by mutableStateOf("")
    var phoneSP by mutableStateOf("")
    var passwordSP by mutableStateOf("")
    var otpTxt by mutableStateOf("")
    var confirmPassword by mutableStateOf("")

    var isNext by mutableStateOf(false)

    fun initSharedPrefs(sharedPrefManager: SharedPrefManager){
        if (sharedPrefManager.getReferenceID()?.isNotEmpty()!!) {
            referenceIDSP = sharedPrefManager.getReferenceID()!!
        }
        if (sharedPrefManager.getFullName()?.isNotEmpty()!!) {
            fullNameSP = sharedPrefManager.getFullName()!!
        }
        if (sharedPrefManager.getPhone()?.isNotEmpty()!!) {
            phoneSP = sharedPrefManager.getPhone()!!
        }
        if (sharedPrefManager.getPassword()?.isNotEmpty()!!) {
            passwordSP = sharedPrefManager.getPassword()!!
        }
    }
}