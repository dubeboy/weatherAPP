package za.co.dubedivine.dvtweatherapp.rest

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import za.co.dubedivine.dvtweatherapp.BuildConfig
import java.util.concurrent.TimeUnit

// singleton class
object RetrofitRESTClient {

    private const val BASE_URL = "https://openweathermap.org/"

    fun getClient(): RetrofitWeatherInterface {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        val client = makeOkHttpClient()
        val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
        return retrofit.create(RetrofitWeatherInterface::class.java)
    }

    private fun makeOkHttpClient(): OkHttpClient {

        val httpClientBuilder = OkHttpClient.Builder()
        // for slow networks
        httpClientBuilder.readTimeout(60, TimeUnit.SECONDS)
        httpClientBuilder.connectTimeout(60, TimeUnit.SECONDS)


        // don`t log in production please
        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            httpClientBuilder.addInterceptor(loggingInterceptor)
        }

        return httpClientBuilder.build()
    }
}