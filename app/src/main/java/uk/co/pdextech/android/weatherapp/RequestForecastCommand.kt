package uk.co.pdextech.android.weatherapp

import uk.co.pdextech.android.domain.Command
import uk.co.pdextech.android.domain.ServerDataMapper
import uk.co.pdextech.android.domain.ForecastList

/**
 * Created by Pdex on 12/02/2018.
 */
class RequestForecastCommand(private val zipCode: Long,
                             val forecastProvider: ForecastProvider = ForecastProvider()) : Command<ForecastList> {

    companion object {
        val DAYS = 7
    }

    override fun execute(): ForecastList {
        return forecastProvider.requestByZipCode(zipCode, DAYS)
    }
}