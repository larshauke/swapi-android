package de.larshauke.swapi_android.search.domain.model

import de.larshauke.swapi_android.core.data.models.SearchDto

data class SearchResult(
        val nextUrl: String?,
        val data: List<SearchDto>
)