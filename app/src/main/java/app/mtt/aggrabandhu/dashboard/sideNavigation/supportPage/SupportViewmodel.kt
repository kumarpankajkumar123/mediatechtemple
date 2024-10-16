package app.mtt.aggrabandhu.dashboard.sideNavigation.supportPage

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
class SupportViewmodel @Inject constructor(
    private val repository: Repository) : ViewModel() {

    var enquiryDescription by mutableStateOf("")
    var contact by mutableStateOf("")
    var name by mutableStateOf("")

    val supportResponse : StateFlow<Int>
        get() = repository.supportResponse

    fun init(sP: SharedPrefManager) {
        name = sP.getFullName().toString()
        contact = sP.getFullName().toString()
    }

    fun postEnquiry(context: Context) {
        viewModelScope.launch {
            repository.support(enquiryDescription, contact, name, context)
        }
    }

}