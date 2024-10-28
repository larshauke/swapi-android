package de.larshauke.swapi_android.search.data.repository

import de.larshauke.swapi_android.core.models.SearchDto
import de.larshauke.swapi_android.core.models.SwapiType
import de.larshauke.swapi_android.core.models.Url
import de.larshauke.swapi_android.search.data.model.PaginatedResponse
import de.larshauke.swapi_android.search.data.webservice.SearchApi
import de.larshauke.swapi_android.search.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class SearchRepositoryImpl
@Inject
constructor(
    private val api: SearchApi
): SearchRepository {

    override fun search(
        query: String,
        types: List<SwapiType>
    ): Flow<Array<Pair<PaginatedResponse<out SearchDto>, SwapiType>>> {
        val list = mutableListOf<Flow<Pair<PaginatedResponse<out SearchDto>, SwapiType>>>().apply {
            types.forEach { type ->
                when (type) {
                    SwapiType.FILMS -> add(flow { emit(Pair(api.searchFilms(query), SwapiType.FILMS)) })
                    SwapiType.PEOPLE -> add(flow { emit(Pair(api.searchPeople(query), SwapiType.PEOPLE)) })
                    SwapiType.PLANETS -> add(flow { emit(Pair(api.searchPlanets(query), SwapiType.PLANETS)) })
                    SwapiType.SPECIES -> add(flow { emit(Pair(api.searchSpecies(query), SwapiType.SPECIES)) })
                    SwapiType.STARSHIPS -> add(flow { emit(Pair(api.searchStarships(query), SwapiType.STARSHIPS)) })
                    SwapiType.VEHICLES -> add(flow { emit(Pair(api.searchVehicles(query), SwapiType.VEHICLES)) })
                }
            }
        }

        return combine(list) { it }
    }

    override fun loadMore(
        source: List<Pair<Url, SwapiType>>
    ): Flow<Array<Pair<PaginatedResponse<out SearchDto>, SwapiType>>> {
        val list = mutableListOf<Flow<Pair<PaginatedResponse<out SearchDto>, SwapiType>>>().apply {
            source.forEach { pair ->
                when (pair.second) {
                    SwapiType.FILMS -> add(flow { emit(Pair(api.getPageOfFilms(pair.first), SwapiType.FILMS)) })
                    SwapiType.PEOPLE -> add(flow { emit(Pair(api.getPageOfPeople(pair.first), SwapiType.PEOPLE)) })
                    SwapiType.PLANETS -> add(flow { emit(Pair(api.getPageOfPlanets(pair.first), SwapiType.PLANETS)) })
                    SwapiType.SPECIES -> add(flow { emit(Pair(api.getPageOfSpecies(pair.first), SwapiType.SPECIES)) })
                    SwapiType.STARSHIPS -> add(flow { emit(Pair(api.getPageOfStarships(pair.first), SwapiType.STARSHIPS)) })
                    SwapiType.VEHICLES -> add(flow { emit(Pair(api.getPageOfVehicles(pair.first), SwapiType.VEHICLES)) })
                }
            }
        }

        return combine(list) { it }
    }

}
