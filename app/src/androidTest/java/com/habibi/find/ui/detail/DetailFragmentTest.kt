package com.habibi.find.ui.detail

import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.IdlingRegistry
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.habibi.core.utils.EspressoIdlingResource
import com.habibi.find.R
import com.habibi.find.ui.MainActivity

import org.junit.After
import org.junit.Before
import org.junit.Test

class DetailFragmentTest {

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
    fun loadDataDetail() {
        delayTwoSecond()
        onView(withId(R.id.til_search)).check(matches(isDisplayed()))
        onView(withId(R.id.edt_search)).check(matches(isDisplayed()))
        onView(withId(R.id.edt_search)).perform(clearText())
        onView(withId(R.id.edt_search)).perform(click()).perform(clearText(), typeText("habibi"))
            .perform(pressImeActionButton())
        onView(withId(R.id.edt_search)).perform(clearText())
        onView(withId(R.id.edt_search)).perform(click()).perform(clearText(), typeText("habibi1"))
            .perform(pressImeActionButton())
        onView(withId(R.id.rv_search)).check(matches(isDisplayed()))
        delayTwoSecond()
        onView(withId(R.id.rv_search)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                click()
            )
        )
        onView(withId(R.id.tv_name)).check(matches(isDisplayed()))
        onView(withId(R.id.iv_avatar_detail)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_type)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_bio)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_follower)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_location)).check(matches(isDisplayed()))
        onView(withId(R.id.tv_email)).check(matches(isDisplayed()))
        onView(withId(R.id.rv_repository)).check(matches(isDisplayed()))
    }

    private fun delayTwoSecond() {
        try {
            Thread.sleep(2000)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }
}