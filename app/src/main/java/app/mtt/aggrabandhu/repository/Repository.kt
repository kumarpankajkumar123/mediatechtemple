package app.mtt.aggrabandhu.repository

import android.util.Log
import app.mtt.aggrabandhu.api.AllApi
import app.mtt.aggrabandhu.authentication.onboarding.DocValidationResponse
import app.mtt.aggrabandhu.authentication.onboarding.ProfessionData
import app.mtt.aggrabandhu.dashboard.pages.liveDonation.LiveDonationData
import app.mtt.aggrabandhu.dashboard.sideNavigation.allMembers.AllMemberData
import app.mtt.aggrabandhu.dashboard.sideNavigation.peopleReceivedDonations.ReceivedDonationData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
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

    private val _liveDonation = MutableStateFlow<List<LiveDonationData>>(emptyList())
    val liveDonation : StateFlow<List<LiveDonationData>>
        get() = _liveDonation

    private val _allMembers = MutableStateFlow<List<AllMemberData>>(emptyList())
    val allMembers : StateFlow<List<AllMemberData>>
        get() = _allMembers

    private val _validateID = MutableStateFlow<DocValidationResponse>(DocValidationResponse(false, false))
    val validateID : StateFlow<DocValidationResponse>
        get() = _validateID

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
        }
    }

    suspend fun getLiveDonationsData(){
        val response = allApi.liveDonations()
        if (response.isSuccessful && response.body() != null){
            _liveDonation.emit(response.body()!!.data)
        }
    }

    suspend fun getAllMembers(){
        val response = allApi.getAllMembers()
        if (response.isSuccessful && response.body() != null){
            _allMembers.emit(response.body()!!.data)
        }
    }

    suspend fun validateDocument(idNumber : String, idType : String, multiPartBody : MultipartBody.Part){
        val response = allApi.validateDocuments(
                multiPartBody,
                idNumber.toRequestBody("multipart/form-data".toMediaTypeOrNull()),
                idType.toRequestBody("multipart/form-data".toMediaTypeOrNull())
            )
        if (response.isSuccessful && response.body() != null){
            _validateID.emit(response.body()!!)
            Log.d("ValidationResponse", response.body()!!.valid.toString())
        } else {
            Log.d("ValidationError", response.code().toString())
        }
        Log.d("ValidationResponse", "${response.code()} ${response.message()}")
    }

    suspend fun getProfileDetails(memberID : Int){
        val response = allApi.getProfileInfo("member_id",memberID)
        if (response.isSuccessful && response.body() != null){

        }
    }

}