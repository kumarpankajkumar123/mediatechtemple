package app.mtt.aggrabandhu.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.mtt.aggrabandhu.dashboard.pages.liveDonation.LiveDonationData
import app.mtt.aggrabandhu.dashboard.sideNavigation.peopleReceivedDonations.ReceivedDonationData
import app.mtt.aggrabandhu.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LiveDonationsViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    val responseCode : StateFlow<Int>
        get() = repository.liveDonationResponseCode
    val liveDonationsData : StateFlow<List<LiveDonationData>>
    get() = repository.liveDonation

    init {
        viewModelScope.launch {
            repository.getLiveDonationsData()
        }
    }

}