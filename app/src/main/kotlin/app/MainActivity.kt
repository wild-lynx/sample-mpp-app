package app

/*import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import androidx.annotation.VisibleForTesting
//import androidx.appcompat.app.AppCompatActivity
//import androidx.recyclerview.widget.LinearLayoutManager
import io.WeatherApi
import io.defaultCitiesList
import io.ktor.client.engine.okhttp.OkHttpConfig
import io.ktor.client.engine.okhttp.OkHttpEngine
import io.ktor.util.InternalAPI
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import util.WeatherListViewAdapter
import util.getDeviceModel
import util.getFullDeviceInfo
import util.requestTheWeather
import kotlin.coroutines.CoroutineContext*/
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import io.ktor.client.engine.okhttp.OkHttpConfig
import io.ktor.client.engine.okhttp.OkHttpEngine
import io.ktor.util.InternalAPI
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import app.R
import util.WeatherListViewAdapter
import io.WeatherApi
import io.defaultCitiesList
import util.getDeviceModel
import util.getFullDeviceInfo
import util.requestTheWeather
import kotlin.coroutines.CoroutineContext

@InternalAPI // to allow usage of `OkHttpEngine` in `showTheWeather` fun
class MainActivity : AppCompatActivity(), CoroutineScope {
    private val job = Job()
    override val coroutineContext: CoroutineContext
        get() = job

    private fun initializeWeatherList() {
        weatherListView.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = WeatherListViewAdapter()
        }
    }

    inline fun <reified T> Any.safeCast() = this as? T

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initializeWeatherList()

        /* Set the text with the device info */
        header.text = getDeviceModel()
        deviceInfo.text = getFullDeviceInfo()

        /* Download and show the weather info */
        showTheWeather()

        job.complete()
    }

    private fun showTheWeather() {
        // set the "loading" placeholder
        weatherDebugInfo.text = getString(R.string.loading_weather_placeholder)

        val weatherApi = WeatherApi(OkHttpEngine(OkHttpConfig()))
        launch(Dispatchers.Main) {
            val resultWeatherList = mutableListOf<WeatherApi.Weather>()
            val cList = defaultCitiesList
            try {
                // using the explicit syntax instead of default value for $citiesList
                // due to https://github.com/Kotlin/kotlinx.coroutines/issues/1647
                requestTheWeather(resultWeatherList, weatherApi, cList)
                displayTheWeather(resultWeatherList)
            } catch (e: Exception) {
                weatherDebugInfo.text = e.message.toString()
            }
        }
    }

//    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    internal fun displayTheWeather(resultWeatherList: MutableList<WeatherApi.Weather>) {
        // clean the "loading" placeholder
        weatherDebugInfo.text = ""
        weatherListView.adapter?.safeCast<WeatherListViewAdapter>()
            ?.updWeatherList(resultWeatherList)
    }
}
