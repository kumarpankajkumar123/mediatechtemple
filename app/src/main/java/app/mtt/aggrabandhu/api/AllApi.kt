package app.mtt.aggrabandhu.api

import app.mtt.aggrabandhu.authentication.onboarding.DocValidationResponse
import app.mtt.aggrabandhu.authentication.onboarding.ProfessionData
import app.mtt.aggrabandhu.dashboard.pages.liveDonation.LiveDonationsData
import app.mtt.aggrabandhu.dashboard.sideNavigation.peopleReceivedDonations.ReceivedDonationsData
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
        file : Multipart
    ) : Response<DocValidationResponse>

    @GET("donationreceive")
    suspend fun liveDonations() : Response<LiveDonationsData>

    @GET("donation")
    suspend fun receivedDonations() : Response<ReceivedDonationsData>

}