package app.mtt.aggrabandhu.api

import app.mtt.aggrabandhu.authentication.onboarding.DocValidationResponse
import app.mtt.aggrabandhu.authentication.onboarding.ProfessionData
import app.mtt.aggrabandhu.dashboard.pages.liveDonation.LiveDonationsData
import app.mtt.aggrabandhu.dashboard.sideNavigation.allMembers.AllMembersData
import app.mtt.aggrabandhu.dashboard.sideNavigation.peopleReceivedDonations.ReceivedDonationsData
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST

interface AllApi {

    @GET("profession")
    suspend fun getProfession() : Response<List<ProfessionData>>

    @GET("gotra")
    suspend fun getGotra() : Response<List<ProfessionData>>

    @Multipart
    @POST("validate-image")
    suspend fun validateDocuments(
        number : Int,
        type_id : String,
        file : MultipartBody
    ) : Response<DocValidationResponse>

    @GET("donationreceive")
    suspend fun liveDonations() : Response<LiveDonationsData>

    @GET("donation")
    suspend fun receivedDonations() : Response<ReceivedDonationsData>

    @GET("member")
    suspend fun getAllMembers() : Response<AllMembersData>

}