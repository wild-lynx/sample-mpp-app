package io

import io.ktor.client.HttpClient
import io.ktor.client.engine.HttpClientEngine
import io.ktor.client.features.json.JsonFeature
import io.ktor.client.features.json.serializer.KotlinxSerializer
import io.ktor.client.request.get
import io.ktor.client.request.url
import kotlinx.serialization.Serializable
import kotlinx.serialization.UnstableDefault
import kotlinx.serialization.json.Json

class WeatherApi(private val engine: HttpClientEngine) {
    companion object {
        private const val baseUrl = "https://api.openweathermap.org/data/2.5"
    }

    @Serializable
    data class Wind(val speed: Float, val deg: Float)

    @Serializable
    data class Weather(val wind: Wind, val visibility: String, val name: String) {
        override fun toString(): String {
            return "$name $visibility $wind"
        }
    }

    @UseExperimental(UnstableDefault::class)
    private val client by lazy {
        HttpClient(engine) {
            install(JsonFeature) {
                serializer = KotlinxSerializer(Json.nonstrict)
            }
        }
    }

    /**
     * Get the weather in the required city.
     * If the city isn't specified or invalid, the default city ($defaultCity) will be used.
     * @return Weather serialized obj
     * */
    suspend fun fetchWeather(city: String = defaultCity): Weather {
        return try {
            (client.get {
                url("$baseUrl/weather?q=$city,de&appid=$weatherApiKey")
            })
        } catch (exception: Exception) {
            (client.get {
                url("$baseUrl/weather?q=$default404City,de&appid=$weatherApiKey")
            })
        }
    }
}
