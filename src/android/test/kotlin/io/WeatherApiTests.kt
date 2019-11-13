package io

import io.ktor.client.engine.okhttp.OkHttpConfig
import io.ktor.client.engine.okhttp.OkHttpEngine
import io.ktor.util.InternalAPI
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import util.getTheWeather
import kotlin.test.Test
import kotlin.test.assertEquals

@InternalAPI // to allow usage of `OkHttpEngine` in `WeatherApi` initialization
class WeatherApiTests {
    // @UseExperimental(kotlinx.coroutines.ObsoleteCoroutinesApi::class)
    val weatherApi = WeatherApi(OkHttpEngine(OkHttpConfig()))

    @Test
    fun fetchWeather_NoCity() = runBlocking {
        // using `runBlocking` instead of `runBlockingTest` because of https://github.com/Kotlin/kotlinx.coroutines/issues/1204
        val result = weatherApi.fetchWeather()
        assertEquals(result.name, defaultCity)
    }

    @Test
    fun fetchWeather_UnknownCity() = runBlocking {
        // using `runBlocking` instead of `runBlockingTest` because of https://github.com/Kotlin/kotlinx.coroutines/issues/1204
        val result = weatherApi.fetchWeather("unknown")
        assertEquals(result.name, default404City)
    }

    // commented out due to the replacement of `runBlockingTest` with `runBlocking` — see the comment below
/*
    @UseExperimental(kotlinx.coroutines.ObsoleteCoroutinesApi::class)
    private val mainThreadSurrogate = newSingleThreadContext("UI thread")

    @BeforeTest
    @UseExperimental(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
    fun setUp() {
        Dispatchers.setMain(mainThreadSurrogate)
    }

    @AfterTest
    @UseExperimental(kotlinx.coroutines.ExperimentalCoroutinesApi::class)
    fun tearDown() {
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
        mainThreadSurrogate.close()
    }*/

    // commented out due to https://github.com/Kotlin/kotlinx.coroutines/issues/1647
    /*
    @Test
    @ExperimentalCoroutinesApi
    fun getTheWeather_NoCityListTest() = runBlocking {
        // using `runBlocking` instead of `runBlockingTest` because of https://github.com/Kotlin/kotlinx.coroutines/issues/1204
        val resultWeatherList = mutableListOf<WeatherApi.Weather>()
        val deferred = async {
            // no city list specified, so the default city list ($defaultCitiesList) value should be used
            getTheWeather(resultWeatherList, weatherApi)

            val controlList = mutableListOf<String>()
            resultWeatherList.forEach { controlList.add(it.name) }

            assertEquals(controlList, defaultCitiesList)
        }
        deferred.await()
    }
    */

    @Test
    @ExperimentalCoroutinesApi
    fun getTheWeather_EmptyCityListTest() = runBlocking {
        // using `runBlocking` instead of `runBlockingTest` because of https://github.com/Kotlin/kotlinx.coroutines/issues/1204
        val resultWeatherList =
            mutableListOf<WeatherApi.Weather>() // empty list, so the default city ($defaultCity) value should be used
        val deferred = async {
            getTheWeather(resultWeatherList, weatherApi, citiesList = mutableListOf())
            assertEquals(resultWeatherList[0].name, defaultCity)
        }
        deferred.await()
    }
}
