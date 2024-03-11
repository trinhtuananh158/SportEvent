package com.example.sportevent

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import androidx.test.rule.ActivityTestRule
import com.example.sportevent.ui.activities.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class MainActivityUITest {

    @get:Rule
    val activityRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun testTabSelection() {
        // Check the initial visibility of tab content
        onView(withId(R.id.ctlTeamFragment)).check(matches(isDisplayed()))

        // Click on the second tab
        onView(withText("Previous Matches")).perform(click())

        // Check the visibility after clicking the second tab
        onView(withId(R.id.ctlPreviousFragment)).check(matches(isDisplayed()))

        // Click on the third tab
        onView(withText("Upcoming Matches")).perform(click())

        // Check the visibility after clicking the third tab
        onView(withId(R.id.ctlUpcomingMatch)).check(matches(isDisplayed()))
    }
}