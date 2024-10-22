package app.mediatech.aggrabandhu.dashboard.sideNavigation.myDonations

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.mediatech.aggrabandhu.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyDonationViewmodel @Inject constructor(
    private val repository: Repository) : ViewModel() {

        val responseCode : StateFlow<Int>
            get() = repository.myDonationCode

        val responseData : StateFlow<List<MyDonation>>
            get() = repository.myDonationData

        fun myDonation(memberId:String, context: Context) {
            viewModelScope.launch {
                repository.myDonation(
                    memberId,
                    context
                )
            }
        }

}