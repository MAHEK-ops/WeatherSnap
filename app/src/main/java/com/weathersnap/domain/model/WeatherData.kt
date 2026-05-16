package com.weathersnap.domain.model

import com.google.gson.annotations.SerializedName

data class WeatherData(
    val cityName: String,
    val country: String,
    val latitude: Double,
    val longitude: Double,
    val temperature: Double,
    val condition: String,
    val humidity: Int,
    val windSpeed: Double,
    val pressure: Double
)