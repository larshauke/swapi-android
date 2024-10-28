package de.larshauke.swapi_android.details.domain.repository

import de.larshauke.swapi_android.core.models.FilmDto
import de.larshauke.swapi_android.core.models.PersonDto
import de.larshauke.swapi_android.core.models.PlanetDto
import de.larshauke.swapi_android.core.models.SpeciesDto
import de.larshauke.swapi_android.core.models.StarshipDto
import de.larshauke.swapi_android.core.models.Url
import de.larshauke.swapi_android.core.models.VehicleDto

interface DetailsRepository {

    suspend fun getFilmDetails(url: Url): FilmDto

    suspend fun getPersonDetails(url: Url): PersonDto

    suspend fun getPlanetDetails(url: Url): PlanetDto

    suspend fun getSpeciesDetails(url: Url): SpeciesDto

    suspend fun getStarshipDetails(url: Url): StarshipDto

    suspend fun getVehicleDetails(url: Url): VehicleDto
}