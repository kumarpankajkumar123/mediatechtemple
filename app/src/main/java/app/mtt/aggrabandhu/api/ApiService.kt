package app.mtt.aggrabandhu.api

import app.mtt.aggrabandhu.authentication.onboarding.secondOnboarding.PostalData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("pincode/{pincode}")
    suspend fun getPostalDetails(
        @Path("pincode") pincode: String
    ): Response<List<PostalData>>
}