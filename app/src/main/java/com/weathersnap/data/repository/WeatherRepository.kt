package com.weathersnap.data.repository

import com.weathersnap.domain.model.CityResult
import com.weathersnap.domain.model.WeatherData

interface WeatherRepository {
    suspend fun searchCities(query: String): Result<List<CityResult>>
    suspend fun getWeather(latitude: Double, longitude: Double, cityName: String, country: String): Result<WeatherData>
}