package extension.domain.githubreposearch.di

import androidx.paging.ExperimentalPagingApi
import com.google.gson.GsonBuilder
import com.haroldadmin.cnradapter.NetworkResponseAdapterFactory
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import extension.domain.githubreposearch.data.network.APIService
import extension.domain.githubreposearch.data.network.DateDeserializer
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@ExperimentalPagingApi
@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {
    @Singleton
    @Provides
    fun provideClient(): OkHttpClient {
        val httpBuilder = OkHttpClient.Builder()
        httpBuilder
            .connectTimeout(20, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .addInterceptor {
                it.proceed(
                    it.request().newBuilder().addHeader("Content-Type", "application/json").build()
                )
            }
        return httpBuilder.build()
    }

    @Singleton
    @Provides
    fun provideGsonConverterFactory(): GsonConverterFactory {
        val gson = GsonBuilder()
            .setDateFormat(DateDeserializer.DATE_FORMAT)
            .registerTypeAdapter(Date::class.java, DateDeserializer())
            .excludeFieldsWithoutExposeAnnotation()
            .create()
        return GsonConverterFactory.create(gson)
    }


    @Singleton
    @Provides
    fun provideAPIService(
        client: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): APIService {
        return Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(gsonConverterFactory)
            .addCallAdapterFactory(NetworkResponseAdapterFactory())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(client)
            .build()
            .create(APIService::class.java)
    }
}