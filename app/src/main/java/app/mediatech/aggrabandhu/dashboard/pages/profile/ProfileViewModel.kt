package app.mediatech.aggrabandhu.dashboard.pages.profile

import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.mediatech.aggrabandhu.authentication.onboarding.secondOnboarding.PostalData
import app.mediatech.aggrabandhu.di.baseUrl
import app.mediatech.aggrabandhu.repository.Repository
import app.mediatech.aggrabandhu.utils.prepareFilePart
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val repository : Repository) : ViewModel(){

    val editProfileResponseCode : StateFlow<Int>
        get() = repository.editProfileResponseCode

    val profileData : StateFlow<ProfileData>
        get() = repository.profileData
    val profileResponseCode : StateFlow<Int>
        get() = repository.profileResponseCode

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
        tahsil = newText
        _selectedTahsil.value = newText
    }

    var imageUri by mutableStateOf<Uri?>(null)

    var memberId by mutableStateOf("")
    var profileUrl by mutableStateOf("")
    var name by mutableStateOf( "")
    var father by mutableStateOf( "")
    var motherName by mutableStateOf( "")
    var gotra by mutableStateOf( "")
    var gender by mutableStateOf( "")
    /* --------------------- Get MarriageStatus Data -------------------*/
    val maritalStatusList = arrayListOf("Married", "Single", "Divorced", "Widow", "Widower")
    var maritalStatus by mutableStateOf( "")
    var spouseName by mutableStateOf( "")
    var marriageDate by mutableStateOf( "")
    var marriageYears by mutableStateOf( "")
    var profession by mutableStateOf( "")
    var district by mutableStateOf( "")
    var tahsil by mutableStateOf( "")
    var state by mutableStateOf( "")
    var pinCode by mutableStateOf( "")
    var address by mutableStateOf( "")
    var email by mutableStateOf( "")
    var nominee by mutableStateOf( "")
    var relation by mutableStateOf( "")
    var nominee2 by mutableStateOf( "")
    var relation2 by mutableStateOf( "")

    var isProfile by mutableStateOf( true)
    var isProfileEdited by mutableStateOf( true)

    fun getProfile(id:Int, context: Context) {
        viewModelScope.launch {
            repository.getProfileDetails(id, context)
        }
    }

    fun getProfile() {
        memberId = profileData.value.id.toString()
        profileUrl = "$baseUrl${profileData.value.profileUrl}"
        name = profileData.value.name
        father = profileData.value.father_name
        motherName = profileData.value.mother_name
        gotra = profileData.value.gotra
        gender = profileData.value.gender
        maritalStatus = profileData.value.marital_status
        spouseName = profileData.value.spouse_name
        marriageDate = profileData.value.marriage_date
        marriageYears = profileData.value.marriage_age
        profession = profileData.value.profession
        district = profileData.value.district
        tahsil = profileData.value.tahsil
        state = profileData.value.state
        pinCode = profileData.value.pincode
        address = profileData.value.address
        email = profileData.value.email
        nominee = profileData.value.nominees[0].nominee
        relation = profileData.value.nominees[0].relationship
        nominee2 = profileData.value.nominees[0].nominee2
        relation2 = profileData.value.nominees[0].relationship2
    }

    fun editProfile(context: Context) {
        var profilePart: MultipartBody.Part? = null
        viewModelScope.launch {
            if (imageUri != null) {
                profilePart = prepareFilePart(imageUri!!, "profile", context)
            }
//            Toast.makeText(context, "$email", Toast.LENGTH_SHORT).show()
            repository.editProfile(
                memberId,
                name,
                father,
                motherName,
                profession,
                address,
                maritalStatus,
                spouseName,
                marriageYears,
                marriageDate,
                district,
                state,
                tahsil,
                pinCode,
                email,
                nominee,
                relation,
                nominee2,
                relation2,
                profile = profilePart
            )
        }
    }

}