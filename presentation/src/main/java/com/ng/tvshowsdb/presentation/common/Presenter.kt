package com.ng.tvshowsdb.presentation.common

import io.reactivex.disposables.CompositeDisposable

abstract class Presenter {

  protected val disposables: CompositeDisposable = CompositeDisposable()

  fun detach() {
    disposables.dispose()
  }
}