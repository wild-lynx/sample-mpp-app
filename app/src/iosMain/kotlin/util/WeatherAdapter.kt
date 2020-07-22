package util

import io.WeatherApi
import io.defaultCitiesList
import io.ktor.client.HttpClient
import kotlin.coroutines.CoroutineContext
import io.ktor.client.engine.ios.Ios
import io.ktor.client.engine.ios.IosClientEngineConfig
import kotlinx.coroutines.*
import platform.darwin.dispatch_get_main_queue

val iOSCoroutineScope = CoroutineScope(SimpleCoroutineDispatcher(dispatch_get_main_queue()))

/*
 * An utility class to wrap up all the weatherinfo-related functions
 * in Kotlin/Native and leave only UI to swift.
 * */
class WeatherAdapter {
    fun getTheWeather(callback: (String) -> Unit) = iOSCoroutineScope.launch {
//        val weatherApi = WeatherApi(IosClientEngine(IosClientEngineConfig()))
        val weatherApi = WeatherApi(Ios.create())
        val resultWeatherList = mutableListOf<WeatherApi.Weather>()

        try {
            // using the explicit syntax instead of default value for $citiesList
            // due to https://github.com/Kotlin/kotlinx.coroutines/issues/1647
            requestTheWeather(resultWeatherList, weatherApi, defaultCitiesList)
        } catch (e: Exception) {
            resultWeatherList.add(
                WeatherApi.Weather(
                    WeatherApi.Wind(0F, 0F),
                    e.message.toString(),
                    "Exception:"
                )
            )
        }

        callback(resultWeatherList.joinToString("\n\n"))
    }
}
