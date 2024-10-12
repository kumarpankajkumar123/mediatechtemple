package app.mtt.aggrabandhu.di

import app.mtt.aggrabandhu.api.AllApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

const val baseUrl1 = "https://agerbandhu-production.up.railway.app"
const val baseUrl = "https://backend.aggrabandhuss.org"

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Singleton
    @Provides
    fun providesRetrofit() : Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://backend.aggrabandhuss.org/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideAllAPI(retrofit: Retrofit) : AllApi {
        return retrofit.create(AllApi::class.java)
    }

}