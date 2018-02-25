package uk.co.pdextech.android.weatherapp

import android.util.Log
import com.google.gson.Gson
import uk.co.pdextech.android.model.ServerForecastResult
import java.net.URL


/**
 * Created by Pdex on 12/02/2018.
 */
class Request(val zipCode: Long) {

    companion object {
        private val APP_ID = "15646a06818f61f7b8d7823ca833e1ce"
        private val URL = "http://api.openweathermap.org/data/2.5/" +
                "forecast/daily?mode=json&units=metric&cnt=7"
        private val COMPLETE_URL = "$URL&APPID=$APP_ID&q="
    }

    fun execute(): ServerForecastResult {
        val forecastJSONString = URL(COMPLETE_URL + zipCode.toString()).readText()
        Log.d(javaClass.simpleName, forecastJSONString)

        return Gson().fromJson(forecastJSONString, ServerForecastResult::class.java)
    }
}