package uk.co.pdextech.android.model

/**
 * Created by Pdex on 12/02/2018.
 */

data class ServerForecastResult(val city: ServerCity,
                                val list: List<ServerForecast>)

data class ServerCity(val id: Long,
                      val name: String,
                      val coord: ServerCoordinates,
                      val country: String,
                      val population: Int)

data class ServerCoordinates(val lon: Float,
                             val lat: Float)

data class ServerForecast(val dt: Long,
                          val temp: ServerTemperature,
                          val pressure: Float,
                          val humidity: Int,
                          val weather: List<ServerWeather>,
                          val speed: Float,
                          val deg: Int,
                          val clouds: Int,
                          val rain: Float)

data class ServerTemperature(val day: Float,
                             val min: Float,
                             val max: Float,
                             val night: Float,
                             val eve: Float,
                             val morn: Float)

data class ServerWeather(val id: Long,
                         val main: String,
                         val description: String,
                         val icon: String)