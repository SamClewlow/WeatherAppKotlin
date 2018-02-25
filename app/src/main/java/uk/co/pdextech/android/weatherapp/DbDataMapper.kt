package uk.co.pdextech.android.weatherapp

import uk.co.pdextech.android.domain.Forecast
import uk.co.pdextech.android.domain.ForecastList
import uk.co.pdextech.android.model.DbCityForecast
import uk.co.pdextech.android.model.DbDayForecast

/**
 * Created by Pdex on 14/02/2018.
 */
class DbDataMapper {

    fun convertToDomain(city: DbCityForecast) = with(city) {
        val daily = dailyForecast.map { convertDayToDomain(it) }
        ForecastList(_id, this.city, country, daily)
    }

    fun convertDayToDomain(dayForecast: DbDayForecast) = with(dayForecast) {
        Forecast(_id, date, description, high, low, iconUrl)
    }

    fun convertFromDomain(forecast: ForecastList) = with(forecast) {
        val days = dailyForecast.map { convertFromDayDomain(id, it) }
        DbCityForecast(id, city, country, days)
    }

    fun convertFromDayDomain(cityId: Long, dayForecast: Forecast) = with(dayForecast) {
        DbDayForecast(date, description, high, low, iconUrl, cityId)
    }
}