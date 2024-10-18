package app.mediatech.aggrabandhu.dashboard.sideNavigation.policy

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.mediatech.aggrabandhu.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PolicyViewmodel @Inject constructor(
    private val repository: Repository) : ViewModel() {

    val policy : StateFlow<String>
        get() = repository.policy

    init {
        viewModelScope.launch {
            repository.getPolicy()
        }
    }
}