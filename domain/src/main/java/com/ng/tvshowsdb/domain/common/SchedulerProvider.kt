package com.ng.tvshowsdb.domain.common

import io.reactivex.Scheduler

interface SchedulerProvider {

  fun io(): Scheduler

  fun ui(): Scheduler
}