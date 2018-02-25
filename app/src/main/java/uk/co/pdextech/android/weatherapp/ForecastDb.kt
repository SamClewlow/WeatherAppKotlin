package uk.co.pdextech.android.weatherapp

import android.database.sqlite.SQLiteDatabase
import android.util.Log
import org.jetbrains.anko.db.MapRowParser
import org.jetbrains.anko.db.SelectQueryBuilder
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import uk.co.pdextech.android.domain.Forecast
import uk.co.pdextech.android.domain.ForecastList
import uk.co.pdextech.android.model.DbCityForecast
import uk.co.pdextech.android.model.DbDayForecast

/**
 * Created by Pdex on 14/02/2018.
 */
class ForecastDb(private val forecastDbHelper: ForecastDbHelper = ForecastDbHelper.instance,
                 private val dataMapper: DbDataMapper = DbDataMapper()) : ForecastDataSource {

    override fun requestForecastByZipCode(zipCode: Long, date: Long) = forecastDbHelper.use {

        val dailyRequest = "${DayForecastTable.CITY_ID} = ? AND ${DayForecastTable.DATE} >= ?"
        val dailyForecast = select(DayForecastTable.NAME)
                .whereSimple(dailyRequest, zipCode.toString(), date.toString())

        val parsed = dailyForecast.parseList { DbDayForecast(HashMap(it)) }

        val city = select(CityForecastTable.NAME)
                .whereSimple("${CityForecastTable.ID} = ?", zipCode.toString())
                .parseOpt { DbCityForecast(HashMap(it), parsed) }

        if (city != null) dataMapper.convertToDomain(city) else null
    }

    override fun requestDayForecast(id: Long): Forecast? = forecastDbHelper.use {
        val forecast = select(DayForecastTable.NAME).byId(id)
                .parseOpt { DbDayForecast(HashMap(it)) }
        if (forecast != null) dataMapper.convertDayToDomain(forecast) else null
    }

    fun saveForecast(forecast: ForecastList) = forecastDbHelper.use {

        clear(CityForecastTable.NAME)
        clear(DayForecastTable.NAME)

        with(dataMapper.convertFromDomain(forecast)) {
            var result = insert(CityForecastTable.NAME, *map.toVarargArray())

            Log.d("DB_TAG_SAM", "$result")

            dailyForecast.forEach {

                var result = insert(DayForecastTable.NAME, *it.map.toVarargArray())
                Log.d("DB_TAG_SAM", "$result")
            }
        }
    }
}

fun <K, V : Any> MutableMap<K, V?>.toVarargArray():
        Array<out Pair<K, V>> = map({ Pair(it.key, it.value!!) }).toTypedArray()

fun SQLiteDatabase.clear(tableName: String) {
    execSQL("delete from $tableName")
}

fun SelectQueryBuilder.byId(id: Long): SelectQueryBuilder {
    return whereSimple("_id = ?", id.toString())
}

fun <T : Any> SelectQueryBuilder.parseOpt(parser: (Map<String, Any?>) -> T) : T? =
        parseOpt(object : MapRowParser<T> {
            override fun parseRow(columns: Map<String, Any?>): T = parser(columns)
        })

fun <T : Any> SelectQueryBuilder.parseList(parser: (Map<String, Any?>) -> T) : List<T> =
        parseList(object : MapRowParser<T> {
            override fun parseRow(columns: Map<String, Any?>): T = parser(columns)
})