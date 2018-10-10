package com.galaksi.cbc.data.source.remote

import android.content.Context
import android.support.annotation.VisibleForTesting
import android.util.Log
import com.galaksi.cbc.App
import com.galaksi.cbc.BuildConfig
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by rahmatrasyidi on 29/04/18.
 */

class RetrofitHelper constructor(context: Context) {

    fun provideRetrofit(): Retrofit {
        val clientBuilder = OkHttpClient.Builder()
                .addInterceptor(provideHttpLoggingInterceptor())
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)

        val gson = GsonBuilder().setLenient().serializeNulls().create()
        return Retrofit.Builder()
                .baseUrl(BuildConfig.API_BASE_URL)
                .client(clientBuilder.build())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }

    private fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger { message -> Log.d(HTTP_TAG, message) })
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.HEADERS
        return httpLoggingInterceptor
    }

    companion object {
        private val HTTP_TAG = "ApiLog"
        private var INSTANCE: RetrofitHelper? = null

        private val lock = Any()

        fun getInstance(): RetrofitHelper {
            synchronized(lock) {
                if (INSTANCE == null) {
                    INSTANCE = RetrofitHelper(App.INSTANCE)
                }
            }
            return INSTANCE!!
        }

        @VisibleForTesting
        fun destroyInstance() {
            INSTANCE = null
        }
    }
}