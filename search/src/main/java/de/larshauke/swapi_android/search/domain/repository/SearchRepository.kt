package de.larshauke.swapi_android.search.domain.repository

import de.larshauke.swapi_android.core.data.models.SearchDto
import de.larshauke.swapi_android.core.domain.SwapiType
import de.larshauke.swapi_android.search.data.model.PaginatedResponse
import kotlinx.coroutines.flow.Flow

interface SearchRepository {

    fun search(query: String, types: List<SwapiType>): Flow<Array<PaginatedResponse<out SearchDto>>>

}