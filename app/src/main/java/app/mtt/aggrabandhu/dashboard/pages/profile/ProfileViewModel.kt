package app.mtt.aggrabandhu.dashboard.pages.profile

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.mtt.aggrabandhu.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val repository : Repository) : ViewModel(){

    val profileData : StateFlow<ProfileData>
        get() = repository.profileData
    val profileResponseCode : StateFlow<Int>
        get() = repository.profileResponseCode

    fun getProfile(id:Int) {
        viewModelScope.launch {
            repository.getProfileDetails(id)
        }
    }

}