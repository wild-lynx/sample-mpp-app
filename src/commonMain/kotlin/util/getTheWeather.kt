package util

import io.WeatherApi
import io.defaultCitiesList

/*
The function is multiplatform because of the different
`kotlinx.coroutines` solutions of IO treatment for iOS and Android
*/
expect suspend fun getTheWeather(
    resultWeatherList: MutableList<WeatherApi.Weather>,
    weatherApi: WeatherApi,
    citiesList: MutableList<String> = defaultCitiesList
)
