package uk.co.pdextech.android.weatherapp

import uk.co.pdextech.android.domain.Command
import uk.co.pdextech.android.domain.Forecast

/**
 * Created by Pdex on 15/02/2018.
 */
class RequestDayForecastCommand(val id: Long,
                                val forecastProvider: ForecastProvider = ForecastProvider()): Command<Forecast> {
    override fun execute(): Forecast {
        return forecastProvider.requestForecast(id)
    }
}