package app.mtt.aggrabandhu.repository

import android.util.Log
import app.mtt.aggrabandhu.api.AllApi
import app.mtt.aggrabandhu.authentication.onboarding.firstOnboarding.ProfessionData
import app.mtt.aggrabandhu.dashboard.pages.liveDonation.LiveDonationData
import app.mtt.aggrabandhu.dashboard.pages.profile.ProfileData
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

//    private val _validateID = MutableStateFlow<DocValidationResponse>(DocValidationResponse(false, false, ""))
//    val validateID : StateFlow<DocValidationResponse>
//        get() = _validateID
    private val _validateID = MutableStateFlow<Int>(0)
    val validateID : StateFlow<Int>
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

    suspend fun getAllMembers() {
        val response = allApi.getAllMembers()
        if (response.isSuccessful && response.body() != null){
            _allMembers.emit(response.body()!!.data)
        }
    }

    suspend fun validateDocument(idNumber : String, idType : String, multiPartBody : MultipartBody.Part){
        try {
            val response = allApi.validateDocuments(
                multiPartBody,
                idNumber.toRequestBody("multipart/form-data".toMediaTypeOrNull()),
                idType.toRequestBody("multipart/form-data".toMediaTypeOrNull())
            )
            if (response.isSuccessful && response.body() != null) {
                _validateID.emit(response.code())
                Log.d(
                    "ValidationResponse",
                    "${response.code()} ${response.message()} ${response.body()!!.valid} "
                )
            } else {
                _validateID.emit(response.code())
                Log.d("ValidationError", "${response.code()} ${response.message()}")
            }
        } catch (e : Exception) {
            e.printStackTrace()
            _validateID.emit(400)
        }
    }

    suspend fun signUpUnmarriedWithoutFile3Profile (
        referenceID : String,
        gotra : String,
        name : String,
        fatherName : String,
        motherName : String,
        dob : String,
        password : String,
        maritalStatus : String,
        mobileNumber : String,
        address : String,
        district : String,
        state : String,
        pincode : String,
        profession : String,
        adharNumber : String,
        idType : String,
        idNumber : String,
        nominee : String,
        relationShip : String,
        nominee2 : String,
        relationShip2 : String,
        adharFile : MultipartBody.Part,
        panFile : MultipartBody.Part,
        profile : MultipartBody.Part,
        rulesAccepted : String,
    ) {
        Log.d("SignUP", "Sending")
        Log.d("onViewModel2",
            "Gotra : $gotra, " +
                    "DOB : $dob, " +
                    "father : $fatherName, " +
                    "mother : $motherName, " +
                    "pass : $password, " +
                    "Profession : $profession, " +
                    "Marital : $maritalStatus, " +
                    "City :$district, " +
                    "State : $state,  " +
                    "Pin : $pincode, " +
                    "Rules : $rulesAccepted, " +
                    "referenceID : $referenceID, " +
                    "idType : $idType-$idNumber, "
        )
        Log.d("Files", "$adharFile, \n$panFile, \n$profile")

        val response = allApi.signUpWithoutDisease(
            RequestBody.create("multipart/form-data".toMediaTypeOrNull(), name),
            RequestBody.create("multipart/form-data".toMediaTypeOrNull(), gotra),
            RequestBody.create("multipart/form-data".toMediaTypeOrNull(), fatherName),
            RequestBody.create("multipart/form-data".toMediaTypeOrNull(), motherName),
            RequestBody.create("multipart/form-data".toMediaTypeOrNull(), dob),
            RequestBody.create("multipart/form-data".toMediaTypeOrNull(), "example@gmail.com"),
            RequestBody.create("multipart/form-data".toMediaTypeOrNull(), password),
            RequestBody.create("multipart/form-data".toMediaTypeOrNull(), maritalStatus),
            RequestBody.create("multipart/form-data".toMediaTypeOrNull(), mobileNumber),
            RequestBody.create("multipart/form-data".toMediaTypeOrNull(), address),
            RequestBody.create("multipart/form-data".toMediaTypeOrNull(), district),
            RequestBody.create("multipart/form-data".toMediaTypeOrNull(), state),
            RequestBody.create("multipart/form-data".toMediaTypeOrNull(), pincode),
            RequestBody.create("multipart/form-data".toMediaTypeOrNull(), profession),
            RequestBody.create("multipart/form-data".toMediaTypeOrNull(), adharNumber),
            RequestBody.create("multipart/form-data".toMediaTypeOrNull(), idType),
            RequestBody.create("multipart/form-data".toMediaTypeOrNull(), idNumber),
            adharFile,
            panFile,
            profile,
            RequestBody.create("multipart/form-data".toMediaTypeOrNull(), nominee),
            RequestBody.create("multipart/form-data".toMediaTypeOrNull(), nominee2),
            RequestBody.create("multipart/form-data".toMediaTypeOrNull(), relationShip),
            RequestBody.create("multipart/form-data".toMediaTypeOrNull(), relationShip2),
            RequestBody.create("multipart/form-data".toMediaTypeOrNull(), referenceID),
            RequestBody.create("multipart/form-data".toMediaTypeOrNull(), rulesAccepted)
        )
        if (response.isSuccessful && response.body() != null) {
            Log.d("CreateMember", "Sent : ${response.code()} ${response.message()} ${response.body()?.message}")
        } else {
            Log.d("CreateMember", "${response.code()} ${response.message()} ${response.body()?.message}")
        }
    }

    private val _profileData = MutableStateFlow<ProfileData>(
        ProfileData("","","","",false,"","","","","","",0,"","","","","","","","","","","","",false,"","","","","")
    )
    val profileData : StateFlow<ProfileData>
        get() = _profileData

    suspend fun getProfileDetails(memberID : Int){
        val response = allApi.getProfileInfo("id",memberID)
        if (response.isSuccessful && response.body() != null){
            _profileData.emit(response.body()!![0])
            Log.d("Profile" ,"${response.body().toString()} ${response.code().toString()}")
        }
        Log.d("Profile" ,"${response.body().toString()} ${response.code()} ")
    }

}