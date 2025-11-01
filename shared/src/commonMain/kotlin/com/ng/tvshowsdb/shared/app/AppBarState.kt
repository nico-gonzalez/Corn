package com.ng.tvshowsdb.shared.app

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class AppBarStateDelegate constructor() {

    private val _appBarState = MutableStateFlow(AppBarState())
    val appBarState: StateFlow<AppBarState> = _appBarState

    fun update(function: (AppBarState) -> AppBarState) {
        _appBarState.update(function)
    }
}

data class AppBarState(
    val title: String? = null
)