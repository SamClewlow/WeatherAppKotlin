package uk.co.pdextech.android.weatherapp

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_forecast.view.*
import uk.co.pdextech.android.ctx
import uk.co.pdextech.android.domain.Forecast
import uk.co.pdextech.android.domain.ForecastList
import java.text.DateFormat
import java.util.*

/**
 * Created by Pdex on 12/02/2018.
 */


class ForecastListAdaptor(val weekForecast: ForecastList, val itemClick: (Forecast) -> Unit) : RecyclerView.Adapter<ForecastListAdaptor.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.ctx)
                    .inflate(
                        R.layout.item_forecast,
                        parent,
                        false)

        return ViewHolder(view, itemClick)
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       with(weekForecast[position]) {
            holder.bindForecast(this)
        }
    }

    override fun getItemCount(): Int = weekForecast.size


    class ViewHolder(val view: View, val itemClick: (Forecast) -> Unit) : RecyclerView.ViewHolder (view) {

        fun bindForecast(forecast: Forecast) {
            with(forecast) {
                Picasso.with(view.ctx).load(iconUrl).into(itemView.icon)
                itemView.date.text = convertDate(date)
                itemView.description.text = description
                itemView.maxTemperature.text = "$high"
                itemView.minTemperature.text = "$low"
                itemView.setOnClickListener { itemClick(this) }
            }
        }

        private fun convertDate(date: Long) : String {
            val df = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.getDefault())
            return df.format(date)
        }

    }
}