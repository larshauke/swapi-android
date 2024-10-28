package de.larshauke.swapi_android.core.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
sealed class SearchDto {
    abstract val url: Url
    abstract val keys: List<String>
    abstract val primaryName: String
    open val secondaryName: String? = null
}
