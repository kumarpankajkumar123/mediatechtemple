package app.mediatech.aggrabandhu.dashboard.pages.profile.editProfile

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.mediatech.aggrabandhu.dashboard.pages.profile.ProfileData
import app.mediatech.aggrabandhu.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditProfileViewModel @Inject constructor(
    private val repository : Repository,
    private val savedStateHandle: SavedStateHandle) : ViewModel(){

    val editProfileResponseCode : StateFlow<Int>
        get() = repository.editProfileResponseCode

    val profileData : StateFlow<ProfileData>
        get() = repository.profileData
    val profileResponseCode : StateFlow<Int>
        get() = repository.profileResponseCode

    fun getProfile(id:Int, context: Context) {
        viewModelScope.launch {
            repository.getProfileDetails(id, context)
        }
    }

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

    fun editProfile() {
        viewModelScope.launch {
//            repository.editProfile(
//
//            )
        }
    }

}