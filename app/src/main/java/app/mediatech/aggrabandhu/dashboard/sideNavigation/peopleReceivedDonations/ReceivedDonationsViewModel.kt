package app.mediatech.aggrabandhu.dashboard.sideNavigation.peopleReceivedDonations

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.mediatech.aggrabandhu.dashboard.pages.liveDonation.LiveDonationData
import app.mediatech.aggrabandhu.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReceivedDonationsViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    val receivedDonationsData : StateFlow<List<LiveDonationData>>
    get() = repository.receivedDonationData

    val code : StateFlow<Int>
        get() = repository.receivedDonationDataCode

    init {
        viewModelScope.launch {
            repository.getReceivedDonationsData()
        }
    }

}