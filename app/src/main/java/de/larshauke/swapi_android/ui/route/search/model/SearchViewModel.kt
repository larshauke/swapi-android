package de.larshauke.swapi_android.ui.route.search.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import de.larshauke.swapi_android.core.models.FilmDto
import de.larshauke.swapi_android.core.models.PersonDto
import de.larshauke.swapi_android.core.models.PlanetDto
import de.larshauke.swapi_android.core.models.SearchDto
import de.larshauke.swapi_android.core.models.SpeciesDto
import de.larshauke.swapi_android.core.models.StarshipDto
import de.larshauke.swapi_android.core.models.SwapiType
import de.larshauke.swapi_android.core.models.Url
import de.larshauke.swapi_android.core.models.VehicleDto
import de.larshauke.swapi_android.search.domain.model.SearchResult
import de.larshauke.swapi_android.search.domain.use_case.LoadMore
import de.larshauke.swapi_android.search.domain.use_case.Search
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel
@Inject
constructor(
    private val search: Search,
    private val loadMore: LoadMore
) : ViewModel() {

    val initialValue = SearchUiContract.State(
        isLoading = false,
        searchResults = null,
        categoryState = SearchUiContract.State.CategoryState(
            films = true,
            people = true,
            planets = true,
            species = true,
            starships = true,
            vehicles = true,
        ),
        isMoreToLoad = false
    )

    private val _category = MutableStateFlow(SwapiType.entries.toList())
    private val _query = MutableStateFlow("")
    private val _moreUrls = MutableStateFlow<List<Pair<Url, SwapiType>>>(emptyList())

    private val _loadMore = MutableSharedFlow<Unit>()

    private val _effect = Channel<SearchUiContract.Effect>()
    val effect = _effect.receiveAsFlow()

    private val _state = MutableStateFlow(initialValue)
    val state = _state
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = initialValue
        )

    init {
        initSearch()

        initLoadMore()
    }

    fun onEvent(event: SearchUiContract.Event) {
        when(event) {
            is SearchUiContract.Event.OnQueryChanged -> _query.update { event.query }
            is SearchUiContract.Event.OnCategorySelected -> renderCategorySelected(event)
            is SearchUiContract.Event.OnEntryClicked -> renderOnEntryClicked(event)
            is SearchUiContract.Event.OnMore -> viewModelScope.launch {
                _loadMore.emit(Unit)
            }
        }
    }

    private fun initSearch() {
        viewModelScope.launch {
            combine(
                _query,
                _category
            ) { query, category ->
                Pair(query, category)
            }
                .onEach { renderLoading(false) }
                .filter { it.first.isNotBlank() && it.second.isNotEmpty() }
                .debounce(500)
                .distinctUntilChanged()
                .onEach { renderLoading(true) }
                .flatMapLatest {
                    search(
                        query = it.first,
                        types = it.second
                    )
                }
                .flowOn(Dispatchers.IO)
                .collect {
                    it.onSuccess { list ->
                        renderSearchResult(list)
                    }.onFailure { t ->
                        renderError(t.message)
                    }
                }
        }
    }

    private fun renderSearchResult(list: List<SearchResult>) {
        renderLoading(false)
        val moreUrls = list.mapNotNull { searchResult ->
            searchResult.nextUrl?.let {
                Pair(it, searchResult.type)
            }
        }
        _moreUrls.update {
            moreUrls
        }

        _state.update { state ->
            state.copy(
                isMoreToLoad = moreUrls.isNotEmpty(),
                searchResults = list
                    .flatMap { it.data }
                    .toImmutableList()
            )
        }
    }

    private fun renderLoadMoreResult(list: List<SearchResult>) {
        renderLoading(false)
        val moreUrls = list.mapNotNull { searchResult ->
            searchResult.nextUrl?.let {
                Pair(it, searchResult.type)
            }
        }
        _moreUrls.update {
            moreUrls
        }

        _state.update { state ->
            state.copy(
                isMoreToLoad = moreUrls.isNotEmpty(),
                searchResults =
                    (state.searchResults.orEmpty() + list.flatMap { it.data }).toImmutableList()
            )
        }
    }

    private fun renderError(message: String?) {
        renderLoading(false)

        viewModelScope.launch {
            _effect.send(SearchUiContract.Effect.Error(message.orEmpty()))
        }
    }

    private fun renderCategorySelected(event: SearchUiContract.Event.OnCategorySelected) {
        _category.update {
            event.category.mapCategoryToSwapiTypes()
        }

        _state.update { state ->
            state.copy(
                categoryState = event.category
            )
        }
    }

    private fun renderOnEntryClicked(event: SearchUiContract.Event.OnEntryClicked) {
        viewModelScope.launch {
            _effect.send(SearchUiContract.Effect.Navigate(
                    url = event.entry.url,
                    title = event.entry.primaryName,
                    type = event.entry.toSwapiType()
                )
            )
        }
    }

    private fun renderLoading(isLoading: Boolean) {
        _state.update { state ->
            state.copy(
                isLoading = isLoading
            )
        }
    }

    private fun initLoadMore() {
        viewModelScope.launch {
            _loadMore
                .map {
                    _moreUrls.value
                }
                .filter { it.isNotEmpty() }
                .distinctUntilChanged()
                .debounce(500)
                .onEach { renderLoading(true) }
                .flatMapLatest {
                    loadMore(it)
                }
                .flowOn(Dispatchers.IO)
                .collect {
                    it.onSuccess { list ->
                        renderLoadMoreResult(list)
                    }.onFailure { t ->
                        renderError(t.message)
                    }
                }
        }
    }


    private fun SearchDto.toSwapiType(): SwapiType {
        return when(this) {
            is FilmDto -> SwapiType.FILMS
            is PersonDto -> SwapiType.PEOPLE
            is PlanetDto -> SwapiType.PLANETS
            is SpeciesDto -> SwapiType.SPECIES
            is StarshipDto -> SwapiType.STARSHIPS
            is VehicleDto -> SwapiType.VEHICLES
        }
    }

    private fun SearchUiContract.State.CategoryState.mapCategoryToSwapiTypes(): List<SwapiType> {
        return mutableListOf<SwapiType>().apply {
            if (this@mapCategoryToSwapiTypes.films) add(SwapiType.FILMS)
            if (this@mapCategoryToSwapiTypes.people) add(SwapiType.PEOPLE)
            if (this@mapCategoryToSwapiTypes.planets) add(SwapiType.PLANETS)
            if (this@mapCategoryToSwapiTypes.species) add(SwapiType.SPECIES)
            if (this@mapCategoryToSwapiTypes.starships) add(SwapiType.STARSHIPS)
            if (this@mapCategoryToSwapiTypes.vehicles) add(SwapiType.VEHICLES)
        }
    }
}