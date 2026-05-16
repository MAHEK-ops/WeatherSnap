package com.weathersnap.data.remote.model

import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    @SerializedName("current_weather")
    val currentWeather: CurrentWeather,
    @SerializedName("hourly")
    val hourly: HourlyData
)

data class CurrentWeather(
    @SerializedName("temperature")
    val temperature: Double,
    @SerializedName("windspeed")
    val windSpeed: Double,
    @SerializedName("weathercode")
    val weatherCode: Int
)

data class HourlyData(
    @SerializedName("relativehumidity_2m")
    val humidity: List<Int>,
    @SerializedName("surface_pressure")
    val pressure: List<Double>
)