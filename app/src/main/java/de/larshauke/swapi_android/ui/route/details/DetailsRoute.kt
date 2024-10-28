package de.larshauke.swapi_android.ui.route.details

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import de.larshauke.swapi_android.R
import de.larshauke.swapi_android.core.models.SwapiType
import de.larshauke.swapi_android.ui.common.AlertDialog
import de.larshauke.swapi_android.ui.common.ObserveAsEvents
import de.larshauke.swapi_android.ui.navigation.Destinations
import de.larshauke.swapi_android.ui.route.details.compose.DetailsScreen
import de.larshauke.swapi_android.ui.route.details.model.DetailsUiContract
import de.larshauke.swapi_android.ui.route.details.model.DetailsViewModel
import kotlin.reflect.typeOf

fun NavGraphBuilder.detailsRoute(
    navigateUp: () -> Unit,
    showDetails: (Destinations.DetailDestination) -> Unit
) {
    composable<Destinations.DetailDestination>(
        typeMap = mapOf(
            typeOf<SwapiType>() to NavType.EnumType(SwapiType::class.java)
        )
    ) {
        val args by remember { mutableStateOf(it.toRoute<Destinations.DetailDestination>()) }

        val viewModel = hiltViewModel<DetailsViewModel, DetailsViewModel.Factory>(
            creationCallback = { factory -> factory.create(args) }
        )
        val state by viewModel.state.collectAsStateWithLifecycle(viewModel.initialValue)

        DetailsScreen(
            state = state,
            onEvent = viewModel::onEvent
        )

        ObserveAsEvents(
            flow = viewModel.effect,
            content = { effect ->
                when {
                    effect is DetailsUiContract.Effect.Error -> {
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
                    is DetailsUiContract.Effect.NavigateUp -> {
                        navigateUp()
                    }
                    is DetailsUiContract.Effect.Navigate -> {
                        showDetails(
                            Destinations.DetailDestination(
                                url = effect.url,
                                title = null,
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