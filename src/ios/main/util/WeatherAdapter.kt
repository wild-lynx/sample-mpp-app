package util

import io.WeatherApi
import io.defaultCitiesList
import io.ktor.client.HttpClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import io.ktor.client.engine.ios.Ios
import io.ktor.client.engine.ios.IosClientEngineConfig

/*
 * An utility class to wrap up all the weatherinfo-related functions
 * in Kotlin/Native and leave only UI to swift.
 * */
class WeatherAdapter : CoroutineScope {
    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = job

    fun getTheWeather(): MutableList<WeatherApi.Weather> {
//        val weatherApi = WeatherApi(IosClientEngine(IosClientEngineConfig()))
        val weatherApi = WeatherApi(Ios.create())
        val resultWeatherList = mutableListOf<WeatherApi.Weather>()
        var result = resultWeatherList

        launch(Dispatchers.Main) {
            val cList = defaultCitiesList
            try {
                // using the explicit syntax instead of default value for $citiesList
                // due to https://github.com/Kotlin/kotlinx.coroutines/issues/1647
                requestTheWeather(resultWeatherList, weatherApi, cList)
                result = resultWeatherList
            } catch (e: Exception) {
                resultWeatherList.add(
                    WeatherApi.Weather(
                        WeatherApi.Wind(0F, 0F),
                        e.message.toString(),
                        "Exception:"
                    )
                )
                result = resultWeatherList
            }
        }
        job.complete()
        return result
    }
}
