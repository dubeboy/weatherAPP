package za.co.dubedivine.dvtweatherapp.rest

import retrofit2.Call
import retrofit2.http.GET
import za.co.dubedivine.dvtweatherapp.model.CurrentWeather
import za.co.dubedivine.dvtweatherapp.model.Forecast

interface RetrofitWeatherInterface {

//    api.openweathermap.org/data/2.5/forecast?q=London,us&mode=xml
//    api.openweathermap.org/data/2.5/forecast?id={city ID}
//    62784b0751250bd346d2890db891c6e6

    //    api.openweathermap.org/data/2.5/weather?q=London,uk&APPID=62784b0751250bd346d2890db891c6e6

    @GET("forecast?cnt=5")
    fun getFiveDayWeatherForecast(): Call<Forecast>

    @GET("weather")
    fun getCurrent(): Call<CurrentWeather>

}