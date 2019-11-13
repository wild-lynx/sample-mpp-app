package app

import androidx.lifecycle.Lifecycle
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.rules.activityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import io.WeatherApi
import io.ktor.util.InternalAPI
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@InternalAPI
@RunWith(AndroidJUnit4::class)
class UIRefreshingTests {
    @get:Rule
    var activityScenarioRule = activityScenarioRule<MainActivity>()

    @Test
    fun displayTheWeatherFunTest() {
        val resultWeatherListMock = mutableListOf(
            WeatherApi.Weather(
                wind = WeatherApi.Wind(speed = 42F, deg = 4.2F),
                visibility = "tVisib",
                name = "TestCity"
            )
        )
        val scenario = activityScenarioRule.scenario
        scenario.moveToState(Lifecycle.State.RESUMED)
        scenario.onActivity { activity ->
            activity.displayTheWeather(resultWeatherListMock)
        }
        onView(withId(R.id.name)).check(matches(withText("TestCity")))
    }
}
