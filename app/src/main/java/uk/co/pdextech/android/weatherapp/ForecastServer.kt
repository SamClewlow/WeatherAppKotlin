package uk.co.pdextech.android.weatherapp

import uk.co.pdextech.android.domain.Forecast
import uk.co.pdextech.android.domain.ForecastList
import uk.co.pdextech.android.domain.ServerDataMapper

/**
 * Created by Pdex on 14/02/2018.
 */

class ForecastServer(val dataMapper: ServerDataMapper = ServerDataMapper(),
                     val forecastDb: ForecastDb = ForecastDb()): ForecastDataSource {

    override fun requestDayForecast(id: Long): Forecast? {
        throw UnsupportedOperationException()
    }

    override fun requestForecastByZipCode(zipCode: Long, date: Long): ForecastList? {
        val result = Request(zipCode).execute()
        val converted = dataMapper.convertFromDataModel(zipCode, result)
        forecastDb.saveForecast(converted)
        return forecastDb.requestForecastByZipCode(zipCode, date)
    }
}