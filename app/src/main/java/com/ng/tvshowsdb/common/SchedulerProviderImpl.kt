package com.ng.tvshowsdb.common

import com.ng.tvshowsdb.domain.common.SchedulerProvider
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SchedulerProviderImpl : SchedulerProvider {

  override fun io(): Scheduler = Schedulers.io()

  override fun ui(): Scheduler = AndroidSchedulers.mainThread()
}