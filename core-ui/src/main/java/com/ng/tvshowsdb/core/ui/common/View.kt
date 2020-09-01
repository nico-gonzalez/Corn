package com.ng.tvshowsdb.core.ui.common

interface View<in T : Presenter> {

    fun setPresenter(presenter: T)
}