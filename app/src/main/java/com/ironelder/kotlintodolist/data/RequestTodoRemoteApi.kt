package com.ironelder.kotlintodolist.data

import com.ironelder.kotlintodolist.BuildConfig
import com.ironelder.kotlintodolist.common.CLIENT_TIMEOUT
import com.ironelder.kotlintodolist.common.REMOTE_TODO_URL
import com.ironelder.kotlintodolist.common.USER_NAME
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RequestTodoRemoteApi {
    val todoRemoteApi : TodoRemoteApi by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(REMOTE_TODO_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(mOkHttpClient)
            .build()

        return@lazy retrofit.create(TodoRemoteApi::class.java)
    }

    private val mOkHttpClient:OkHttpClient by lazy {
        val httpClient = OkHttpClient.Builder().apply {
            connectTimeout(CLIENT_TIMEOUT, TimeUnit.SECONDS)
            readTimeout(CLIENT_TIMEOUT, TimeUnit.SECONDS)
            addNetworkInterceptor(getLogInterceptor())
            addInterceptor(getTodoInterceptor())
        }
        return@lazy httpClient.build()
    }

    private fun getLogInterceptor():HttpLoggingInterceptor{
        return HttpLoggingInterceptor().apply{
            level = if(BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
        }
    }

    private fun getTodoInterceptor():Interceptor{
        return Interceptor {chain ->
            val original = chain.request()
            val requestBuilder = original.newBuilder()
                .header("X-Cardoc-User", USER_NAME)
            val request = requestBuilder.build()
            chain.proceed(request)
        }
    }
}