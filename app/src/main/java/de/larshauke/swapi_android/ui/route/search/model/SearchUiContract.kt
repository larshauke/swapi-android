package de.larshauke.swapi_android.ui.route.search.model

import androidx.compose.runtime.Stable
import de.larshauke.swapi_android.core.models.SearchDto
import de.larshauke.swapi_android.core.models.SwapiType
import kotlinx.collections.immutable.ImmutableList

sealed interface SearchUiContract {

    @Stable
    data class State(
        val isLoading: Boolean,
        val searchResults: ImmutableList<SearchDto>?,
        val categoryState: CategoryState,
        val isMoreToLoad: Boolean
    ): SearchUiContract {

        @Stable
        data class CategoryState(
            val films: Boolean,
            val people: Boolean,
            val planets: Boolean,
            val species: Boolean,
            val starships: Boolean,
            val vehicles: Boolean,
        )
    }

    sealed interface Event: SearchUiContract {
        data class OnQueryChanged(val query: String): Event
        data class OnCategorySelected(val category: State.CategoryState): Event
        data class OnEntryClicked(val entry: SearchDto): Event
        data object OnMore: Event
    }

    sealed interface Effect: SearchUiContract {
        data class Navigate(
            val url: String,
            val title: String,
            val type: SwapiType
        ): Effect

        data class Error(val message: String): Effect
    }
}