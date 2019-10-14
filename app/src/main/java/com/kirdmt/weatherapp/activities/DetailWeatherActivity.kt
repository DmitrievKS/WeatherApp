package com.kirdmt.weatherapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.bumptech.glide.Glide
import com.kirdmt.weatherapp.Data.City
import com.kirdmt.weatherapp.R
import kotlinx.android.synthetic.main.activity_detail_weather.*
import kotlinx.android.synthetic.main.city_list_item.view.*

class DetailWeatherActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_weather)

        val city = intent.extras?.get("clickedCity") as City

        findViewById<TextView>(R.id.detail_textView_city).text = city.cityName
        findViewById<TextView>(R.id.detail_textView_temp).text = city.temp
        findViewById<TextView>(R.id.detail_textView_wind_degree).text = city.windDeg

        findViewById<TextView>(R.id.detail_textView_wind_degree).text = city.windDeg
        findViewById<TextView>(R.id.detail_close_button).setOnClickListener {
            onBackPressed()
        }

        Glide.with(this).load("https://openweathermap.org/img/wn/" + city.icon + "@2x.png")
            .into(this.detail_weather_image)

    }
}
