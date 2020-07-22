package util

import io.WeatherApi

actual suspend fun requestTheWeather(
    resultWeatherList: MutableList<WeatherApi.Weather>,
    weatherApi: WeatherApi,
    citiesList: MutableList<String>
) {
    if (citiesList.size == 0) { // if it's empty, use the $defaultCity (specified in `fetchWeather` fun)
        resultWeatherList.add(weatherApi.fetchWeather())
    } else {
        citiesList.forEach {
            resultWeatherList.add(weatherApi.fetchWeather(it))
        }
    }
}