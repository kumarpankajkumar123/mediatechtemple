package app.mediatech.aggrabandhu.repository

import android.content.Context
import android.util.Log
import android.widget.Toast
import app.mediatech.aggrabandhu.api.AllApi
import app.mediatech.aggrabandhu.api.ApiService
import app.mediatech.aggrabandhu.authentication.login.LoginResponse
import app.mediatech.aggrabandhu.authentication.onboarding.firstOnboarding.ProfessionData
import app.mediatech.aggrabandhu.authentication.onboarding.secondOnboarding.PostalData
import app.mediatech.aggrabandhu.authentication.onboarding.secondOnboarding.SignupResponse
import app.mediatech.aggrabandhu.dashboard.pages.liveDonation.LiveDonationData
import app.mediatech.aggrabandhu.dashboard.pages.profile.Nominees
import app.mediatech.aggrabandhu.dashboard.pages.profile.ProfileData
import app.mediatech.aggrabandhu.dashboard.sideNavigation.allMembers.AllMemberData
import app.mediatech.aggrabandhu.dashboard.sideNavigation.peopleReceivedDonations.ReceivedDonationData
import app.mediatech.aggrabandhu.utils.SharedPrefManager
import es.dmoral.toasty.Toasty
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
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

    private val _loginResponse = MutableStateFlow<LoginResponse>(LoginResponse( "", 0, ""))
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

    private val _validateOtherID = MutableStateFlow<Int>(0)
    val validateOtherID : StateFlow<Int>
        get() = _validateOtherID

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

    suspend fun getLiveDonationsData() {
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

    suspend fun validateDocument(idNumber : String, idType : String, multiPartBody : MultipartBody.Part) {
        _validateID.emit(0)
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
            Log.d("Error inn validation", e.message.toString())
            _validateID.emit(400)
        }
    }
    suspend fun validateOtherDocument(idNumber : String, idType : String, multiPartBody : MultipartBody.Part){
        _validateOtherID.emit(0)
        try {
            val response = allApi.validateDocuments(
                multiPartBody,
                idNumber.toRequestBody("multipart/form-data".toMediaTypeOrNull()),
                idType.toRequestBody("multipart/form-data".toMediaTypeOrNull())
            )
            if (response.isSuccessful && response.body() != null) {
                _validateOtherID.emit(response.code())
                Log.d(
                    "ValidationResponse",
                    "${response.code()} ${response.message()} ${response.body()!!.valid} "
                )
            } else {
                _validateOtherID.emit(response.code())
                Log.d("ValidationError", "${response.code()} ${response.message()}")
            }
        } catch (e : Exception){
            Log.d("Error inn validation", e.message.toString())
            _validateOtherID.emit(400)
        }
    }

    suspend fun login (mobileNumber: String, password: String, context: Context) : Int {
        _loginResponseCode = 0
        try {
            val sp = SharedPrefManager(context)
            val response = allApi.login(
                mobileNumber.toRequestBody("multipart/form-data".toMediaTypeOrNull()),
                password.toRequestBody("multipart/form-data".toMediaTypeOrNull())
            )
            if (response.isSuccessful && response.body() != null) {
                sp.saveMemberID(response.body()!!.userid)
                sp.saveLoginStatus(true)
                sp.savePhone(mobileNumber)
                sp.saveFullName(response.body()!!.username)

                Log.d(
                    "LoginResponse",
                    "${response.code()} ${response.message()} ${response.body()!!.userid} "
                )
                _loginResponse.emit(response.body()!!)
                _loginResponseCode = (response.code())
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
        email : String,
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
        tahsil : String,
        gender : String,
        total_age : String,
        marriage_age : String,
        marriage_date : String,
        nominee : String,
        relationShip : String,
        nominee2 : String,
        relationShip2 : String,
        adharFile : MultipartBody.Part,
        panFile : MultipartBody.Part,
        profile : MultipartBody.Part,
        disease : String,
        rulesAccepted : String,
        declarationAccepted : String,
        context: Context
    ) {
        var marriage_date1 = marriage_date
        if (marriage_date1 == "no"){
            marriage_date1 = ""
        }
        Log.d("SignUP", "Sending Without")
        Log.d("onViewModel2",
            "Gotra : $gotra, " +
                    "marriageDate : $marriage_date1 " +
                    "referenceID : $referenceID, " +
                    "phone : $mobileNumber, " +
                    "adhar : $adharNumber, " +
                    "idNumber : $idNumber, " +
                    "DOB : $dob, " +
                    "declaration : $declarationAccepted, " +
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

        try {
            val response = allApi.signUp(
                RequestBody.create("multipart/form-data".toMediaTypeOrNull(), name),
                RequestBody.create("multipart/form-data".toMediaTypeOrNull(), gotra),
                RequestBody.create("multipart/form-data".toMediaTypeOrNull(), fatherName),
                RequestBody.create("multipart/form-data".toMediaTypeOrNull(), motherName),
                RequestBody.create("multipart/form-data".toMediaTypeOrNull(), dob),
                RequestBody.create("multipart/form-data".toMediaTypeOrNull(), email),
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
                RequestBody.create("multipart/form-data".toMediaTypeOrNull(), tahsil),
                RequestBody.create("multipart/form-data".toMediaTypeOrNull(), gender),
                RequestBody.create("multipart/form-data".toMediaTypeOrNull(), marriage_age),
                RequestBody.create("multipart/form-data".toMediaTypeOrNull(), total_age),
                RequestBody.create("multipart/form-data".toMediaTypeOrNull(), marriage_date1),
                RequestBody.create("multipart/form-data".toMediaTypeOrNull(), nominee),
                RequestBody.create("multipart/form-data".toMediaTypeOrNull(), nominee2),
                RequestBody.create("multipart/form-data".toMediaTypeOrNull(), relationShip),
                RequestBody.create("multipart/form-data".toMediaTypeOrNull(), relationShip2),
                RequestBody.create("multipart/form-data".toMediaTypeOrNull(), referenceID),
                RequestBody.create("multipart/form-data".toMediaTypeOrNull(), disease),
                RequestBody.create("multipart/form-data".toMediaTypeOrNull(), rulesAccepted),
                RequestBody.create("multipart/form-data".toMediaTypeOrNull(), declarationAccepted)
            )
            if (response.isSuccessful && response.body() != null) {
                val sharedPref = SharedPrefManager(context)
                sharedPref.saveMemberID(response.body()?.memberAdd?.id!!)
                Log.d(
                    "CreateMember",
                    "Sent : ${response.code()} ${response.message()} ${response.body()?.message}"
                )
                _signUpResponse.emit(response.body()!!)
                _signUpResponseCode.emit(response.code())
            } else {
                Log.d("CreateMember", "${response.code()} ${response.message()}")
                _signUpResponseCode.emit(response.code())
            }
        } catch (e : Exception) {
            Log.d("Signup Without", e.message.toString())
        }
    }

    suspend fun signUp (
        referenceID : String,
        gotra : String,
        name : String,
        fatherName : String,
        motherName : String,
        dob : String,
        email : String,
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
        tahsil : String,
        gender : String,
        total_age : String,
        marriage_age : String,
        marriage_date : String,
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
        declarationAccepted : String,
        context: Context
    ) {
        Log.d("SignUP", "Sending Everything")
        Log.d("onViewModel2",
            "Gotra : $gotra, " +
                    "DOB : $dob, " +
                    "Declaration : $declarationAccepted, " +
//                    "father : $fatherName, " +
//                    "mother : $motherName, " +
//                    "pass : $password, " +
//                    "Profession : $profession, " +
//                    "Marital : $maritalStatus, " +
//                    "City :$district, " +
//                    "State : $state,  " +
//                    "Pin : $pincode, " +
//                    "Rules : $rulesAccepted, " +
                    "referenceID : $referenceID, " +
                    "idType : $idType-$idNumber, "
        )
        Log.d("Files", "$adharFile, \n$panFile, \n$profile \n$disease $diseaseFile")

        try {
            val response = allApi.signUp(
                RequestBody.create("multipart/form-data".toMediaTypeOrNull(), name),
                RequestBody.create("multipart/form-data".toMediaTypeOrNull(), gotra),
                RequestBody.create("multipart/form-data".toMediaTypeOrNull(), fatherName),
                RequestBody.create("multipart/form-data".toMediaTypeOrNull(), motherName),
                RequestBody.create("multipart/form-data".toMediaTypeOrNull(), dob),
                RequestBody.create("multipart/form-data".toMediaTypeOrNull(), email),
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
                RequestBody.create("multipart/form-data".toMediaTypeOrNull(), tahsil),
                RequestBody.create("multipart/form-data".toMediaTypeOrNull(), gender),
                RequestBody.create("multipart/form-data".toMediaTypeOrNull(), marriage_age),
                RequestBody.create("multipart/form-data".toMediaTypeOrNull(), total_age),
                RequestBody.create("multipart/form-data".toMediaTypeOrNull(), marriage_date),
                RequestBody.create("multipart/form-data".toMediaTypeOrNull(), nominee),
                RequestBody.create("multipart/form-data".toMediaTypeOrNull(), nominee2),
                RequestBody.create("multipart/form-data".toMediaTypeOrNull(), relationShip),
                RequestBody.create("multipart/form-data".toMediaTypeOrNull(), relationShip2),
                RequestBody.create("multipart/form-data".toMediaTypeOrNull(), referenceID),
                RequestBody.create("multipart/form-data".toMediaTypeOrNull(), disease),
                RequestBody.create("multipart/form-data".toMediaTypeOrNull(), rulesAccepted),
                RequestBody.create("multipart/form-data".toMediaTypeOrNull(), declarationAccepted),
                diseaseFile
            )
            if (response.isSuccessful && response.body() != null) {
                val sharedPref = SharedPrefManager(context)
                sharedPref.saveMemberID(response.body()?.memberAdd?.id!!)
                Log.d(
                    "CreateMember",
                    "Sent : ${response.code()} ${response.message()} ${response.body()?.message}"
                )
                _signUpResponse.emit(response.body()!!)
                _signUpResponseCode.emit(response.code())
            } else {
                Log.d(
                    "CreateMember",
                    "${response.code()} ${response.message()} ${response.body()?.message}"
                )
                _signUpResponseCode.emit(response.code())
            }
        }catch (e : Exception){
            Log.d("SignUp With", e.message.toString())
            _signUpResponseCode.emit(400)
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
        ProfileData("","","","",false,"","","","","","","","",0,"","","","","","","","","","","","",false,"","","","","","","",
            listOf(Nominees("","","","")))
    )
    val profileData : StateFlow<ProfileData>
        get() = _profileData

    suspend fun getProfileDetails(memberID : Int, context: Context){
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
//            Toast.makeText(context, response.code().toString(), Toast.LENGTH_SHORT).show()
        }catch (e:Exception){
            e.printStackTrace()
            _profileResponseCode.emit(400)
            Log.d("Profile", e.message.toString())
        }
    }

    private val _rules = MutableStateFlow("wait")
    val rules : StateFlow<String>
        get() = _rules

    suspend fun getRules() {
        val response = allApi.getRules()
        try {
            if (response.isSuccessful && response.body() != null) {
                _rules.emit(response.body()!![0].rule)
                Log.d("Rules", " ${response.code().toString()}")
            } else {
                _rules.emit("Error")
                Log.d("Rules", "${response.body().toString()} ${response.code()} ")
            }
        } catch (e : Exception) {
            _rules.emit("Error")
            e.printStackTrace()
        }
    }

    private val _policy = MutableStateFlow("wait")
    val policy : StateFlow<String>
        get() = _policy

    suspend fun getPolicy() {
        val response = allApi.getPolicy()
        try {
            if (response.isSuccessful && response.body() != null) {
                _policy.emit(response.body()!![0].policy)
                Log.d("_policy", " ${response.code()}")
            } else {
                _policy.emit("Error")
                Log.d("_policy", "${response.body().toString()} ${response.code()} ")
            }
        } catch (e : Exception) {
            _policy.emit("Error")
            e.printStackTrace()
        }
    }

    private val _declaration = MutableStateFlow("wait")
    val declaration : StateFlow<String>
        get() = _declaration

    suspend fun getDeclaration(){
        try {
            val response = allApi.getDeclaration()
            if (response.isSuccessful && response.body() != null) {
                _declaration.emit(response.body()!![0].declearation)
                Log.d("RulesDeclaration", " ${response.code().toString()}")
            } else {
                _declaration.emit("Error")
                Log.d("RulesDeclaration", "${response.body().toString()} ${response.code()} ")
            }
        } catch (e : Exception) {
            _declaration.emit("Error")
            e.printStackTrace()
        }
    }

    private val _referenceCode = MutableStateFlow(0)
    val referenceCode : StateFlow<Int>
        get() = _referenceCode

    suspend fun checkReferenceCode(referenceID: String, context: Context){
        _referenceCode.emit(0)
        try {
            val response = allApi.checkReferenceCode(referenceID)
            if (response.isSuccessful && response.body() != null) {
                _referenceCode.emit(response.code())
//                Toasty.success(context, "Verified", Toast.LENGTH_SHORT).show()
                Log.d("Reference Code", "${response.body().toString()} ${response.code().toString()}")
            } else {
                _referenceCode.emit(response.code())
                Toasty.error(context, "Wrong Reference ID", Toast.LENGTH_SHORT).show()
                Log.d("Reference Code", "${response.body().toString()} ${response.code()} ")
            }
        } catch (e : Exception) {
            _referenceCode.emit(400)
            Toasty.error(context, "Something Went Wrong", Toast.LENGTH_SHORT).show()
            Log.d("Reference Code", e.message.toString())
            e.printStackTrace()
        }
    }

    private val _otpVerification = MutableStateFlow("wait")
    val otpVerification : StateFlow<String>
        get() = _otpVerification
    suspend fun verifyOtp(via : String, otp : String, context: Context) {
        try {
        val response = allApi.verifyOtp(
            RequestBody.create("multipart/form-data".toMediaTypeOrNull(), via),
            RequestBody.create("multipart/form-data".toMediaTypeOrNull(), otp),
        )
            if (response.isSuccessful) {
                _otpVerification.emit(response.body()!!.message)
                Toasty.success(context, "Verified", Toast.LENGTH_SHORT).show()
                Log.d("Rules", "${response.body().toString()} ${response.code()}")
            } else {
                Toasty.error(context, "Enter valid otp", Toast.LENGTH_SHORT).show()
                _otpVerification.emit("Error")
                Log.d("Rules", "${response.body().toString()} ${response.code()} ")
            }
        } catch (e : Exception) {
            Toasty.error(context, "Something went wrong", Toast.LENGTH_SHORT).show()
            _otpVerification.emit("Error")
            Log.d("OTP Error", e.message.toString())
        }
    }

    private val _sendOtp = MutableStateFlow("wait")
    val sendOtp : StateFlow<String>
        get() = _sendOtp
    suspend fun sendOtp(via : String, context: Context) {
        try {
            val response = allApi.sendOtp(
                RequestBody.create("multipart/form-data".toMediaTypeOrNull(), via)
            )
            if (response.isSuccessful) {
                _sendOtp.emit(response.body()!!.message)
                Toasty.success(context, "OTP sent", Toast.LENGTH_SHORT).show()
                Log.d("Send OTP", "${response.body().toString()} ${response.code()}")
            } else {
                if (response.code() == 406) {
                    Toasty.error(context, "Number already Exist", Toast.LENGTH_SHORT).show()
                    _sendOtp.emit("Error")
                    Log.d("Send OTP", "${response.body().toString()} ${response.code()} ")
                } else {
                    _sendOtp.emit("Error")
                    Log.d("Send OTP", "${response.body().toString()} ${response.code()} ")
                    Toasty.error(context, "Wrong Number", Toast.LENGTH_SHORT).show()
                }
            }
        } catch (e : Exception) {
            Toasty.error(context, "Something went wrong", Toast.LENGTH_SHORT).show()
            _sendOtp.emit("Error Catch")
            Log.d("OTP Error", e.message.toString())
            e.printStackTrace()
        }
    }

    private val _supportResponse = MutableStateFlow(0)
    val supportResponse : StateFlow<Int>
        get() = _supportResponse

    suspend fun support(message : String, contact : String, name : String, context: Context) {
        try {
            val response = allApi.support(
                RequestBody.create("multipart/form-data".toMediaTypeOrNull(), message),
                RequestBody.create("multipart/form-data".toMediaTypeOrNull(), contact),
                RequestBody.create("multipart/form-data".toMediaTypeOrNull(), name)
            )
            if (response.isSuccessful) {
                _supportResponse.emit(response.code())
                Toasty.success(context, "Query Sent", Toast.LENGTH_SHORT).show()
                Log.d("supportResponse", "${response.body().toString()} ${response.code()}")
            } else {
                _supportResponse.emit(response.code())
                Log.d("supportResponse", "${response.body().toString()} ${response.code()} ")
                Toasty.error(context, "Try Again", Toast.LENGTH_SHORT).show()
            }
        } catch (e : Exception) {
            Toasty.error(context, "Something went wrong", Toast.LENGTH_SHORT).show()
            _supportResponse.emit(400)
            Log.d("supportResponse", e.message.toString())
            e.printStackTrace()
        }
    }

    private val _postalData = MutableStateFlow(PostalData("", emptyList(),""))
    val postalData : StateFlow<PostalData>
        get() = _postalData

    private val BASE_URL = "https://api.postalpincode.in/"
    private val api: ApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
    suspend fun getPostalData(postalCode : String) {
        try {
            val response = api.getPostalDetails(postalCode)
            if (response.isSuccessful) {
                if (response.body()!![0].PostOffice != null) {
                    _postalData.emit(response.body()!![0])
                }
                Log.d("Declaration", "${response.body().toString()} ${response.code().toString()}")
            } else {
                _postalData.emit(PostalData("No records found", emptyList(), "Error"))
                Log.d("Postal", "${response.body().toString()} ${response.code().toString()}")
            }
        }catch (e : Exception){
            _postalData.emit(PostalData("No records found", emptyList(), "Error"))
            e.printStackTrace()
        }
    }

}