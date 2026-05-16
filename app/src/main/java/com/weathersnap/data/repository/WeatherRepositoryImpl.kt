package com.weathersnap.data.repository

import com.weathersnap.data.remote.api.GeocodingApi
import com.weathersnap.data.remote.api.WeatherApi
import com.weathersnap.domain.model.CityResult
import com.weathersnap.domain.model.WeatherData
import com.weathersnap.util.toWeatherCondition
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val geocodingApi: GeocodingApi,
    private val weatherApi: WeatherApi
) : WeatherRepository {

    // In-memory cache: query → list of city results
    private val cityCache = mutableMapOf<String, List<CityResult>>()

    override suspend fun searchCities(query: String): Result<List<CityResult>> =
        withContext(Dispatchers.IO) {
            // Return cached result if available
            cityCache[query]?.let { return@withContext Result.success(it) }

            try {
                val response = geocodingApi.searchCities(query)
                val cities = response.results?.map { result ->
                    CityResult(
                        id = result.id,
                        name = result.name,
                        country = result.country,
                        latitude = result.latitude,
                        longitude = result.longitude
                    )
                } ?: emptyList()

                // Store in cache
                cityCache[query] = cities
                Result.success(cities)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }

    override suspend fun getWeather(
        latitude: Double,
        longitude: Double,
        cityName: String,
        country: String
    ): Result<WeatherData> = withContext(Dispatchers.IO) {
        try {
            val response = weatherApi.getWeather(latitude, longitude)
            val humidity = response.hourly.humidity.firstOrNull() ?: 0
            val pressure = response.hourly.pressure.firstOrNull() ?: 0.0

            val weather = WeatherData(
                cityName = cityName,
                country = country,
                latitude = latitude,
                longitude = longitude,
                temperature = response.currentWeather.temperature,
                condition = response.currentWeather.weatherCode.toWeatherCondition(),
                humidity = humidity,
                windSpeed = response.currentWeather.windSpeed,
                pressure = pressure
            )
            Result.success(weather)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}