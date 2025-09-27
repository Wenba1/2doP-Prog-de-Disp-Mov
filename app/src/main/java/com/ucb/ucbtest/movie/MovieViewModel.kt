package com.ucb.ucbtest.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ucb.data.NetworkResult
import com.ucb.domain.Movie
import com.ucb.usecases.GetLikedMovies
import com.ucb.usecases.GetPopularMovies
import com.ucb.usecases.SetMovieLike
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/*@HiltViewModel
class MovieViewModel @Inject constructor (private val getPopularMovies: GetPopularMovies): ViewModel() {

    sealed class MovieUIState {
        object Loading: MovieUIState()
        class Loaded(val list: List<Movie>): MovieUIState()
        class Error(val message: String): MovieUIState()
    }
    private val _state = MutableStateFlow<MovieUIState>(MovieUIState.Loading)
    val state : StateFlow<MovieUIState> = _state

    fun loadMovies() {
        _state.value = MovieUIState.Loading
        viewModelScope.launch {
            val response = getPopularMovies.invoke()
            when ( val result = response ) {
                is NetworkResult.Error -> {
                    _state.value = MovieUIState.Error(result.error)
                }
                is NetworkResult.Success -> {
                    _state.value = MovieUIState.Loaded(result.data)
                }
            }

        }

    }
}*/

@HiltViewModel
class MovieViewModel @Inject constructor(
    private val getPopularMovies: GetPopularMovies,
    private val getLikedMovies: GetLikedMovies,
    private val setMovieLike: SetMovieLike
) : ViewModel() {

    sealed interface MovieUIState {
        data object Loading: MovieUIState
        data class Loaded(val data: List<MovieItemUI>): MovieUIState
        data class Error(val message: String): MovieUIState
    }

    data class MovieItemUI(
        val title: String,
        val posterPath: String,
        val overview: String,
        val isLiked: Boolean
    )

    private val _state = MutableStateFlow<MovieUIState>(MovieUIState.Loading)
    val state: StateFlow<MovieUIState> = _state

    fun load() {
        _state.value = MovieUIState.Loading
        viewModelScope.launch {
            when (val result = getPopularMovies.invoke()) {
                is com.ucb.data.NetworkResult.Error -> {
                    _state.value = MovieUIState.Error(result.error)
                }
                is com.ucb.data.NetworkResult.Success -> {
                    val liked = getLikedMovies().toSet()
                    val items = result.data.map { m ->
                        MovieItemUI(
                            title = m.title,
                            posterPath = m.posterPath,
                            overview = m.overview,
                            isLiked = liked.contains(m.title)
                        )
                    }.sortedByDescending { it.isLiked } // 👈 liked primero
                    _state.value = MovieUIState.Loaded(items)
                }
            }
        }
    }

    fun onToggleLike(item: MovieItemUI) {
        viewModelScope.launch {
            val newLiked = !item.isLiked
            setMovieLike(item.title, newLiked)
            // refresca estado localmente (sin volver a llamar red)
            val current = (_state.value as? MovieUIState.Loaded)?.data ?: return@launch
            val updated = current.map {
                if (it.title == item.title) it.copy(isLiked = newLiked) else it
            }.sortedByDescending { it.isLiked } // reordenar
            _state.value = MovieUIState.Loaded(updated)
        }
    }
}
