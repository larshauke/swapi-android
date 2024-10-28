package de.larshauke.swapi_android.search.domain.use_case

import de.larshauke.swapi_android.core.models.SwapiType
import de.larshauke.swapi_android.core.models.Url
import de.larshauke.swapi_android.search.domain.model.SearchResult
import de.larshauke.swapi_android.search.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface LoadMore {

    operator fun invoke(source: List<Pair<Url, SwapiType>>): Flow<Result<List<SearchResult>>>

}

class LoadMoreImpl
@Inject
constructor(
    private val searchRepository: SearchRepository
): LoadMore {

    override operator fun invoke(source: List<Pair<Url, SwapiType>>): Flow<Result<List<SearchResult>>> {
        return searchRepository.loadMore(source)
            .map {
                Result.success(
                    it.map { response ->
                        SearchResult(
                            nextUrl = response.first.nextUrl,
                            data = response.first.results,
                            type = response.second
                        )
                    }
                )
            }
            .catch { emit(Result.failure(it)) }
    }
}

