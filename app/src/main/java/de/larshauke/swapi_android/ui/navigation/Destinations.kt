package de.larshauke.swapi_android.ui.navigation

import de.larshauke.swapi_android.core.models.SwapiType
import kotlinx.serialization.Serializable

@Serializable
sealed class Destinations {

    @Serializable
    data object SearchDestination: Destinations()

    @Serializable
    data object BrowseDestination: Destinations()

    @Serializable
    data class DetailDestination(
        val url: String? = null,
        val title: String? = null,
        val swapiType: SwapiType? = null
    ): Destinations()

    @Serializable
    data object CreditDestination: Destinations()
}