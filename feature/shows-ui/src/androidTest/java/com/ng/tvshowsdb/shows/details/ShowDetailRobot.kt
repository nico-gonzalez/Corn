package com.ng.tvshowsdb.shows.details

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import androidx.test.espresso.intent.Intents.intended
import androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent
import androidx.test.espresso.intent.matcher.IntentMatchers.hasExtra
import androidx.test.espresso.intent.rule.IntentsTestRule
import androidx.test.espresso.matcher.ViewMatchers.*
import com.ng.tvshowsdb.core.ui.testing.TestRobot
import com.ng.tvshowsdb.core.ui.testing.withIndex
import com.ng.tvshowsdb.shows.R
import com.ng.tvshowsdb.shows.detail.EXTRA_SHOW_ID
import com.ng.tvshowsdb.shows.detail.ShowDetailActivity
import com.ng.tvshowsdb.shows.detail.ShowDetailsUiModel
import com.ng.tvshowsdb.shows.list.ShowsViewHolder
import org.hamcrest.CoreMatchers.allOf
import org.junit.Rule

fun showDetail(tvShow: ShowDetailsUiModel, func: ShowDetailRobot.() -> Unit) = ShowDetailRobot(
    tvShow
).apply {
    func()
    finish()
}

class ShowDetailRobot(private val tvShow: ShowDetailsUiModel) : TestRobot() {

    @get:Rule
    var activityTestRule = IntentsTestRule(
        ShowDetailActivity::class.java,
        true,
        false
    )

    init {
        val intent = ShowDetailActivity.startIntent(application(), tvShow.id)
        activityTestRule.launchActivity(intent)
    }

    fun finish() {
        activityTestRule.finishActivity()
    }

    fun checkShowDetailsAreDisplayed() {
        onView(withIndex(withId(R.id.titleTv), 0)).check(matches(withText(tvShow.title)))
        onView(withIndex(withId(R.id.ratingTv), 0)).check(matches(withText(tvShow.rating)))
        onView(withIndex(withId(R.id.posterIv), 0)).check(matches(isDisplayed()))
        onView(withId(R.id.descriptionTv)).check(matches(withText(tvShow.description)))
    }

    fun clickOnSimilarTvShowAt(position: Int) {
        onView(withId(R.id.similarShowsRv))
            .perform(actionOnItemAtPosition<ShowsViewHolder>(position, click()))
    }

    fun checkShowDetailsAreOpened(selectedShowId: Long) {
        intended(
            allOf(
                hasComponent(ShowDetailActivity::class.java.name),
                hasExtra(EXTRA_SHOW_ID, selectedShowId)
            )
        )
    }
}