package app.mediatech.aggrabandhu.dashboard.pages.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.mediatech.aggrabandhu.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    val allNotifications : StateFlow<List<Data>>
    get() = repository.notificationData

    val notificationCode : StateFlow<Int>
    get() = repository.notificationCode

    init {
        viewModelScope.launch {
            repository.getNotification()
        }
    }

}