package app.mtt.aggrabandhu.api

import app.mtt.aggrabandhu.authentication.onboarding.DocValidationResponse
import app.mtt.aggrabandhu.authentication.onboarding.firstOnboarding.ProfessionData
import app.mtt.aggrabandhu.authentication.onboarding.ResponseData
import app.mtt.aggrabandhu.authentication.onboarding.secondOnboarding.SignupResponse
import app.mtt.aggrabandhu.dashboard.pages.liveDonation.LiveDonationsData
import app.mtt.aggrabandhu.dashboard.pages.profile.ProfileData
import app.mtt.aggrabandhu.dashboard.sideNavigation.allMembers.AllMembersData
import app.mtt.aggrabandhu.dashboard.sideNavigation.peopleReceivedDonations.ReceivedDonationsData
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface AllApi {

    @GET("profession")
    suspend fun getProfession() : Response<List<ProfessionData>>

    @GET("gotra")
    suspend fun getGotra() : Response<List<ProfessionData>>

    @Multipart
    @POST("validate-image")
    suspend fun validateDocuments(
        @Part file : MultipartBody.Part,
        @Part("number") number : RequestBody,
        @Part("type_id") type_id : RequestBody,
    ) : Response<DocValidationResponse>

    @GET("member/detail")
    suspend fun getProfileInfo(
        @Query("key") key : String,
        @Query("value") value : Int
    ) : Response<List<ProfileData>>

    @GET("donation/detail")
    suspend fun getDonationInfo(
        @Query("key") key : String,
        @Query("value") value : Int
    ) : Response<LiveDonationsData>

    @GET("donationreceive")
    suspend fun liveDonations() : Response<LiveDonationsData>

    @GET("donation")
    suspend fun receivedDonations() : Response<ReceivedDonationsData>

    @Multipart
    @POST("member")
    suspend fun signUp(
        @Part("reference_id") reference_id : RequestBody,
        @Part("gotra") gotra : RequestBody,
        @Part("name") name : RequestBody,
        @Part("father_name") father_name : RequestBody,
        @Part("mother_name") mother_name : RequestBody,
        @Part("dob") dob : RequestBody,
        @Part("password") password : RequestBody,
        @Part("marital_status") marital_status : RequestBody,
        @Part("spouse_name") spouse_name : RequestBody,
        @Part("mobile_no") mobile_no : RequestBody,
        @Part("address") address : RequestBody,
        @Part("district") district : RequestBody,
        @Part("state") state : RequestBody,
        @Part("pincode") pincode : RequestBody,
        @Part("profession") profession : RequestBody,
        @Part("aadhar_no") aadhar_no : RequestBody,
        @Part("id_type") id_type : RequestBody,
        @Part("id_no") id_no : RequestBody,
        @Part("nominee") nominee : RequestBody,
        @Part("relationship") relationship : RequestBody,
        @Part("nominee2") nominee2 : RequestBody,
        @Part("relationship2") relationship2 : RequestBody,
        @Part("disease") disease : RequestBody, // True/False
        @Part("rulesAccepted") rulesAccepted : RequestBody, // True/False
        @Part file : MultipartBody.Part, // Aadhar
        @Part file2 : MultipartBody.Part, // Pan/ID
        @Part profile : MultipartBody.Part, // Pan/ID
        @Part diseaseFile : MultipartBody.Part, // Pan/ID
    ) : Response<SignupResponse>

    @Multipart
    @POST("member")
    suspend fun signUpWithoutDisease(
        @Part("name") name : RequestBody,
        @Part("gotra") gotra : RequestBody,
        @Part("father_name") father_name : RequestBody,
        @Part("mother_name") mother_name : RequestBody,
        @Part("dob") dob : RequestBody,
        @Part("email") email : RequestBody,
        @Part("password") password : RequestBody,
        @Part("marital_status") marital_status : RequestBody,
        @Part("mobile_no") mobile_no : RequestBody,
        @Part("address") address : RequestBody,
        @Part("district") district : RequestBody,
        @Part("state") state : RequestBody,
        @Part("pincode") pincode : RequestBody,
        @Part("profession") profession : RequestBody,
        @Part("aadhar_no") aadhar_no : RequestBody,
        @Part("id_type") id_type : RequestBody,
        @Part("id_no") id_no : RequestBody,
        @Part file : MultipartBody.Part, // Aadhar
        @Part file2 : MultipartBody.Part, // Pan/ID
        @Part profile : MultipartBody.Part, // Profile
        @Part("nominee") nominee : RequestBody,
        @Part("nominee2") nominee2 : RequestBody,
        @Part("relationship") relationship : RequestBody,
        @Part("relationship2") relationship2 : RequestBody,
        @Part("reference_id") reference_id : RequestBody,
        @Part("rulesAccepted") rulesAccepted : RequestBody,
    ) : Response<SignupResponse>

    @GET("member")
    suspend fun getAllMembers() : Response<AllMembersData>

}