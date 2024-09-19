package app.mtt.aggrabandhu.repository

import android.util.Log
import app.mtt.aggrabandhu.api.AllApi
import app.mtt.aggrabandhu.authentication.onboarding.ProfessionData
import app.mtt.aggrabandhu.dashboard.sideNavigation.peopleReceivedDonations.ReceivedDonationData
import app.mtt.aggrabandhu.dashboard.sideNavigation.peopleReceivedDonations.ReceivedDonationsData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class Repository @Inject constructor(private val allApi: AllApi){

    private val _professions = MutableStateFlow<List<ProfessionData>>(emptyList())
    val professions : StateFlow<List<ProfessionData>>
        get() = _professions

    private val _gotra = MutableStateFlow<List<ProfessionData>>(emptyList())
    val gotra : StateFlow<List<ProfessionData>>
        get() = _gotra

    private val _receivedDonations = MutableStateFlow<List<ReceivedDonationData>>(emptyList())
    val receivedDonationData : StateFlow<List<ReceivedDonationData>>
        get() = _receivedDonations

    suspend fun getProfession() {
        val response = allApi.getProfession()
        if (response.isSuccessful && response.body() != null) {
            _professions.emit(response.body()!!)
        }
    }

    suspend fun getGotra() {
        val response = allApi.getGotra()
        if (response.isSuccessful && response.body() != null) {
            _gotra.emit(response.body()!!)
        }
    }

    suspend fun getReceivedDonationsData(){
        val response = allApi.receivedDonations()
        if (response.isSuccessful && response.body() != null){
            _receivedDonations.emit(response.body()!!.data)
            Log.d("DonationData", response.body()!!.toString())
            Log.d("DonationData", response.body()!!.data.toString())
        }
    }

}