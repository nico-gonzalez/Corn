package com.ng.tvshowsdb.domain.common

import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers

class ImmediateSchedulers : SchedulerProvider {

    override fun io(): Scheduler = Schedulers.trampoline()

    override fun ui(): Scheduler = Schedulers.trampoline()
}