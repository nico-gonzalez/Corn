package com.ng.tvshowsdb.core.ui.testing

import com.ng.tvshowsdb.core.domain.common.SchedulerProvider
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ImmediateSchedulers @Inject constructor() : SchedulerProvider {

    override fun io(): Scheduler = Schedulers.trampoline()

    override fun ui(): Scheduler = Schedulers.trampoline()
}