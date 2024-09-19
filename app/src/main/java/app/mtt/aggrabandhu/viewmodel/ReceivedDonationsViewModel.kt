package app.mtt.aggrabandhu.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.mtt.aggrabandhu.dashboard.sideNavigation.peopleReceivedDonations.ReceivedDonationData
import app.mtt.aggrabandhu.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReceivedDonationsViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    val receivedDonationsData : StateFlow<List<ReceivedDonationData>>
    get() = repository.receivedDonationData

    init {
        viewModelScope.launch {
            repository.getReceivedDonationsData()
        }
    }

}