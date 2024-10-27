package de.larshauke.swapi_android.search.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import de.larshauke.swapi_android.core.data.util.Url

@JsonClass(generateAdapter = true)
data class PaginatedResponse<T>(
    val count: Int,
    @Json(name = "next") val nextUrl: Url? = null,
    @Json(name = "previous") val previousUrl: Url? = null,
    val results: List<T>
)