package app.mtt.aggrabandhu.viewmodel

import android.net.Uri
import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.mtt.aggrabandhu.authentication.onboarding.DocValidationResponse
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

    var profileFile : MultipartBody.Part ?= null
    var file : MultipartBody.Part ?= null
    var file2 : MultipartBody.Part ?= null

    val referenceID : String
        get() = savedStateHandle.get<String>("referenceID")!!
    val getName : String
        get() = savedStateHandle.get<String>("name")!!
    val phone : String
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
    val maritalStatus : String
        get() = savedStateHandle.get<String>("maritalStatus")!!
    val dob : String
        get() = savedStateHandle.get<String>("dob")!!
    val profession : String
        get() = savedStateHandle.get<String>("profession")!!

    var pincode : String? = ""
    var city : String? = ""
    var state : String? = ""
    var address : String? = ""
    var adharNumber : String? = ""
    var adharUri : Uri?= null

    var idNumber : String? = ""
    var panUri : Uri?= null
    var nominee : String? = ""
    var relation : String? = ""
    var nominee2 : String? = ""
    var relation2 : String? = ""
    var isRuleAccepted = false
    var isAadharVerified = false
    var isOtherDocVerified = false

    fun validateDoc(
        idNumber : String,
        idType : String,
        multiPartBody : MultipartBody.Part
    ){
        viewModelScope.launch {
            repository.validateDocument(idNumber,idType,multiPartBody)
        }
    }

    fun signUpUnmarriedWithoutFile3Profile (idType: String, rulesAccepted : String) {
        viewModelScope.launch {
            Log.d("onViewModel2", "Gotra : $gotra DOB : $dob pass : $password Profession : $profession City :$city State : $state  Pin : $pincode idType : $idType-$idNumber ")

            repository.signUpUnmarriedWithoutFile3Profile(
                referenceID,
                gotra,
                getName,
                father,
                mother,
                dob,
                password,
                maritalStatus,
                phone,
                address!!,
                city!!,
                state!!,
                pincode!!,
                profession,
                adharNumber!!,
                idType,
                idNumber!!,
                nominee!!,
                relation!!,
                nominee2!!,
                relation2!!,
                file!!,
                file2!!,
                profileFile!!,
                rulesAccepted
            )
        }
    }

}