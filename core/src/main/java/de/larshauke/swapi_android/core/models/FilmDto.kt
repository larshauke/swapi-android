package de.larshauke.swapi_android.core.models

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import java.time.ZonedDateTime
import java.util.Date

@JsonClass(generateAdapter = true)
data class FilmDto(
    val title: String,
    @Json(name = "episode_id") val episodeId: Int,
    @Json(name = "opening_crawl") val openingCrawl: String,
    @Json(name = "release_date") val releaseDate: Date,
    val director: String,
    val producer: String,
    val species: List<Url>,
    val starships: List<Url>,
    val vehicles: List<Url>,
    val characters: List<Url>,
    val planets: List<Url>,
    override val url: String,
    val created: ZonedDateTime,
    val edited: ZonedDateTime
): SearchDto() {
    override val keys: List<String> =
        listOf(title)

    override val primaryName: String
        get() = title
}