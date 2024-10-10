package app.mtt.aggrabandhu.repository

import android.util.Log
import app.mtt.aggrabandhu.api.AllApi
import app.mtt.aggrabandhu.authentication.login.LoginResponse
import app.mtt.aggrabandhu.authentication.onboarding.firstOnboarding.ProfessionData
import app.mtt.aggrabandhu.authentication.onboarding.secondOnboarding.SignupResponse
import app.mtt.aggrabandhu.dashboard.pages.liveDonation.LiveDonationData
import app.mtt.aggrabandhu.dashboard.pages.profile.Nominees
import app.mtt.aggrabandhu.dashboard.pages.profile.ProfileData
import app.mtt.aggrabandhu.dashboard.sideNavigation.allMembers.AllMemberData
import app.mtt.aggrabandhu.dashboard.sideNavigation.peopleReceivedDonations.ReceivedDonationData
import app.mtt.aggrabandhu.utils.getRandomString
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

    private val _liveDonationResponseCode = MutableStateFlow(0)
    val liveDonationResponseCode : StateFlow<Int>
        get() = _liveDonationResponseCode
    private val _liveDonation = MutableStateFlow<List<LiveDonationData>>(emptyList())
    val liveDonation : StateFlow<List<LiveDonationData>>
        get() = _liveDonation

    private val _membersResponseCode = MutableStateFlow<Int>(0)
    val membersResponseCode : StateFlow<Int>
        get() = _membersResponseCode
    private val _allMembers = MutableStateFlow<List<AllMemberData>>(emptyList())
    val allMembers : StateFlow<List<AllMemberData>>
        get() = _allMembers

    private val _loginResponse = MutableStateFlow<LoginResponse>(LoginResponse( "", 0))
    val loginResponse : StateFlow<LoginResponse>
        get() = _loginResponse
    private var _loginResponseCode = 0

    private val _signUpResponse = MutableStateFlow<SignupResponse>(SignupResponse())
    val signUpResponse : StateFlow<SignupResponse>
        get() = _signUpResponse
    private val _signUpResponseCode = MutableStateFlow<Int>( 0 )
    val signUpResponseCode : StateFlow<Int>
        get() = _signUpResponseCode

    private val _editProfileResponseCode = MutableStateFlow<Int>( 0 )
    val editProfileResponseCode : StateFlow<Int>
        get() = _editProfileResponseCode

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
        try {
            val response = allApi.liveDonations()
            if (response.isSuccessful && response.body() != null) {
                _liveDonation.emit(response.body()!!.data)
                _liveDonationResponseCode.emit(response.code())
                Log.d("Live  Donation", "${response.code()}-> ${response.body()!!.data.toString()}")
            } else {
                _liveDonationResponseCode.emit(response.code())
                Log.d("Live  Donation", "${response.code()}-> ${response.message()}")
            }
        } catch (e : Exception){
            _liveDonationResponseCode.emit(400)
            e.printStackTrace()
        }
    }

    suspend fun getAllMembers() {
        try {
            val response = allApi.getAllMembers()
            if (response.isSuccessful && response.body() != null) {
                _allMembers.emit(response.body()!!.data)
                _membersResponseCode.emit(response.code())
                Log.d("All Members", "${response.code()}-> ${response.body()!!.data.toString()}")
            } else {
                Log.d("All Members", "${response.code()}-> {${response.message()}}")
                _membersResponseCode.emit(response.code())
            }
        } catch (e : Exception) {
            _membersResponseCode.emit(400)
            e.printStackTrace()
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

    suspend fun login (mobileNumber: String, password: String) : Int {
        try {
            _loginResponseCode = 1
            val response = allApi.login(
                mobileNumber.toRequestBody("multipart/form-data".toMediaTypeOrNull()),
                password.toRequestBody("multipart/form-data".toMediaTypeOrNull())
            )
            if (response.isSuccessful && response.body() != null) {
                _loginResponse.emit(response.body()!!)
                _loginResponseCode = (response.code())
                Log.d(
                    "LoginResponse",
                    "${response.code()} ${response.message()} ${response.body()!!.userid} "
                )
            } else {
                _loginResponseCode = (401)
                Log.d("LoginError", "${response.code()} ${response.message()}")
            }
            return _loginResponseCode
        } catch (e : Exception) {
            e.printStackTrace()
            _loginResponseCode = (401)
            return _loginResponseCode
        }
    }

    suspend fun signUp (
        referenceID : String,
        gotra : String,
        name : String,
        fatherName : String,
        motherName : String,
        dob : String,
        password : String,
        maritalStatus : String,
        spouseName : String,
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
        disease : String,
        rulesAccepted : String,
    ) {
        Log.d("SignUP", "Sending Without")
        Log.d("onViewModel2",
            "Gotra : $gotra, " +
                    "DOB : $dob, " +
                    "father : $fatherName, " +
                    "mother : $motherName, " +
                    "pass : $password, " +
                    "Profession : $profession, " +
                    "Marital : $maritalStatus, " +
                    "spouse : $spouseName, " +
                    "City :$district, " +
                    "State : $state,  " +
                    "Pin : $pincode, " +
                    "Rules : $rulesAccepted, " +
                    "referenceID : $referenceID, " +
                    "idType : $idType-$idNumber, "
        )
        Log.d("Files", "$adharFile, \n$panFile, \n$profile")

        val response = allApi.signUp(
            RequestBody.create("multipart/form-data".toMediaTypeOrNull(), name),
            RequestBody.create("multipart/form-data".toMediaTypeOrNull(), gotra),
            RequestBody.create("multipart/form-data".toMediaTypeOrNull(), fatherName),
            RequestBody.create("multipart/form-data".toMediaTypeOrNull(), motherName),
            RequestBody.create("multipart/form-data".toMediaTypeOrNull(), dob),
            RequestBody.create("multipart/form-data".toMediaTypeOrNull(), "${getRandomString(4)}@gmail.com"),
            RequestBody.create("multipart/form-data".toMediaTypeOrNull(), password),
            RequestBody.create("multipart/form-data".toMediaTypeOrNull(), maritalStatus),
            RequestBody.create("multipart/form-data".toMediaTypeOrNull(), spouseName),
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
            RequestBody.create("multipart/form-data".toMediaTypeOrNull(), disease),
            RequestBody.create("multipart/form-data".toMediaTypeOrNull(), rulesAccepted)
        )
        if (response.isSuccessful && response.body() != null) {
            Log.d("CreateMember", "Sent : ${response.code()} ${response.message()} ${response.body()?.message}")
            _signUpResponse.emit(response.body()!!)
            _signUpResponseCode.emit(response.code())
        } else {
            Log.d("CreateMember", "${response.code()} ${response.message()}")
            _signUpResponseCode.emit(response.code())
        }
    }

    suspend fun signUp (
        referenceID : String,
        gotra : String,
        name : String,
        fatherName : String,
        motherName : String,
        dob : String,
        password : String,
        maritalStatus : String,
        spouseName : String,
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
        diseaseFile : MultipartBody.Part,
        disease : String,
        rulesAccepted : String,
    ) {
        Log.d("SignUP", "Sending Everything")
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
        Log.d("Files", "$adharFile, \n$panFile, \n$profile \n$disease $diseaseFile")

        val response = allApi.signUp(
            RequestBody.create("multipart/form-data".toMediaTypeOrNull(), name),
            RequestBody.create("multipart/form-data".toMediaTypeOrNull(), gotra),
            RequestBody.create("multipart/form-data".toMediaTypeOrNull(), fatherName),
            RequestBody.create("multipart/form-data".toMediaTypeOrNull(), motherName),
            RequestBody.create("multipart/form-data".toMediaTypeOrNull(), dob),
            RequestBody.create("multipart/form-data".toMediaTypeOrNull(), "${getRandomString(4)}@gmail.com"),
            RequestBody.create("multipart/form-data".toMediaTypeOrNull(), password),
            RequestBody.create("multipart/form-data".toMediaTypeOrNull(), maritalStatus),
            RequestBody.create("multipart/form-data".toMediaTypeOrNull(), spouseName),
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
            RequestBody.create("multipart/form-data".toMediaTypeOrNull(), disease),
            RequestBody.create("multipart/form-data".toMediaTypeOrNull(), rulesAccepted),
            diseaseFile
        )
        if (response.isSuccessful && response.body() != null) {
            Log.d("CreateMember", "Sent : ${response.code()} ${response.message()} ${response.body()?.message}")
            _signUpResponse.emit(response.body()!!)
            _signUpResponseCode.emit(response.code())
        } else {
            Log.d("CreateMember", "${response.code()} ${response.message()} ${response.body()?.message}")
            _signUpResponseCode.emit(response.code())
        }
    }

    suspend fun editProfile (
        name : String,
        fatherName : String,
        motherName : String,
        mobileNumber : String,
        address : String,
        district : String,
        state : String,
        pincode : String,
        nominee : String,
        relationShip : String,
        nominee2 : String,
        relationShip2 : String,
        profile : MultipartBody.Part,
    ) {
        Log.d("Edit Profile",
                    "father : $fatherName, " +
                    "mother : $motherName, " +
                    "City :$district, " +
                    "State : $state,  " +
                    "Pin : $pincode, "
        )
        Log.d("Files","$profile")

        val response = allApi.editProfile(
            "2",
            RequestBody.create("multipart/form-data".toMediaTypeOrNull(), name),
            RequestBody.create("multipart/form-data".toMediaTypeOrNull(), fatherName),
            RequestBody.create("multipart/form-data".toMediaTypeOrNull(), motherName),
            RequestBody.create("multipart/form-data".toMediaTypeOrNull(), mobileNumber),
            RequestBody.create("multipart/form-data".toMediaTypeOrNull(), address),
            RequestBody.create("multipart/form-data".toMediaTypeOrNull(), district),
            RequestBody.create("multipart/form-data".toMediaTypeOrNull(), state),
            RequestBody.create("multipart/form-data".toMediaTypeOrNull(), pincode),
            profile,
            RequestBody.create("multipart/form-data".toMediaTypeOrNull(), nominee),
            RequestBody.create("multipart/form-data".toMediaTypeOrNull(), nominee2),
            RequestBody.create("multipart/form-data".toMediaTypeOrNull(), relationShip),
            RequestBody.create("multipart/form-data".toMediaTypeOrNull(), relationShip2)
        )
        if (response.isSuccessful && response.body() != null) {
            Log.d("CreateMember", "Sent : ${response.code()} ${response.message()} ${response.body()?.message}")
            _editProfileResponseCode.emit(response.code())
        } else {
            Log.d("CreateMember", "${response.code()} ${response.message()} ${response.body()?.message}")
            _editProfileResponseCode.emit(response.code())
        }
    }


    private val _profileResponseCode = MutableStateFlow(0)
    val profileResponseCode : StateFlow<Int>
        get() = _profileResponseCode
    private val _profileData = MutableStateFlow<ProfileData>(
        ProfileData("","","","",false,"","","","","","",0,"","","","","","","","","","","","",false,"","","","","",
            listOf(Nominees("","","","")))
    )
    val profileData : StateFlow<ProfileData>
        get() = _profileData

    suspend fun getProfileDetails(memberID : Int){
        try {
            val response = allApi.getProfileInfo("id", memberID)
            if (response.isSuccessful && response.body() != null) {
                _profileResponseCode.emit(response.code())
                _profileData.emit(response.body()!!)
                Log.d("Profile", "${response.body().toString()} ${response.code()}")
            } else {
                _profileResponseCode.emit(response.code())
                Log.d("Profile", "${response.message()} ${response.code()} ")
            }
        }catch (e:Exception){
            e.printStackTrace()
            _profileResponseCode.emit(400)
        }
    }

    private val _rules = MutableStateFlow("wait")
    val rules : StateFlow<String>
        get() = _rules

    suspend fun getRules(){
        val response = allApi.getRules()
        if (response.isSuccessful && response.body() != null){
            _rules.emit(response.body()!![0].rule)
            Log.d("Rules" ,"${response.body().toString()} ${response.code().toString()}")
        } else {
            _rules.emit("Error")
            Log.d("Rules", "${response.body().toString()} ${response.code()} ")
        }
    }

}