package com.kirdmt.weatherapp.util

import android.util.Log
import com.kirdmt.weatherapp.Data.City
import org.json.JSONObject


class WeatherJSONParser() {


    fun getCity(weatherJson: JSONObject): City {


        val main = weatherJson.getJSONObject("main")
        val sys = weatherJson.getJSONObject("sys")
        val wind = weatherJson.getJSONObject("wind")
        val weather = weatherJson.getJSONArray("weather").getJSONObject(0)

        //Log.d("weatherDataTag", "DATA IS: " + weatherJson.toString())

        val weatherDescription = weather.getString("description")
        val windSpeed = wind.getString("speed")
        val windDeg = wind.getString("deg")
        val name = weatherJson.getString("name")
        val icon = weather.getString("icon")

        //if we need convert kelvin to celsius
        /*
        var support = Support()
        val tempNativeString = main.getString("temp")
        val tempNativeDouble = tempNativeString.toDouble()
        val tempCelsiusInt = support.CelsiusKelvinConverter(tempNativeDouble)
        val temp = "$tempCelsiusInt°C"*/

        val temp = main.getString("temp") + "°C"
        return City(name, windSpeed, windDeg, temp, weatherDescription, icon)
    }
}