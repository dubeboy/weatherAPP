package za.co.dubedivine.dvtweatherapp.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class Rain {

    @SerializedName("3h")
    @Expose
    var threeHour:Double? = null

}
