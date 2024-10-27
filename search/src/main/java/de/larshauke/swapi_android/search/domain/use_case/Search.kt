package de.larshauke.swapi_android.search.domain.use_case

import de.larshauke.swapi_android.core.domain.SwapiType
import de.larshauke.swapi_android.search.domain.model.SearchResult
import de.larshauke.swapi_android.search.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface Search {

    operator fun invoke(query: String, types: List<SwapiType>): Flow<Result<List<SearchResult>>>
}

class SearchImpl
@Inject
constructor(
    private val searchRepository: SearchRepository
): Search {

    override operator fun invoke(query: String, types: List<SwapiType>): Flow<Result<List<SearchResult>>> {
        return searchRepository.search(query, types)
            .map {
                Result.success(
                    it.map { response ->
                        SearchResult(
                            nextUrl = response.nextUrl,
                            data = response.results
                        )
                    }
                )

            }
    }
}

