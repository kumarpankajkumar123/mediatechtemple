package app.mtt.aggrabandhu.viewmodel

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.mtt.aggrabandhu.authentication.onboarding.secondOnboarding.PostalData
import app.mtt.aggrabandhu.authentication.onboarding.secondOnboarding.SignupResponse
import app.mtt.aggrabandhu.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import javax.inject.Inject

@HiltViewModel
class Onboarding2Viewmodel @Inject constructor(
    private val repository: Repository,
    private val savedStateHandle: SavedStateHandle) : ViewModel() {

    val validateID : StateFlow<Int>
        get() = repository.validateID
    val validateOtherID : StateFlow<Int>
        get() = repository.validateOtherID

    val rules : StateFlow<String>
        get() = repository.rules
    val declaration : StateFlow<String>
        get() = repository.declaration

    init {
        viewModelScope.launch {
            repository.getDeclaration()
            repository.getRules()
        }
    }

    val tahsilList : StateFlow<PostalData>
        get() = repository.postalData

    private val _selectedTahsil = MutableStateFlow("")
    val selectedTahsil: StateFlow<String> = _selectedTahsil

    fun getPostalData(postalCode : String) {
        viewModelScope.launch {
            repository.getPostalData(postalCode)
        }
    }
    // Function to update the text state
    fun selectedTahsilChanged(newText: String) {
        _selectedTahsil.value = newText
    }

    val signupResponse : StateFlow<SignupResponse>
        get() = repository.signUpResponse
    val signupResponseCode : StateFlow<Int>
        get() = repository.signUpResponseCode

    var isSignUp by mutableStateOf(false)

    var docFile : MultipartBody.Part ?= null


    var profileFile : MultipartBody.Part ?= null
    var file : MultipartBody.Part ?= null
    var file2 : MultipartBody.Part ?= null
    var diseaseFile : MultipartBody.Part ?= null

    private val referenceID : String
        get() = savedStateHandle.get<String>("referenceID")!!
    private val getName : String
        get() = savedStateHandle.get<String>("name")!!
    private val phone : String
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
    val gender : String
        get() = savedStateHandle.get<String>("gender")!!
    private val maritalStatus : String
        get() = savedStateHandle.get<String>("maritalStatus")!!
    private val dob : String
        get() = savedStateHandle.get<String>("dob")!!
    val profession : String
        get() = savedStateHandle.get<String>("profession")!!
    private val spouseName : String
        get() = savedStateHandle.get<String>("spouse")!!
    private val marriageDate : String
        get() = savedStateHandle.get<String>("marriageDate")!!
    val marriageYears : String
        get() = savedStateHandle.get<String>("marriageYears")!!
    val ageYears : String
        get() = savedStateHandle.get<String>("ageYears")!!

    var pincode : String? = ""

    private val _city = MutableStateFlow("")
    val city: StateFlow<String>
        get() = _city

    private val _state = MutableStateFlow("")
    val state: StateFlow<String>
        get() = _state

    var address : String? = ""
    var email : String? = ""
    var adharNumber : String? = ""
    var adharUri : Uri?= null

    var idNumber : String? = ""
    var panUri : Uri?= null
    var nominee : String? = ""
    var relation : String? = ""
    var nominee2 : String? = ""
    var relation2 : String? = ""
    var isDisease = false
    var isRuleAccepted = false
    var isDeclaration = false
    var isAdharVerified = false
    var isOtherDocVerified = false

    fun cityTextChanged(text : String) {
        _city.value = text
    }
    fun stateTextChanged(text : String) {
        _state.value = text
    }
    fun validateDoc(
        idNumber : String,
        idType : String,
        multiPartBody : MultipartBody.Part
    ){
        viewModelScope.launch {
            repository.validateDocument(idNumber,idType,multiPartBody)
        }
    }

    fun validateOtherDoc(
        idNumber : String,
        idType : String,
        multiPartBody : MultipartBody.Part
    ){
        viewModelScope.launch {
            repository.validateOtherDocument(idNumber,idType,multiPartBody)
        }
    }

    fun signUp (idType: String, rulesAccepted : String, context: Context) {
        viewModelScope.launch {
            Log.d("onViewModel2 Without", "Gotra : $gotra DOB : $dob pass : $password Profession : $profession City :${city.value} State : ${state.value}  Pin : $pincode idType : $idType-$idNumber ")

            repository.signUp(
                referenceID,
                gotra,
                getName,
                father,
                mother,
                dob,
                email!!,
                password,
                maritalStatus,
                spouseName,
                phone,
                address!!,
                city.value,
                state.value,
                pincode!!,
                profession,
                adharNumber!!,
                idType,
                idNumber!!,
                selectedTahsil.value,
                gender,
                ageYears,
                marriageYears,
                marriageDate,
                nominee!!,
                relation!!,
                nominee2!!,
                relation2!!,
                file!!,
                file2!!,
                profileFile!!,
                "$isDisease",
                rulesAccepted,
                isDeclaration.toString(),
                context
            )
        }
    }

    fun signUpWith (idType: String, rulesAccepted : String, context: Context) {
        viewModelScope.launch {
            Log.d("onViewModel2 With", "Gotra : $gotra DOB : $dob pass : $password Profession : $profession City :${city.value} State : ${state.value}  Pin : $pincode idType : $idType-$idNumber ")

            repository.signUp(
                referenceID,
                gotra,
                getName,
                father,
                mother,
                dob,
                email!!,
                password,
                maritalStatus,
                spouseName,
                phone,
                address!!,
                city.value,
                state.value,
                pincode!!,
                profession,
                adharNumber!!,
                idType,
                idNumber!!,
                selectedTahsil.value,
                gender,
                ageYears,
                marriageYears,
                marriageDate,
                nominee!!,
                relation!!,
                nominee2!!,
                relation2!!,
                file!!,
                file2!!,
                profileFile!!,
                diseaseFile!!,
                "$isDisease",
                rulesAccepted,
                isDeclaration.toString(),
                context
            )
        }
    }

}