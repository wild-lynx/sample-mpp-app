package util

import io.WeatherApi

actual suspend fun getTheWeather(
    resultWeatherList: MutableList<WeatherApi.Weather>,
    weatherApi: WeatherApi,
    citiesList: MutableList<String>
) {
}