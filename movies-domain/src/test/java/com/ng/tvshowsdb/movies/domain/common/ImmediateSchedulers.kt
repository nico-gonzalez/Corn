package com.ng.tvshowsdb.movies.domain.common

import com.ng.tvshowsdb.core.domain.common.SchedulerProvider
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers

class ImmediateSchedulers : SchedulerProvider {

    override fun io(): Scheduler = Schedulers.trampoline()

    override fun ui(): Scheduler = Schedulers.trampoline()
}
