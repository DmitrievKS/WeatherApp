package com.kirdmt.weatherapp.Data



import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class City (val cityName: String, val windSpeed: String, val windDeg: String, val temp: String, val description: String, val icon: String) : Parcelable