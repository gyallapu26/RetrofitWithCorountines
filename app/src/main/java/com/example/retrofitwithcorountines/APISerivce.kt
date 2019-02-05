package com.example.retrofitwithcorountines

import com.example.entities.CurrentWheatherResponse
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import kotlinx.coroutines.Deferred
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


const val API_KEY = "89e8bd89085b41b7a4b142029180210"

interface APISerivce {


    @GET("current.json")
    fun getCurrentWheather(
        @Query("q")location : String,
        @Query("lang") lan : String = "en")
            : Deferred<CurrentWheatherResponse>




    companion object {

        lateinit var apiSerivce : APISerivce




        val requestInterceptor : Interceptor = Interceptor {
            val url = it.request()
                .url()
                .newBuilder()
                .addQueryParameter("key", API_KEY)
                .build()
            val request = it.request()
                .newBuilder()
                .url(url)
                .build()

            return@Interceptor it.proceed(request)
        }


        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(requestInterceptor)
            .addInterceptor(getLOggingInterceptor())
            //.addInterceptor(connectivityInterceptor)
            .build()

        private fun getLOggingInterceptor(): Interceptor {
             var loggingInterceptor =  HttpLoggingInterceptor()
            loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
            return  loggingInterceptor

        }








        fun  getInstance() : APISerivce {

            if (!::apiSerivce.isInitialized){
               apiSerivce = Retrofit
                   .Builder()
                   .client(okHttpClient)
                   .baseUrl("https://api.apixu.com/v1/")
                   .addCallAdapterFactory(CoroutineCallAdapterFactory())
                   .addConverterFactory(GsonConverterFactory.create())
                   .build()
                   .create(APISerivce::class.java)
            }
            return  apiSerivce
        }

    }
}