package uk.co.pdextech.android.domain

import uk.co.pdextech.android.domain.Forecast
import uk.co.pdextech.android.model.ServerForecast
import uk.co.pdextech.android.model.ServerForecastResult
import java.text.DateFormat
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * Created by Pdex on 12/02/2018.
 */
class ServerDataMapper {

    fun convertFromDataModel(zipCode: Long, forecast: ServerForecastResult): ForecastList {
        return ForecastList(zipCode, forecast.city.name, forecast.city.country, convertForecastListToDomain(forecast.list))
    }

    private fun convertForecastListToDomain(list: List<ServerForecast>): List<Forecast> {
        return list.mapIndexed { index, forecast ->
            val date = Calendar.getInstance().timeInMillis + TimeUnit.DAYS.toMillis(index.toLong())
            converForcastItemToDomain(forecast.copy(dt = date))
        }
    }

    private fun converForcastItemToDomain(forecast: ServerForecast): Forecast {
        return Forecast(
                -1,
                forecast.dt,
                forecast.weather[0].description,
                forecast.temp.max.toInt(),
                forecast.temp.min.toInt(),
                generateIconUrl(forecast.weather[0].icon)
        )
    }

    private fun generateIconUrl(iconCode: String): String {
        return "http://openweathermap.org/img/w/$iconCode.png"
    }
}