package com.kirdmt.weatherapp.activities


import android.annotation.SuppressLint
import android.content.Intent
import android.location.Location
import android.os.AsyncTask
import android.os.Bundle
import android.os.Looper
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.location.*
import com.kirdmt.weatherapp.Data.City
import com.kirdmt.weatherapp.R
import com.kirdmt.weatherapp.adapters.CitiesAdapter
import com.kirdmt.weatherapp.util.PermissionWorker
import com.kirdmt.weatherapp.util.WeatherJSONParser
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONObject
import java.net.URL
import java.util.*

class MainActivity : AppCompatActivity() {


    val API: String = "8118ed6ee68db2debfaaa5a44c832918"

    private var url: String = "nothing"
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    var cities: ArrayList<City> = ArrayList()
    var citiesAdapter = CitiesAdapter(cities, { city -> cityItemClicked(city) })


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)


        cities_list.layoutManager = LinearLayoutManager(this)

        cities_list.adapter = citiesAdapter

        //////////
        var permissionWorker = PermissionWorker(this, this)

        //if permissions is disabled
        if (!permissionWorker.checkPermissions()) {
            permissionWorker.requestPermissions()

        }
        //if location is off
        else if (!permissionWorker.isLocationEnabled()) {
            Toast.makeText(this, getString(R.string.switch_on_location), Toast.LENGTH_LONG).show()
        } else {

            getStartLocationWeather()
        }


    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here.
        val id = item.getItemId()

        if (id == R.id.action_one) {

            openDialog()
            return true
        }
        return super.onOptionsItemSelected(item)

    }


    fun openDialog() {
        val builder = AlertDialog.Builder(this)
        val inflater = layoutInflater
        builder.setTitle(getString(R.string.dialog_alert_title))
        val dialogLayout = inflater.inflate(R.layout.alert_dialog, null)
        val editText = dialogLayout.findViewById<EditText>(R.id.editText)
        builder.setView(dialogLayout)
        builder.setPositiveButton(getString(R.string.dialog_alert_ok)) { dialogInterface, i ->

            url =
                "https://api.openweathermap.org/data/2.5/weather?q=" + editText.text.toString() + "&units=metric&appid=$API"
            weatherTask().execute()

        }

        builder.setNegativeButton(getString(R.string.dialog_alert_cancel), null)
        builder.show()
    }

    private fun cityItemClicked(city: City) {

        val intent = Intent(this, DetailWeatherActivity::class.java)
        intent.putExtra("clickedCity", city)
        startActivity(intent)
    }


    private fun getStartLocationWeather() {

        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->

                if (location != null) {

                    var latitude = location.latitude
                    var longitude = location.longitude


                    url =
                        "https://api.openweathermap.org/data/2.5/weather?lat=$latitude&lon=$longitude&units=metric&appid=$API"


                    weatherTask().execute()


                } else {
                    requestNewLocationData()
                }


            }
    }

    @SuppressLint("MissingPermission")
    private fun requestNewLocationData() {
        var mLocationRequest = LocationRequest()
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        mLocationRequest.interval = 0
        mLocationRequest.fastestInterval = 0
        mLocationRequest.numUpdates = 1

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        fusedLocationClient!!.requestLocationUpdates(
            mLocationRequest, mLocationCallback,
            Looper.myLooper()
        )
    }

    private val mLocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            var mLastLocation: Location = locationResult.lastLocation

            url =

                "https://api.openweathermap.org/data/2.5/weather?lat=" + mLastLocation.latitude.toString() + "&lon=" + mLastLocation.longitude.toString() + "&units=metric&appid=$API"

            weatherTask().execute()
        }
    }

    inner class weatherTask() : AsyncTask<String, Void, String>() {

        override fun onPreExecute() {
            super.onPreExecute()
        }

        override fun doInBackground(vararg params: String?): String? {
            var response: String?
            try {

                response = URL(url).readText(
                    Charsets.UTF_8


                )
            } catch (e: Exception) {
                response = null
            }
            return response
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)


            try {

                val jsonObj = JSONObject(result)

                var parser = WeatherJSONParser()
                var city = parser.getCity(jsonObj)

                citiesAdapter.update(city)


            } catch (e: Exception) {

            }


        }
    }
}