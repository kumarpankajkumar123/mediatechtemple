package app.mtt.aggrabandhu.api

import app.mtt.aggrabandhu.authentication.onboarding.ProfessionData
import retrofit2.Response
import retrofit2.http.GET


interface AllApi {

    @GET("profession")
    suspend fun getProfession() : Response<List<ProfessionData>>

}