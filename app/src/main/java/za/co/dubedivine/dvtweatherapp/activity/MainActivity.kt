package za.co.dubedivine.dvtweatherapp.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.IntRange
import android.support.v7.content.res.AppCompatResources
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import za.co.dubedivine.dvtweatherapp.R
import za.co.dubedivine.dvtweatherapp.model.CurrentWeather
import za.co.dubedivine.dvtweatherapp.model.Forecast
import za.co.dubedivine.dvtweatherapp.rest.RetrofitRESTClient
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity() {


    private val degreeChar = '\u00B0'

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        getWeatherData()
        getFiveDayForcast()

    }

    fun getFiveDayForcast() {
        RetrofitRESTClient.getClient().getFiveDayWeatherForecast().enqueue(object : Callback<Forecast?> {
            override fun onFailure(call: Call<Forecast?>, t: Throwable) {
                t.printStackTrace()
                showToast("Failed to weather data. Please check your internet connection")
                progress_bar.visibility = View.GONE
            }

            override fun onResponse(call: Call<Forecast?>, response: Response<Forecast?>) {
                if (response.body() != null) {
                    val dayOneTemp = response.body()?.list?.get(0)
                    val dayTwoTemp = response.body()?.list?.get(1)
                    val dayThreeTemp = response.body()?.list?.get(2)
                    val dayFourTemp = response.body()?.list?.get(3)
                    val dayFiveTemp = response.body()?.list?.get(4)

                    tv_day_1_deg.text = dayOneTemp?.main?.temp?.roundToInt()?.toString() + degreeChar ?: "0"
                    tv_day_2_deg.text = dayTwoTemp?.main?.temp?.roundToInt()?.toString() + degreeChar ?: "0"
                    tv_day_3_deg.text = dayThreeTemp?.main?.temp?.roundToInt()?.toString() + degreeChar ?: "0"
                    tv_day_4_deg.text = dayFourTemp?.main?.temp?.roundToInt()?.toString() + degreeChar ?: "0"
                    tv_day_5_deg.text = dayFiveTemp?.main?.temp?.roundToInt()?.toString() + degreeChar ?: "0"

                    // set days of the week days

                    tv_day_1.text = getDayOfWeekFromNow(1)
                    tv_day_2.text = getDayOfWeekFromNow(2)
                    tv_day_3.text = getDayOfWeekFromNow(3)
                    tv_day_4.text = getDayOfWeekFromNow(4)
                    tv_day_5.text = getDayOfWeekFromNow(5)

                    //set the images please
                    if (dayOneTemp?.weather?.get(0)?.main?.contains("rain", true) == true) {

                        img_day_1.setImageDrawable(AppCompatResources.getDrawable(this@MainActivity, R.drawable.rain_2x))
                        img_day_2.setImageDrawable(AppCompatResources.getDrawable(this@MainActivity, R.drawable.rain_2x))
                        img_day_3.setImageDrawable(AppCompatResources.getDrawable(this@MainActivity, R.drawable.rain_2x))
                        img_day_4.setImageDrawable(AppCompatResources.getDrawable(this@MainActivity, R.drawable.rain_2x))
                        img_day_5.setImageDrawable(AppCompatResources.getDrawable(this@MainActivity, R.drawable.rain_2x))

                    } else if (dayOneTemp?.weather?.get(0)?.main?.contains("cloud", true) == true) {

                        img_day_1.setImageDrawable(AppCompatResources.getDrawable(this@MainActivity, R.drawable.partlysunny_2x))
                        img_day_2.setImageDrawable(AppCompatResources.getDrawable(this@MainActivity, R.drawable.partlysunny_2x))
                        img_day_3.setImageDrawable(AppCompatResources.getDrawable(this@MainActivity, R.drawable.partlysunny_2x))
                        img_day_4.setImageDrawable(AppCompatResources.getDrawable(this@MainActivity, R.drawable.partlysunny_2x))
                        img_day_5.setImageDrawable(AppCompatResources.getDrawable(this@MainActivity, R.drawable.partlysunny_2x))
                    } else if (dayOneTemp?.weather?.get(0)?.main?.contains("clear", true) == true) {

                        img_day_1.setImageDrawable(AppCompatResources.getDrawable(this@MainActivity, R.drawable.clear_2x))
                        img_day_2.setImageDrawable(AppCompatResources.getDrawable(this@MainActivity, R.drawable.clear_2x))
                        img_day_3.setImageDrawable(AppCompatResources.getDrawable(this@MainActivity, R.drawable.clear_2x))
                        img_day_4.setImageDrawable(AppCompatResources.getDrawable(this@MainActivity, R.drawable.clear_2x))
                        img_day_5.setImageDrawable(AppCompatResources.getDrawable(this@MainActivity, R.drawable.clear_2x))
                    }
                } else {
                    showToast("Oops Something went terribly wrong, please try again")
                }
                progress_bar.visibility = View.GONE
            }
        })
    }

   private fun showToast(msg: String) {
        Toast.makeText(this@MainActivity,
                msg, Toast.LENGTH_LONG)
                .show()
    }

    // showing off a lil ;)
    private fun getDayOfWeekFromNow(@IntRange(from = 1, to = 5) daysFromNow: Int): String {
        val instance = Calendar.getInstance()
         val date = Date()
        instance.time = date
        val simpleDateFormat = SimpleDateFormat("E", Locale.getDefault())
        instance.add(Calendar.DATE, daysFromNow)
        return simpleDateFormat.format(instance.time)
    }


    private fun getWeatherData() {
        RetrofitRESTClient.getClient().getCurrent().enqueue(object : Callback<CurrentWeather?> {
            override fun onFailure(call: Call<CurrentWeather?>, t: Throwable) {
                t.printStackTrace()
                showToast("Failed to weather data. Please check your internet connection")
                progress_bar.visibility = View.GONE
            }

            override fun onResponse(call: Call<CurrentWeather?>, response: Response<CurrentWeather?>) {
                val body = response.body()
                if (body != null) {

                    // the chances of the elvis operator being hit are really small, but this way we
                    // are guaranteed the app will not crash and the user will see that something is definitely wrong

                    tv_current_deg.text = body.main?.temp?.roundToInt()?.toString() + degreeChar ?: "0"
                    tv_current.text = body.main?.temp?.roundToInt()?.toString() + degreeChar ?: "0"
                    tv_min.text = body.main?.tempMin?.roundToInt()?.toString() + degreeChar ?: "0"
                    tv_max.text = body.main?.tempMax?.roundToInt()?.toString() + degreeChar ?: "0"

                    val weatherForecast = body.weather?.get(0)?.main ?: "Clear"

                    // I am not sure if the response will be rainy or rain cloudy or cloud
                    if (weatherForecast.contains("rain", true)) {
                        img_bg.setImageDrawable(AppCompatResources
                                .getDrawable(
                                        this@MainActivity,
                                        R.drawable.forest_rainy))
                    } else if (weatherForecast.contains("cloud", true)) {
                        img_bg.setImageDrawable(AppCompatResources
                                .getDrawable(
                                        this@MainActivity,
                                        R.drawable.forest_cloudy))

                    } else if (weatherForecast.contains("clear", true)) {
                        img_bg.setImageDrawable(AppCompatResources
                                .getDrawable(
                                        this@MainActivity,
                                        R.drawable.forest_sunny))
                    }
                } else {
                    showToast("Oops an error happened please try again")
                }
                progress_bar.visibility = View.GONE
            }
        })

    }
}
