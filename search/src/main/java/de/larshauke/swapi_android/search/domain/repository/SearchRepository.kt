package de.larshauke.swapi_android.search.domain.repository

import de.larshauke.swapi_android.core.models.SearchDto
import de.larshauke.swapi_android.core.models.SwapiType
import de.larshauke.swapi_android.core.models.Url
import de.larshauke.swapi_android.search.data.model.PaginatedResponse
import kotlinx.coroutines.flow.Flow

interface SearchRepository {

    fun search(query: String, types: List<SwapiType>): Flow<Array<Pair<PaginatedResponse<out SearchDto>, SwapiType>>>

    fun loadMore(source: List<Pair<Url, SwapiType>>): Flow<Array<Pair<PaginatedResponse<out SearchDto>, SwapiType>>>

}