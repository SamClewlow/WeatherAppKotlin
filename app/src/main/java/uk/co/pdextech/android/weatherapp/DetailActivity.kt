package uk.co.pdextech.android.weatherapp

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.appcompat.R.attr.color
import android.support.v7.widget.Toolbar
import android.widget.TextView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*
import org.jetbrains.anko.ctx
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.find
import org.jetbrains.anko.uiThread
import uk.co.pdextech.android.domain.Forecast
import java.text.DateFormat
import java.util.*

class DetailActivity : AppCompatActivity(), ToolbarManager {

    override val toolbar by lazy { find<Toolbar>(R.id.detail_toolbar) }

    companion object {
        val ID = "DetailActivity.Id"
        val CITY_NAME = "DetailActivity.cityName"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        initToolbar()
        toolbarTitle = intent.getStringExtra(CITY_NAME)
        enableHomeAsUp { onBackPressed() }

        doAsync {
            val result = RequestDayForecastCommand(intent.getLongExtra(ID, -1)).execute()
            uiThread {
                bindForecast(result)
            }
        }
    }

    private fun bindForecast(forecast: Forecast) {
        with(forecast) {
            Picasso.with(ctx).load(iconUrl).into(icon)
            supportActionBar?.subtitle = date.toDateString(DateFormat.FULL)
            weatherDescription.text = description
            bindWeather(high to maxTemperature, low to minTemperature)
        }
    }

    private fun bindWeather(vararg views: Pair<Int, TextView>) = views.forEach {

        it.second.text = "${it.first.toString()}"
        it.second.textColor = color(when(it.first) {
            in -50..0 -> android.R.color.holo_red_dark
            in 0..15 -> android.R.color.holo_orange_dark
            else -> android.R.color.holo_green_dark
        })
    }
}



public fun Context.color(res: Int): Int = ContextCompat.getColor(this, res)

var TextView.textColor: Int
    get() = currentTextColor
    set(v) = setTextColor(v)

fun Long.toDateString(format: Int) : String {
    return DateFormat.getDateInstance(format, Locale.getDefault()).format(this)
}

