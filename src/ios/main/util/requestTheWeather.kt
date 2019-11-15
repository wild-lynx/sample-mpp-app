package util

import io.WeatherApi
import kotlinx.coroutines.withContext
import platform.darwin.*

actual suspend fun requestTheWeather(
    resultWeatherList: MutableList<WeatherApi.Weather>,
    weatherApi: WeatherApi,
    citiesList: MutableList<String>
) {
    val iOSCoroutineDispatcher = SimpleCoroutineDispatcher(dispatch_get_main_queue())
    if (citiesList.size == 0) { // if it's empty, use the $defaultCity (specified in `fetchWeather` fun)
        resultWeatherList.add(withContext(iOSCoroutineDispatcher) { weatherApi.fetchWeather() })
    } else {
        citiesList.forEach {
            resultWeatherList.add(withContext(iOSCoroutineDispatcher) { weatherApi.fetchWeather(it) })
        }
    }
}