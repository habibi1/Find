package com.habibi.find.ui.search

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.habibi.core.utils.EspressoIdlingResource
import com.habibi.find.R
import com.habibi.find.ui.MainActivity

import org.junit.After
import org.junit.Before
import org.junit.Test

class SearchFragmentTest {

    @Before
    fun setUp() {
        ActivityScenario.launch(MainActivity::class.java)
        IdlingRegistry.getInstance().register(EspressoIdlingResource.idlingResource)
    }

    @After
    fun tearDown() {
        IdlingRegistry.getInstance().unregister(EspressoIdlingResource.idlingResource)
    }

    @Test
    fun loadData() {
        delayTwoSecond()
        onView(withId(R.id.til_search)).check(matches(isDisplayed()))
        onView(withId(R.id.edt_search)).check(matches(isDisplayed()))
        onView(withId(R.id.edt_search)).perform(clearText())
        onView(withId(R.id.edt_search)).perform(click()).perform(clearText(), typeText("habibi"))
            .perform(pressImeActionButton())
        onView(withId(R.id.edt_search)).perform(clearText())
        onView(withId(R.id.edt_search)).perform(click()).perform(clearText(), typeText("coba"))
            .perform(pressImeActionButton())
        onView(withId(R.id.rv_search)).check(matches(isDisplayed()))
    }

    @Test
    fun loadEmpty() {
        delayTwoSecond()
        onView(withId(R.id.til_search)).check(matches(isDisplayed()))
        onView(withId(R.id.edt_search)).check(matches(isDisplayed()))
        onView(withId(R.id.edt_search)).perform(clearText())
        onView(withId(R.id.edt_search)).perform(click()).perform(clearText(), typeText("habibi667864781263471347523674"))
            .perform(pressImeActionButton())
        onView(withId(R.id.view_data_empty)).check(matches(isDisplayed()))
    }

    private fun delayTwoSecond() {
        try {
            Thread.sleep(2000)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }
}