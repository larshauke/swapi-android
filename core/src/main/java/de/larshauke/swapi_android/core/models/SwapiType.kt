package de.larshauke.swapi_android.core.models

import kotlinx.serialization.Serializable

@Serializable
enum class SwapiType {
    FILMS,
    PEOPLE,
    PLANETS,
    SPECIES,
    STARSHIPS,
    VEHICLES
}