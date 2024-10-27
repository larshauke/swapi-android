package de.larshauke.swapi_android.search.data.webservice

import de.larshauke.swapi_android.core.data.models.FilmDto
import de.larshauke.swapi_android.core.data.models.PersonDto
import de.larshauke.swapi_android.core.data.models.PlanetDto
import de.larshauke.swapi_android.core.data.models.SpeciesDto
import de.larshauke.swapi_android.core.data.models.StarshipDto
import de.larshauke.swapi_android.core.data.models.VehicleDto
import de.larshauke.swapi_android.search.data.model.PaginatedResponse
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url

interface SearchApi {

    @GET("people/")
    suspend fun searchPeople(
        @Query("search") query: String
    ): PaginatedResponse<PersonDto>


    @GET("films/")
    suspend fun searchFilms(
        @Query("search") query: String
    ): PaginatedResponse<FilmDto>

    @GET("planets/")
    suspend fun searchPlanets(
        @Query("search") query: String
    ): PaginatedResponse<PlanetDto>

    @GET("species/")
    suspend fun searchSpecies(
        @Query("search") query: String
    ): PaginatedResponse<SpeciesDto>

    @GET("starships/")
    suspend fun searchStarships(
        @Query("search") query: String
    ): PaginatedResponse<StarshipDto>

    @GET("vehicles/")
    suspend fun searchVehicles(
        @Query("search") query: String
    ): PaginatedResponse<VehicleDto>

    @GET
    suspend fun getPageOfPeople(@Url url: String): PaginatedResponse<PersonDto>

    @GET
    suspend fun getPageOfFilms(@Url url: String): PaginatedResponse<FilmDto>

    @GET
    suspend fun getPageOfPlanets(@Url url: String): PaginatedResponse<PlanetDto>

    @GET
    suspend fun getPageOfSpecies(@Url url: String): PaginatedResponse<SpeciesDto>

    @GET
    suspend fun getPageOfStarships(@Url url: String): PaginatedResponse<StarshipDto>

    @GET
    suspend fun getPageOfVehicles(@Url url: String): PaginatedResponse<VehicleDto>

}