package util

import io.WeatherApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

actual suspend fun getTheWeather(
    resultWeatherList: MutableList<WeatherApi.Weather>,
    weatherApi: WeatherApi,
    citiesList: MutableList<String> // has default value in the `expect` part
) {
    if (citiesList.size == 0) { // if it's empty, use the $defaultCity (specified in `fetchWeather` fun)
        resultWeatherList.add(withContext(Dispatchers.IO) { weatherApi.fetchWeather() })
    } else {
        citiesList.forEach {
            resultWeatherList.add(withContext(Dispatchers.IO) { weatherApi.fetchWeather(it) })
        }
    }
}
