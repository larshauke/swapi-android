package de.larshauke.swapi_android.ui.route.search

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import de.larshauke.swapi_android.R
import de.larshauke.swapi_android.ui.common.AlertDialog
import de.larshauke.swapi_android.ui.common.ObserveAsEvents
import de.larshauke.swapi_android.ui.navigation.Destinations
import de.larshauke.swapi_android.ui.route.search.compose.SearchScreen
import de.larshauke.swapi_android.ui.route.search.model.SearchUiContract
import de.larshauke.swapi_android.ui.route.search.model.SearchViewModel


fun NavGraphBuilder.searchRoute(
    showDetails: (Destinations.DetailDestination) -> Unit
) {
    composable<Destinations.SearchDestination> {
        val viewModel = hiltViewModel<SearchViewModel>()
        val state by viewModel.state.collectAsStateWithLifecycle(viewModel.initialValue)

        SearchScreen(
            state = state,
            onEvent = viewModel::onEvent,
        )

        ObserveAsEvents(
            flow = viewModel.effect,
            content = { effect ->
                when {
                    effect is SearchUiContract.Effect.Error -> {
                        AlertDialog(
                            onDismissRequest = {},
                            dismissRes = R.string.dismiss_btn,
                            titleRes = R.string.dialog_error_title,
                            textPlain = effect.message
                        )
                    }
                }
            },
            onEvent = { effect ->
                when (effect) {
                    is SearchUiContract.Effect.Navigate -> {
                        showDetails(
                            Destinations.DetailDestination(
                                url = effect.url,
                                title = effect.title,
                                swapiType = effect.type
                            )
                        )
                    }

                    else -> {
                        // do nothing
                    }
                }
            }
        )
    }

}