package de.larshauke.swapi_android.search.domain.model

import de.larshauke.swapi_android.core.models.SearchDto
import de.larshauke.swapi_android.core.models.SwapiType

data class SearchResult(
    val nextUrl: String?,
    val data: List<SearchDto>,
    val type: SwapiType
)