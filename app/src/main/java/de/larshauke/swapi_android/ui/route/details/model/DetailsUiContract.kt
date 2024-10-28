package de.larshauke.swapi_android.ui.route.details.model

import androidx.compose.runtime.Stable
import de.larshauke.swapi_android.core.models.SwapiType
import de.larshauke.swapi_android.core.models.Url
import java.time.ZonedDateTime
import java.util.Date

sealed interface DetailsUiContract {

    @Stable
    data class State(
        val isLoading: Boolean,
        val data: Resource?
    ): DetailsUiContract {

        sealed class Resource {
            open val title: String? = null

            data class Person(
                val name: String,
                val birthYear: String,
                val eyeColor: String,
                val gender: String,
                val hairColor: String,
                val height: String,
                val mass: String,
                val skinColor: String,
                val homeworld: Url,
                val films: List<Url>,
                val species: List<Url>,
                val starships: List<Url>,
                val vehicles: List<Url>,
                val created: ZonedDateTime,
                val edited: ZonedDateTime
            ) : Resource() {
                override val title: String
                    get() = name
            }

            data class Film(
                override val title: String,
                val episodeId: Int,
                val openingCrawl: String,
                val director: String,
                val producer: String,
                val releaseDate: Date,
                val species: List<Url>,
                val starships: List<Url>,
                val vehicles: List<Url>,
                val characters: List<Url>,
                val planets: List<Url>,
                val created: ZonedDateTime,
                val edited: ZonedDateTime
            ) : Resource()

            data class Planet(
                val name: String,
                val diameter: String,
                val rotationPeriod: String,
                val orbitalPeriod: String,
                val gravity: String,
                val population: String,
                val climate: String,
                val terrain: String,
                val surfaceWater: String,
                val residents: List<Url>,
                val films: List<Url>,
                val created: ZonedDateTime,
                val edited: ZonedDateTime
            )  : Resource() {
                override val title: String
                    get() = name
            }

            data class Species(
                val name: String,
                val classification: String,
                val designation: String,
                val averageHeight: String,
                val averageLifespan: String,
                val eyeColors: String,
                val hairColors: String,
                val skinColors: String,
                val language: String,
                val homeworld: Url,
                val people: List<Url>,
                val films: List<Url>,
                val created: ZonedDateTime,
                val edited: ZonedDateTime
            )  : Resource() {
                override val title: String
                    get() = name
            }

            data class Starship(
                val name: String,
                val model: String,
                val starshipClass: String,
                val manufacturer: String,
                val costInCredits: String,
                val length: String,
                val crew: String,
                val passengers: String,
                val maxAtmospheringSpeed: String,
                val hyperdriveRating: String,
                val mglt: String,
                val cargoCapacity: String,
                val consumables: String,
                val films: List<Url>,
                val pilots: List<Url>,
                val created: ZonedDateTime,
                val edited: ZonedDateTime
            ) : Resource() {
                override val title: String
                    get() = name
            }

            data class Vehicle(
                val name: String,
                val model: String,
                val vehicleClass: String,
                val manufacturer: String,
                val length: String,
                val costInCredits: String,
                val crew: String,
                val passengers: String,
                val maxAtmospheringSpeed: String,
                val cargoCapacity: String,
                val consumables: String,
                val films: List<Url>,
                val pilots: List<Url>,
                val created: ZonedDateTime,
                val edited: ZonedDateTime
            )  : Resource() {
                override val title: String
                    get() = name
            }
        }
    }

    sealed interface Event: DetailsUiContract {
        data class OnLoadUrls(val swapiType: SwapiType, val urls: List<Url>): Event
        data object OnNavigateUp: Event
    }

    sealed interface Effect: DetailsUiContract {
        data object NavigateUp: Effect
        data class Navigate(val url: String, val type: SwapiType): Effect
        data class Error(val message: String): Effect
    }
}