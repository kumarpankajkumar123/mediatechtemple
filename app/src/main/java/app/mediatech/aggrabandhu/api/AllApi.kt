package app.mediatech.aggrabandhu.api

import app.mediatech.aggrabandhu.authentication.login.LoginResponse
import app.mediatech.aggrabandhu.authentication.onboarding.DocValidationResponse
import app.mediatech.aggrabandhu.authentication.onboarding.firstOnboarding.ProfessionData
import app.mediatech.aggrabandhu.authentication.onboarding.ResponseData
import app.mediatech.aggrabandhu.authentication.onboarding.secondOnboarding.DeclarationData
import app.mediatech.aggrabandhu.authentication.onboarding.secondOnboarding.RulesData
import app.mediatech.aggrabandhu.authentication.onboarding.secondOnboarding.SignupResponse
import app.mediatech.aggrabandhu.dashboard.pages.home.NotificationData
import app.mediatech.aggrabandhu.dashboard.pages.liveDonation.LiveDonationsData
import app.mediatech.aggrabandhu.dashboard.pages.profile.ProfileData
import app.mediatech.aggrabandhu.dashboard.sideNavigation.allMembers.AllMemberData
import app.mediatech.aggrabandhu.dashboard.sideNavigation.allMembers.AllMembersData
import app.mediatech.aggrabandhu.dashboard.sideNavigation.myDonations.MyDonationData
import app.mediatech.aggrabandhu.dashboard.sideNavigation.peopleReceivedDonations.ReceivedDonationsData
import app.mediatech.aggrabandhu.dashboard.sideNavigation.policy.PolicyData
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Part
import retrofit2.http.Path
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
    ) : Response<ProfileData>

    @GET("donation/detail")
    suspend fun getDonationInfo(
        @Query("key") key : String,
        @Query("value") value : Int
    ) : Response<LiveDonationsData>

    @GET("donationreceive/activelist")
    suspend fun liveDonations() : Response<LiveDonationsData>

    @GET("donationreceive/endlist")
    suspend fun receivedDonations() : Response<LiveDonationsData>

    @Multipart
    @POST("member/login")
    suspend fun login(
        @Part("mobile_no") number : RequestBody,
        @Part("password") password : RequestBody,
    ) : Response<LoginResponse>

    @Multipart
    @POST("member")
    suspend fun signUp(
        @Part("name") name : RequestBody,
        @Part("gotra") gotra : RequestBody,
        @Part("father_name") father_name : RequestBody,
        @Part("mother_name") mother_name : RequestBody,
        @Part("dob") dob : RequestBody,
        @Part("email") email : RequestBody,
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
        @Part file : MultipartBody.Part, // Aadhar
        @Part file2 : MultipartBody.Part, // Pan/ID
        @Part profile : MultipartBody.Part, // Profile
        @Part("tahsil") tahsil : RequestBody,
        @Part("gender") gender : RequestBody,
        @Part("marriage_age") marriage_age : RequestBody,
        @Part("total_age") total_age : RequestBody,
        @Part("marriage_date") marriage_date : RequestBody,
        @Part("nominee") nominee : RequestBody,
        @Part("nominee2") nominee2 : RequestBody,
        @Part("relationship") relationship : RequestBody,
        @Part("relationship2") relationship2 : RequestBody,
        @Part("reference_id") reference_id : RequestBody,
        @Part("disease") disease : RequestBody, // True/False
        @Part("rulesAccepted") rulesAccepted : RequestBody,
        @Part("declaration") declaration : RequestBody,
        @Part diseaseFile : MultipartBody.Part, // Pan/ID
    ) : Response<SignupResponse>

    @Multipart
    @POST("member")
    suspend fun signUp(
        @Part("name") name : RequestBody,
        @Part("gotra") gotra : RequestBody,
        @Part("father_name") father_name : RequestBody,
        @Part("mother_name") mother_name : RequestBody,
        @Part("dob") dob : RequestBody,
        @Part("email") email : RequestBody,
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
        @Part file : MultipartBody.Part, // Aadhar
        @Part file2 : MultipartBody.Part, // Pan/ID
        @Part profile : MultipartBody.Part, // Profile
        @Part("tahsil") tahsil : RequestBody,
        @Part("gender") gender : RequestBody,
        @Part("marriage_age") marriage_age : RequestBody,
        @Part("total_age") total_age : RequestBody,
        @Part("marriage_date") marriage_date : RequestBody,
        @Part("nominee") nominee : RequestBody,
        @Part("nominee2") nominee2 : RequestBody,
        @Part("relationship") relationship : RequestBody,
        @Part("relationship2") relationship2 : RequestBody,
        @Part("reference_id") reference_id : RequestBody,
        @Part("disease") disease : RequestBody, // True/False
        @Part("rulesAccepted") rulesAccepted : RequestBody,
        @Part("declaration") declaration : RequestBody,
    ) : Response<SignupResponse>

    @Multipart
    @PUT("member/{memberID}")
    suspend fun editProfile(
        @Path("memberID") memberID: String,
        @Part("name") name : RequestBody,
        @Part("father_name") father_name : RequestBody,
        @Part("mother_name") mother_name : RequestBody,
        @Part("profession") profession : RequestBody,
        @Part("marital_status") marital_status : RequestBody,
        @Part("spouse_name") spouse_name : RequestBody,
        @Part("marriage_date") marriage_date : RequestBody,
        @Part("marriage_age") marriage_age : RequestBody,
        @Part("address") address : RequestBody,
        @Part("district") district : RequestBody,
        @Part("state") state : RequestBody,
        @Part("tahsil") tahsil : RequestBody,
        @Part("pincode") pincode : RequestBody,
        @Part("email") email : RequestBody,
        @Part profile : MultipartBody.Part, // Profile
        @Part("nominee") nominee : RequestBody,
        @Part("nominee2") nominee2 : RequestBody,
        @Part("relationship") relationship : RequestBody,
        @Part("relationship2") relationship2 : RequestBody
    ) : Response<SignupResponse>

    @Multipart
    @PUT("member/{memberID}")
    suspend fun editProfileWithoutImage(
        @Path("memberID") memberID: String,
        @Part("name") name : RequestBody,
        @Part("father_name") father_name : RequestBody,
        @Part("mother_name") mother_name : RequestBody,
        @Part("profession") profession : RequestBody,
        @Part("marital_status") marital_status : RequestBody,
        @Part("spouse_name") spouse_name : RequestBody,
        @Part("marriage_date") marriage_date : RequestBody,
        @Part("marriage_age") marriage_age : RequestBody,
        @Part("address") address : RequestBody,
        @Part("district") district : RequestBody,
        @Part("state") state : RequestBody,
        @Part("tahsil") tahsil : RequestBody,
        @Part("pincode") pincode : RequestBody,
        @Part("email") email : RequestBody,
        @Part("nominee") nominee : RequestBody,
        @Part("nominee2") nominee2 : RequestBody,
        @Part("relationship") relationship : RequestBody,
        @Part("relationship2") relationship2 : RequestBody
    ) : Response<SignupResponse>

    @GET("member")
    suspend fun getAllMembers() : Response<AllMembersData>

    @GET("notification")
    suspend fun getNotification() : Response<NotificationData>

    @GET("rule")
    suspend fun getRules() : Response<List<RulesData>>

    @GET("policy")
    suspend fun getPolicy() : Response<List<PolicyData>>

    @GET("declearation")
    suspend fun getDeclaration() : Response<List<DeclarationData>>

    @GET("donation/mydonation/{memberID}")
    suspend fun myDonations(
        @Path("memberID") memberID : String
    ) : Response<MyDonationData>

    @GET("member/check_referal/{reference_id}")
    suspend fun checkReferenceCode(
        @Path("reference_id") referenceId : String
    ) : Response<String>

    @Multipart
    @POST("member/otp")
    suspend fun sendOtp(
        @Part("via") via : RequestBody
    ) : Response<ResponseData>

    @Multipart
    @POST("member/verifyotp")
    suspend fun verifyOtp(
        @Part("via") via : RequestBody,
        @Part("otp") otp : RequestBody,
    ) : Response<ResponseData>

    @Multipart
    @POST("support")
    suspend fun support(
        @Part("message") message : RequestBody,
        @Part("contact") contact : RequestBody,
        @Part("name") name : RequestBody,
    ) : Response<ResponseData>

    @Multipart
    @POST("donation")
    suspend fun makeDonation(
        @Part("member_id") member_id : RequestBody,
        @Part("donation_id") donation_id : RequestBody,
        @Part("amount") amount : RequestBody,
        @Part file : MultipartBody.Part,
        @Part("transaction_id") transaction_id : RequestBody,
        @Part("donation_date") donation_date : RequestBody,
        @Part("payment_method") payment_method : RequestBody,
    ) : Response<ResponseData>

    @GET("member/referal/{memberID}")
    suspend fun getViewJoined(
        @Path("memberID") memberId : String
    ) : Response<List<AllMemberData>>

}