package de.larshauke.swapi_android.ui.route.details.util

import de.larshauke.swapi_android.core.models.FilmDto
import de.larshauke.swapi_android.core.models.PersonDto
import de.larshauke.swapi_android.core.models.PlanetDto
import de.larshauke.swapi_android.core.models.SearchDto
import de.larshauke.swapi_android.core.models.SpeciesDto
import de.larshauke.swapi_android.core.models.StarshipDto
import de.larshauke.swapi_android.core.models.VehicleDto
import de.larshauke.swapi_android.ui.route.details.model.DetailsUiContract

fun SearchDto.toResource(): DetailsUiContract.State.Resource {
    return when (this) {
        is FilmDto -> DetailsUiContract.State.Resource.Film(
            title = title,
            episodeId = episodeId,
            openingCrawl = openingCrawl,
            director = director,
            producer = producer,
            releaseDate = releaseDate,
            species = species,
            starships = starships,
            vehicles = vehicles,
            characters = characters,
            planets = planets,
            created = created,
            edited = edited
        )

        is PersonDto -> DetailsUiContract.State.Resource.Person(
            name = name,
            birthYear = birthYear,
            eyeColor = eyeColor,
            gender = gender,
            hairColor = hairColor,
            height = height,
            mass = mass,
            skinColor = skinColor,
            homeworld = homeworld,
            films = films,
            species = species,
            starships = starships,
            vehicles = vehicles,
            created = created,
            edited = edited
        )

        is PlanetDto -> DetailsUiContract.State.Resource.Planet(
            name = name,
            diameter = diameter,
            rotationPeriod = rotationPeriod,
            orbitalPeriod = orbitalPeriod,
            gravity = gravity,
            population = population,
            climate = climate,
            terrain = terrain,
            surfaceWater = surfaceWater,
            residents = residents,
            films = films,
            created = created,
            edited = edited
        )

        is SpeciesDto -> DetailsUiContract.State.Resource.Species(
            name = name,
            classification = classification,
            designation = designation,
            averageHeight = averageHeight,
            averageLifespan = averageLifespan,
            eyeColors = eyeColors,
            hairColors = hairColors,
            skinColors = skinColors,
            language = language,
            homeworld = homeworld,
            people = people,
            films = films,
            created = created,
            edited = edited
        )

        is StarshipDto -> DetailsUiContract.State.Resource.Starship(
            name = name,
            model = model,
            starshipClass = starshipClass,
            manufacturer = manufacturer,
            costInCredits = costInCredits,
            length = length,
            crew = crew,
            passengers = passengers,
            maxAtmospheringSpeed = maxAtmospheringSpeed,
            hyperdriveRating = hyperdriveRating,
            mglt = mglt,
            cargoCapacity = cargoCapacity,
            consumables = consumables,
            films = films,
            pilots = pilots,
            created = created,
            edited = edited
        )

        is VehicleDto -> DetailsUiContract.State.Resource.Vehicle(
            name = name,
            model = model,
            vehicleClass = vehicleClass,
            manufacturer = manufacturer,
            length = length,
            costInCredits = costInCredits,
            crew = crew,
            passengers = passengers,
            maxAtmospheringSpeed = maxAtmospheringSpeed,
            cargoCapacity = cargoCapacity,
            consumables = consumables,
            films = films,
            pilots = pilots,
            created = created,
            edited = edited
        )
    }
}