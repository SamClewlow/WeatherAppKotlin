package uk.co.pdextech.android.weatherapp

import android.util.Log
import uk.co.pdextech.android.domain.Forecast
import uk.co.pdextech.android.domain.ForecastList
import java.util.function.Predicate

/**
 * Created by Pdex on 14/02/2018.
 */
interface ForecastDataSource {
    fun requestForecastByZipCode(zipCode: Long, date: Long): ForecastList?
    fun requestDayForecast(id: Long): Forecast?
}



inline fun <T, R : Any> Iterable<T>.firstResult(predicate: (T) -> R?) : R {
    for (element in this) {
        val result = predicate(element)
        if (result != null) return result
    }

    throw NoSuchElementException("No element matching predicate was found")
}