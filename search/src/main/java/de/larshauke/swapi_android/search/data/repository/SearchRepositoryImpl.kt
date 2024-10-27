package de.larshauke.swapi_android.search.data.repository

import de.larshauke.swapi_android.core.data.models.FilmDto
import de.larshauke.swapi_android.core.data.models.PersonDto
import de.larshauke.swapi_android.core.data.models.PlanetDto
import de.larshauke.swapi_android.core.data.models.SearchDto
import de.larshauke.swapi_android.core.data.models.SpeciesDto
import de.larshauke.swapi_android.core.data.models.StarshipDto
import de.larshauke.swapi_android.core.data.models.VehicleDto
import de.larshauke.swapi_android.core.domain.SwapiType
import de.larshauke.swapi_android.search.data.model.PaginatedResponse
import de.larshauke.swapi_android.search.data.webservice.SearchApi
import de.larshauke.swapi_android.search.domain.model.SearchResult
import de.larshauke.swapi_android.search.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SearchRepositoryImpl
@Inject
constructor(
    private val searchApi: SearchApi
): SearchRepository {

    override fun search(
        query: String,
        types: List<SwapiType>
    ): Flow<Array<PaginatedResponse<out SearchDto>>> {
        val list = mutableListOf<Flow<PaginatedResponse<out SearchDto>>>().apply {
            types.forEach {
                when (it) {
                    SwapiType.FILMS -> add(flow { emit(searchApi.searchFilms(query)) })
                    SwapiType.PEOPLE -> add(flow { emit(searchApi.searchPeople(query)) })
                    SwapiType.PLANETS -> add(flow { emit(searchApi.searchPlanets(query)) })
                    SwapiType.SPECIES -> add(flow { emit(searchApi.searchSpecies(query)) })
                    SwapiType.STARSHIPS -> add(flow { emit(searchApi.searchStarships(query)) })
                    SwapiType.VEHICLES -> add(flow { emit(searchApi.searchVehicles(query)) })
                }
            }
        }

        return combine(list) { it }
    }

}
