package uk.co.pdextech.android.weatherapp

import android.util.Log
import uk.co.pdextech.android.domain.Forecast
import uk.co.pdextech.android.domain.ForecastList

/**
 * Created by Pdex on 15/02/2018.
 */
class ForecastProvider(val sources: List<ForecastDataSource> = ForecastProvider.SOURCES) {

    companion object {
        val DAY_IN_MILLIS = 1000 * 60 * 60 * 24
        val SOURCES by lazy { listOf<ForecastDataSource>(ForecastDb(), ForecastServer()) }
    }

    fun requestByZipCode(zipCode: Long, days: Int): ForecastList {
        return requestToSources {
            val result = it.requestForecastByZipCode(zipCode, todayTimeSpan())
            if (result != null && result.size >= days) result else null
        }
    }

    fun requestForecast(id: Long): Forecast {
        return requestToSources { it.requestDayForecast(id) }
    }

//    private fun requestSource(source: ForecastDataSource, days: Int, zipCode: Long): ForecastList? {
//        val result = source.requestForecastByZipCode(zipCode, todayTimeSpan())
//        Log.d("SAM", "${result}")
//        return if (result != null && result.size >= days) result else null
//    }

    private fun <T : Any> requestToSources(f: (ForecastDataSource) -> T?): T {
        return sources.firstResult { f(it) }
    }

    private fun todayTimeSpan(): Long {
        return System.currentTimeMillis() / DAY_IN_MILLIS * DAY_IN_MILLIS
    }
}