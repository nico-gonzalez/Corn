package com.ng.tvshowsdb.details

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import android.support.test.espresso.intent.Intents.intended
import android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent
import android.support.test.espresso.intent.matcher.IntentMatchers.hasExtra
import android.support.test.espresso.intent.rule.IntentsTestRule
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.espresso.matcher.ViewMatchers.withText
import com.ng.tvshowsdb.R
import com.ng.tvshowsdb.common.TestRobot
import com.ng.tvshowsdb.common.withIndex
import com.ng.tvshowsdb.detail.EXTRA_SHOW_ID
import com.ng.tvshowsdb.detail.ShowDetailActivity
import com.ng.tvshowsdb.presentation.detail.TvShowDetailsViewModel
import com.ng.tvshowsdb.shows.ShowsViewHolder
import org.hamcrest.CoreMatchers.allOf
import org.junit.Rule

fun showDetail(tvShow: TvShowDetailsViewModel, func: ShowDetailRobot.() -> Unit) = ShowDetailRobot(
    tvShow).apply {
  func()
  finish()
}

class ShowDetailRobot(private val tvShow: TvShowDetailsViewModel) : TestRobot() {

  @get:Rule
  private var activityTestRule = IntentsTestRule(ShowDetailActivity::class.java,
      true,
      false)

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
    intended(allOf(
        hasComponent(ShowDetailActivity::class.java.name),
        hasExtra(EXTRA_SHOW_ID, selectedShowId)
    ))
  }
}