package de.larshauke.swapi_android.ui.route.details.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import de.larshauke.swapi_android.core.models.SearchDto
import de.larshauke.swapi_android.details.domain.use_case.Details
import de.larshauke.swapi_android.ui.navigation.Destinations
import de.larshauke.swapi_android.ui.route.details.util.toResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = DetailsViewModel.Factory::class)
class DetailsViewModel
@AssistedInject
constructor(
    @Assisted private val args: Destinations.DetailDestination,
    private val details: Details
) : ViewModel() {

    @AssistedFactory
    interface Factory {
        fun create(args: Destinations.DetailDestination): DetailsViewModel
    }

    val initialValue = DetailsUiContract.State(
        isLoading = false,
        data = null
    )

    private val _effect = Channel<DetailsUiContract.Effect>()
    val effect = _effect.receiveAsFlow()

    private val _state = MutableStateFlow(initialValue)
    val state = _state
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = initialValue
        )

    init {
        initDetails()
    }

    fun onEvent(event: DetailsUiContract.Event) {
        when(event) {
            is DetailsUiContract.Event.OnLoadUrls -> {
                // TODO
            }
            DetailsUiContract.Event.OnNavigateUp -> {
                viewModelScope.launch {
                    _effect.send(DetailsUiContract.Effect.NavigateUp)
                }
            }
        }
    }

    private fun initDetails() {
        viewModelScope.launch {
            renderLoading(true)

            args.swapiType?.let { type ->
                args.url?.let { url ->
                    details(url, type)
                        .flowOn(Dispatchers.IO)
                        .collect { result ->
                            result.onSuccess { data ->
                                renderData(data)
                            }.onFailure { t ->
                                renderError(t)
                            }
                        }
                }
            }
        }
    }

    private fun renderData(data: SearchDto) {
        renderLoading(false)
        _state.update { state ->
            state.copy(
                data = data.toResource()
            )
        }
    }

    private fun renderError(throwable: Throwable) {
        renderLoading(false)

        viewModelScope.launch {
            _effect.send(DetailsUiContract.Effect.Error(throwable.message.orEmpty()))
        }
    }

    private fun renderLoading(isLoading: Boolean) {
        _state.update { state ->
            state.copy(
                isLoading = isLoading
            )
        }
    }
}