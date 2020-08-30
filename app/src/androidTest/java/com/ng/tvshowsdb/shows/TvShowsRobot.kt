package com.ng.tvshowsdb.shows

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.intent.matcher.IntentMatchers.hasExtra
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.ng.tvshowsdb.R
import com.ng.tvshowsdb.common.TestRobot
import com.ng.tvshowsdb.detail.EXTRA_SHOW_ID
import com.ng.tvshowsdb.detail.ShowDetailActivity
import org.hamcrest.CoreMatchers.allOf
import org.junit.Rule

fun tvShows(func: TvShowsRobot.() -> Unit) = TvShowsRobot().apply {
    func()
    finish()
}

class TvShowsRobot : TestRobot() {

    @get:Rule
    private var activityTestRule = IntentsTestRule(
      TvShowsActivity::class.java,
      true,
      false
    )

    init {
        activityTestRule.launchActivity(null)
    }

    fun finish() {
        activityTestRule.finishActivity()
    }

    fun checkShowsAreDisplayed() {
        onView(withId(R.id.showsRv)).check(matches(isDisplayed()))
    }

    fun selectShowAt(position: Int) {
        onView(withId(R.id.showsRv)).perform(
          actionOnItemAtPosition<ShowsViewHolder>(
            position,
            click()
          )
        )
    }

    fun checkShowDetailsAreOpened(showId: Long) {
        intended(
          allOf(
            hasComponent(ShowDetailActivity::class.java.name),
            hasExtra(EXTRA_SHOW_ID, showId)
          )
        )
    }
}