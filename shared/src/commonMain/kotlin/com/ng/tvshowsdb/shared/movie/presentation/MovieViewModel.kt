package com.ng.tvshowsdb.shared.movie.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ng.tvshowsdb.shared.app.AppBarStateDelegate
import com.ng.tvshowsdb.shared.movies.data.MovieRepository
import com.ng.tvshowsdb.shared.movies.domain.model.Movie
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MovieViewModel internal constructor(
    val movieId: Long,
    private val movieRepository: MovieRepository,
    private val appBarStateDelegate: AppBarStateDelegate,
) : ViewModel() {

    private val _state = MutableStateFlow(ViewState())
    val state: StateFlow<ViewState> = _state.asStateFlow()

    data class ViewState(
        val isLoading: Boolean = true,
        val movie: Movie? = null,
        val error: Throwable? = null,
    )

    init {
        getMovie()
    }

    private fun getMovie() {
        viewModelScope.launch {
            movieRepository.getMovie(id = movieId)?.also { movie ->
                _state.update {
                    it.copy(
                        movie = movie,
                        isLoading = false,
                        error = null
                    )
                }
                appBarStateDelegate.update {
                    it.copy(title = movie.title)
                }
            }
        }
    }
}
