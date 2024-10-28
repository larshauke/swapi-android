package de.larshauke.swapi_android.details.data.webservice

import de.larshauke.swapi_android.core.models.FilmDto
import de.larshauke.swapi_android.core.models.PersonDto
import de.larshauke.swapi_android.core.models.PlanetDto
import de.larshauke.swapi_android.core.models.SpeciesDto
import de.larshauke.swapi_android.core.models.StarshipDto
import de.larshauke.swapi_android.core.models.VehicleDto
import retrofit2.http.GET
import retrofit2.http.Url

interface DetailsApi {

    @GET
    suspend fun getFilmDetails(@Url url: String): FilmDto

    @GET
    suspend fun getPersonDetails(@Url url: String): PersonDto

    @GET
    suspend fun getPlanetDetails(@Url url: String): PlanetDto

    @GET
    suspend fun getSpeciesDetails(@Url url: String): SpeciesDto

    @GET
    suspend fun getStarshipDetails(@Url url: String): StarshipDto

    @GET
    suspend fun getVehicleDetails(@Url url: String): VehicleDto
}