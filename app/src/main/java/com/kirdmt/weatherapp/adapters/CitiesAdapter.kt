package com.kirdmt.weatherapp.adapters

import android.view.LayoutInflater
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.kirdmt.weatherapp.Data.City
import com.kirdmt.weatherapp.R

import kotlinx.android.synthetic.main.city_list_item.view.*


class CitiesAdapter(val cities: ArrayList<City>, private val clickListener: (City) -> Unit) :
    RecyclerView.Adapter<CitiesAdapter.ViewHolder>() {


    var items = cities

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.city_list_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(items[position], clickListener)

    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(city: City, clickListener: (City) -> Unit) {


            Glide.with(itemView).load("https://openweathermap.org/img/wn/" + city.icon + "@2x.png")
                .into(itemView.weather_image)

            itemView.city_name.text = city.cityName
            itemView.city_temp.text = city.temp
            itemView.setOnClickListener { clickListener(city) }

        }
    }

    fun update(city: City) {
        items.add(city)
        notifyDataSetChanged()
    }


}