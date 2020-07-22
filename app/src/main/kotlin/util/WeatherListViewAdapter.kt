package util

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import app.R
import kotlinx.android.synthetic.main.weatherlist_row.view.*
import io.WeatherApi

class WeatherListViewAdapter : RecyclerView.Adapter<WeatherListViewAdapter.ViewHolder>() {
    var weatherList = mutableListOf<WeatherApi.Weather>()

    fun updWeatherList(list: List<WeatherApi.Weather>) {
        weatherList.clear()
        weatherList.addAll(list)
        notifyDataSetChanged()
    }

    // inflates the row layout from xml when needed
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.weatherlist_row, parent, false)
        return ViewHolder(view)
    }

    // binds the data to the TextView in each row
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        weatherList[position].let {
            holder.bind(weatherItem = it)
        }
    }

    // total number of rows
    override fun getItemCount(): Int {
        return weatherList.size
    }

    // stores and recycles views as they are scrolled off screen
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(weatherItem: WeatherApi.Weather) {
            itemView.name.text = weatherItem.name
            itemView.base.text = weatherItem.visibility
            itemView.coord.text = weatherItem.wind.toString()
        }
    }
}
