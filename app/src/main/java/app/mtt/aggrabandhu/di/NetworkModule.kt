package app.mtt.aggrabandhu.di

import app.mtt.aggrabandhu.api.AllApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

const val baseUrl1 = "https://agerbandhu-production.up.railway.app"
const val baseUrl = "https://backend.aggrabandhuss.org"

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    private val client = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)  // Increase connection timeout
        .readTimeout(30, TimeUnit.SECONDS)     // Increase read timeout
        .writeTimeout(30, TimeUnit.SECONDS)    // Increase write timeout
        .build()

    @Singleton
    @Provides
    fun providesRetrofit() : Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://backend.aggrabandhuss.org/api/")
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideAllAPI(retrofit: Retrofit) : AllApi {
        return retrofit.create(AllApi::class.java)
    }

}