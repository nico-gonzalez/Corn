package com.ng.tvshowsdb.shared.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ng.tvshowsdb.shared.home.domain.action.GetHome
import com.ng.tvshowsdb.shared.home.domain.model.Home
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeScreenViewModel(
    private val getHome: GetHome,
) : ViewModel() {

    private val _state = MutableStateFlow(ViewState())
    val state: StateFlow<ViewState> = _state.asStateFlow()

    data class ViewState(
        val isLoading: Boolean = true,
        val isRefreshing: Boolean = false,
        val home: Home? = null,
        val error: Throwable? = null,
    )

    init {
        getHomeScreen()
    }

    private fun getHomeScreen() {
        viewModelScope.launch {
            getHome()
                .onSuccess(::onGetHomeSuccess)
                .onFailure(::onGetHomeError)
        }
    }

    private fun onGetHomeError(error: Throwable) {
        _state.update {
            it.copy(
                isLoading = false,
                error = error
            )
        }
    }

    private fun onGetHomeSuccess(home: Home) {
        _state.update {
            it.copy(
                isLoading = false,
                home = home,
                error = null,
            )
        }
    }

    fun onRefresh() {
        _state.update {
            it.copy(
                isRefreshing = true
            )
        }
        viewModelScope.launch {
            getHomeScreen()
            _state.update {
                it.copy(
                    isRefreshing = false
                )
            }
        }
    }
}