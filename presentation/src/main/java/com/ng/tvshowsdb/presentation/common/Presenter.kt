package com.ng.tvshowsdb.presentation.common

import io.reactivex.disposables.CompositeDisposable

abstract class Presenter {

    protected val subscriptions: CompositeDisposable = CompositeDisposable()

    fun detach() {
        subscriptions.dispose()
    }
}