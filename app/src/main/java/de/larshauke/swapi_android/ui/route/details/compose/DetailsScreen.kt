package de.larshauke.swapi_android.ui.route.details.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import de.larshauke.swapi_android.R
import de.larshauke.swapi_android.core.models.Url
import de.larshauke.swapi_android.ui.common.SwapiTopAppBar
import de.larshauke.swapi_android.ui.route.details.model.DetailsUiContract.Event
import de.larshauke.swapi_android.ui.route.details.model.DetailsUiContract.State
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun DetailsScreen(
    state: State,
    onEvent: (Event) -> Unit
) {

    Scaffold(
        contentWindowInsets = WindowInsets.systemBars.only(sides = WindowInsetsSides.Top),
        modifier = Modifier.fillMaxSize(),
        topBar = {
            SwapiTopAppBar(
                title = R.string.details_title_fallback,
                titlePlain = state.data?.title,
                navigateUp = { onEvent(Event.OnNavigateUp) }
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier.fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(innerPadding)
        ) {

            state.data?.let {
                ResourceContent(it)
            }

            if (state.isLoading)
                Box(
                    modifier = Modifier.fillMaxSize()
                        .background(Color.Transparent),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
        }
    }
}

@Composable
fun ResourceContent(resource: State.Resource) {
    when (resource) {
        is State.Resource.Person -> PersonContent(resource)
        is State.Resource.Film -> FilmContent(resource)
        is State.Resource.Planet -> PlanetContent(resource)
        is State.Resource.Species -> SpeciesContent(resource)
        is State.Resource.Starship -> StarshipContent(resource)
        is State.Resource.Vehicle -> VehicleContent(resource)
    }
}

@Composable
fun PersonContent(person: State.Resource.Person) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Name: ${person.name}")
        Text("Birth Year: ${person.birthYear}")
        Text("Gender: ${person.gender}")
        Text("Height: ${person.height}")
        Text("Mass: ${person.mass}")
        Text("Eye Color: ${person.eyeColor}")
        Text("Hair Color: ${person.hairColor}")
        Text("Homeworld: ${person.homeworld}")

        Spacer(modifier = Modifier.height(8.dp))
        UrlList("Films", person.films)
        UrlList("Species", person.species)
        UrlList("Starships", person.starships)
        UrlList("Vehicles", person.vehicles)
    }
}

@Composable
fun FilmContent(film: State.Resource.Film) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Title: ${film.title}")
        Text("Episode: ${film.episodeId}")
        Text("Director: ${film.director}")
        Text("Producer: ${film.producer}")
        Text("Release Date: ${formatDateToString(film.releaseDate)}")
        Text("Opening Crawl: ${film.openingCrawl}")

        Spacer(modifier = Modifier.height(8.dp))
        UrlList("Species", film.species)
        UrlList("Starships", film.starships)
        UrlList("Vehicles", film.vehicles)
        UrlList("Characters", film.characters)
        UrlList("Planets", film.planets)
    }
}

private fun formatDateToString(date: Date): String {
    val formatter = SimpleDateFormat("yyyy/MM/dd", Locale.getDefault())
    return formatter.format(date)
}

@Composable
fun PlanetContent(planet: State.Resource.Planet) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Name: ${planet.name}")
        Text("Diameter: ${planet.diameter}")
        Text("Gravity: ${planet.gravity}")
        Text("Population: ${planet.population}")
        Text("Climate: ${planet.climate}")
        Text("Terrain: ${planet.terrain}")
        Text("Surface Water: ${planet.surfaceWater}")

        Spacer(modifier = Modifier.height(8.dp))
        UrlList("Residents", planet.residents)
        UrlList("Films", planet.films)
    }
}

@Composable
fun SpeciesContent(species: State.Resource.Species) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Name: ${species.name}")
        Text("Classification: ${species.classification}")
        Text("Designation: ${species.designation}")
        Text("Average Height: ${species.averageHeight}")
        Text("Average Lifespan: ${species.averageLifespan}")
        Text("Language: ${species.language}")
        Text("Homeworld: ${species.homeworld}")

        Spacer(modifier = Modifier.height(8.dp))
        UrlList("People", species.people)
        UrlList("Films", species.films)
    }
}

@Composable
fun StarshipContent(starship: State.Resource.Starship) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Name: ${starship.name}")
        Text("Model: ${starship.model}")
        Text("Class: ${starship.starshipClass}")
        Text("Manufacturer: ${starship.manufacturer}")
        Text("Cost: ${starship.costInCredits}")
        Text("Length: ${starship.length}")
        Text("Crew: ${starship.crew}")
        Text("Passengers: ${starship.passengers}")

        Spacer(modifier = Modifier.height(8.dp))
        UrlList("Films", starship.films)
        UrlList("Pilots", starship.pilots)
    }
}

@Composable
fun VehicleContent(vehicle: State.Resource.Vehicle) {
    Column(modifier = Modifier.padding(16.dp)) {
        Text("Name: ${vehicle.name}")
        Text("Model: ${vehicle.model}")
        Text("Class: ${vehicle.vehicleClass}")
        Text("Manufacturer: ${vehicle.manufacturer}")
        Text("Cost: ${vehicle.costInCredits}")
        Text("Length: ${vehicle.length}")
        Text("Max Speed: ${vehicle.maxAtmospheringSpeed}")
        Text("Cargo Capacity: ${vehicle.cargoCapacity}")

        Spacer(modifier = Modifier.height(8.dp))
        UrlList("Films", vehicle.films)
        UrlList("Pilots", vehicle.pilots)
    }
}


@Composable
fun UrlList(
    label: String,
    urls: List<Url>
) {
    if (urls.isNotEmpty()) {
        Text("$label:")
        urls.forEach { url ->
            Text(" - $url", modifier = Modifier.padding(start = 8.dp))
        }
    }
}
