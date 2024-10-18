package app.mediatech.aggrabandhu.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.mediatech.aggrabandhu.dashboard.sideNavigation.allMembers.AllMemberData
import app.mediatech.aggrabandhu.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllMembersViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    val allMembers : StateFlow<List<AllMemberData>>
    get() = repository.allMembers

    val allMembersResponseCode : StateFlow<Int>
    get() = repository.membersResponseCode

    init {
        viewModelScope.launch {
            repository.getAllMembers()
        }
    }

}