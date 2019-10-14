package com.kirdmt.weatherapp.util

import android.util.Log
import kotlin.math.roundToInt

class Support {

    fun CelsiusKelvinConverter(degree: Double): Int {

        var result: Double = 0.0

        if (degree > 100) {
            result = degree - 273.15
        } else {
            result = degree
        }

        Log.d("SupportTAG", "First Degree is: " + degree)
        Log.d("SupportTAG", "Result is: " + result)

        return result.roundToInt()

    }
}