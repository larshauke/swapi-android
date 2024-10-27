package de.larshauke.swapi_android.core.domain

enum class SwapiType(private val path: String) {
    FILMS("films"),
    PEOPLE("people"),
    PLANETS("planets"),
    SPECIES("species"),
    STARSHIPS("starships"),
    VEHICLES("vehicles");

    val url: String
        get() = "/$path/"
}