package de.larshauke.swapi_android.core.data.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
sealed class SearchDto {
    abstract val key: List<String>
}
