package com.ng.tvshowsdb.presentation.common

interface View<in T : Presenter> {

  fun setPresenter(presenter: T)

}