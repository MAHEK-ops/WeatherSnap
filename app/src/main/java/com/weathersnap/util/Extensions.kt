package com.weathersnap.util

/**
 * Converts a WMO weather code to a human-readable condition string.
 * Used to display weather condition from Open-Meteo API.
 */
fun Int.toWeatherCondition(): String = when (this) {
    0 -> "Clear sky"
    1, 2, 3 -> "Partly cloudy"
    45, 48 -> "Foggy"
    51, 53, 55 -> "Drizzle"
    61, 63, 65 -> "Rainy"
    71, 73, 75 -> "Snowy"
    80, 81, 82 -> "Rain showers"
    95 -> "Thunderstorm"
    96, 99 -> "Thunderstorm with hail"
    else -> "Unknown"
}

/**
 * Formats bytes into a readable KB string.
 */
fun Long.toKbString(): String = "${this} KB"