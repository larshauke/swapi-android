package de.larshauke.swapi_android.details.data.repository

import de.larshauke.swapi_android.core.models.FilmDto
import de.larshauke.swapi_android.core.models.PersonDto
import de.larshauke.swapi_android.core.models.PlanetDto
import de.larshauke.swapi_android.core.models.SpeciesDto
import de.larshauke.swapi_android.core.models.StarshipDto
import de.larshauke.swapi_android.core.models.Url
import de.larshauke.swapi_android.core.models.VehicleDto
import de.larshauke.swapi_android.details.data.webservice.DetailsApi
import de.larshauke.swapi_android.details.domain.repository.DetailsRepository
import javax.inject.Inject

class DetailsRepositoryImpl
@Inject
constructor(
    private val api: DetailsApi
): DetailsRepository {

    override suspend fun getFilmDetails(url: Url): FilmDto = api.getFilmDetails(url)

    override suspend fun getPersonDetails(url: Url): PersonDto = api.getPersonDetails(url)

    override suspend fun getPlanetDetails(url: Url): PlanetDto = api.getPlanetDetails(url)

    override suspend fun getSpeciesDetails(url: Url): SpeciesDto = api.getSpeciesDetails(url)

    override suspend fun getStarshipDetails(url: Url): StarshipDto = api.getStarshipDetails(url)

    override suspend fun getVehicleDetails(url: Url): VehicleDto = api.getVehicleDetails(url)

}