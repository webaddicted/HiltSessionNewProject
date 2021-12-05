package com.webaddicted.hiltsession.utils.di

import android.content.Context
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.webaddicted.hiltsession.BuildConfig
import com.webaddicted.hiltsession.utils.apiutils.ApiServices
import com.webaddicted.hiltsession.utils.common.GlobalUtils
import com.webaddicted.hiltsession.utils.constant.ApiConstant
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    @Provides
    @Singleton
    fun getRetrofit(@ApplicationContext context: Context): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .addConverterFactory(GsonConverterFactory.create())
            .client(provideOkHttpClient(context))
            .build()
    }

    private fun provideOkHttpClient(context: Context): OkHttpClient {
        val okHttpClientBuilder = OkHttpClient.Builder()
        okHttpClientBuilder.connectTimeout(ApiConstant.API_TIME_OUT, TimeUnit.SECONDS)
        okHttpClientBuilder.readTimeout(ApiConstant.API_TIME_OUT, TimeUnit.SECONDS)
        okHttpClientBuilder.writeTimeout(ApiConstant.API_TIME_OUT, TimeUnit.SECONDS)

        val interceptor = HttpLoggingInterceptor()
//        TODO print response
        interceptor.level = HttpLoggingInterceptor.Level.BODY
//        okHttpClientBuilder.addNetworkInterceptor(StethoInterceptor())
//        if (BuildConfig.DEBUG)interceptor.level = HttpLoggingInterceptor.Level.BODY
//        if (BuildConfig.DEBUG)okHttpClientBuilder.addNetworkInterceptor(StethoInterceptor())
        okHttpClientBuilder.addInterceptor(interceptor)
        okHttpClientBuilder.addInterceptor(TokenInterceptor())
        val authenticator = TokenAuthenticator(context)
        okHttpClientBuilder.authenticator(authenticator)
        return okHttpClientBuilder.build()
    }

    class TokenAuthenticator(private val mContext: Context) : Authenticator {

        override fun authenticate(route: Route?, response: Response): Request {
            val newAccessToken = GlobalUtils.refreshAuthToken(mContext)

            return response.request.newBuilder()
                .header("Authorization", "OAuth $newAccessToken")
                .build()
        }
    }

    class TokenInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val newRequest = chain.request().newBuilder()
                .addHeader("Authorization", "OAuth token")
                .build()
            return chain.proceed(newRequest)
        }
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): ApiServices =
        retrofit.create(ApiServices::class.java)

}