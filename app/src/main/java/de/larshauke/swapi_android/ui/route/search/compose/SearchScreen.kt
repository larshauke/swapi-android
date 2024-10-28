package de.larshauke.swapi_android.ui.route.search.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import de.larshauke.swapi_android.R
import de.larshauke.swapi_android.core.models.SearchDto
import de.larshauke.swapi_android.ui.route.search.model.SearchUiContract
import de.larshauke.swapi_android.ui.theme.SwapiTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    state: SearchUiContract.State,
    onEvent: (SearchUiContract.Event) -> Unit
) {
    var query by rememberSaveable { mutableStateOf("") }

    Scaffold(
        contentWindowInsets = WindowInsets.systemBars.only(sides = WindowInsetsSides.Top),
        modifier = Modifier.fillMaxSize(),
        topBar = {
            SearchBar(
                windowInsets = WindowInsets(0.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 0.dp, horizontal = 16.dp),
                inputField = {
                    SearchBarDefaults.InputField(
                        query = query,
                        onQueryChange = {
                            query = it
                            onEvent(SearchUiContract.Event.OnQueryChanged(query = query))
                        },
                        onSearch = {},
                        expanded = false,
                        onExpandedChange = { },
                        placeholder = { Text(text = stringResource(id = R.string.search_text)) },
                        leadingIcon = { Icon(painter = painterResource(id = R.drawable.ic_search), contentDescription = null) },
                        trailingIcon = null,
                    )
                },
                colors = SearchBarDefaults.colors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainer,
                ),
                expanded = false,
                onExpandedChange = {},
                content = {}
            )
        }
    ) { innerPadding ->

        Column (
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top
        ) {
            CategoryRow(
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                    .background(
                        color = MaterialTheme.colorScheme.surfaceContainer,
                        shape = MaterialTheme.shapes.extraLarge
                    ),
                toggleStates = state.categoryState,
                onEvent = onEvent
            )

            if (state.searchResults == null && !state.isLoading)
                Box(
                    modifier = Modifier.fillMaxSize()
                        .background(Color.Transparent),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = stringResource(R.string.search_hint))
                }

            if (state.isLoading)
                Box(
                    modifier = Modifier.fillMaxSize()
                        .background(Color.Transparent),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }

            if (state.searchResults?.isEmpty() == true)
                Box(
                    modifier = Modifier.fillMaxSize()
                        .background(Color.Transparent),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = stringResource(R.string.search_no_results))
                }

            if (state.searchResults?.isNotEmpty() == true) {
                LazyColumn(
                    modifier = Modifier.fillMaxSize()
                ) {
                    items(state.searchResults) {
                        ResultItem(
                            data = it,
                            onEvent = onEvent
                        )
                    }

                    item {
                        Button(
                            modifier = Modifier.fillMaxWidth()
                                .padding(horizontal = 16.dp,),
                            onClick = { onEvent(SearchUiContract.Event.OnMore)},
                            content = { Text(text = stringResource(R.string.search_load_more))},
                            enabled = state.isMoreToLoad
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ResultItem(
    modifier: Modifier = Modifier,
    data: SearchDto,
    onEvent: (SearchUiContract.Event) -> Unit
) {
    ListItem(
        modifier = modifier
            .fillMaxWidth()
            .clickable { onEvent(SearchUiContract.Event.OnEntryClicked(data)) },
        headlineContent = { Text(text = data.primaryName) },
        supportingContent = {
            data.secondaryName?.let {
                Text(text = it) }
            }
    )
    HorizontalDivider()
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun CategoryRow(
    modifier: Modifier,
    toggleStates: SearchUiContract.State.CategoryState,
    onEvent: (SearchUiContract.Event) -> Unit
) {
    FlowRow(
        modifier = modifier.fillMaxWidth()
            .wrapContentHeight(),
        maxItemsInEachRow = 3,
        horizontalArrangement = Arrangement.Center,
    ) {
        FilterChip(
            modifier = Modifier.padding(horizontal = 2.dp),
            selected = toggleStates.films,
            label = {
                Text(text = "films")
            },
            onClick = {
                onEvent(
                    SearchUiContract.Event.OnCategorySelected(
                        toggleStates.copy(films = !toggleStates.films)
                    )
                )
            }
        )
        FilterChip(
            modifier = Modifier.padding(horizontal = 2.dp),
            selected = toggleStates.people,
            label = {
                Text(text = "people")
            },
            onClick = {
                onEvent(
                    SearchUiContract.Event.OnCategorySelected(
                        toggleStates.copy(people = !toggleStates.people)
                    )
                )
            }
        )
        FilterChip(
            modifier = Modifier.padding(horizontal = 2.dp),
            selected = toggleStates.planets,
            label = {
                Text(text = "planets")
            },
            onClick = {
                onEvent(
                    SearchUiContract.Event.OnCategorySelected(
                        toggleStates.copy(planets = !toggleStates.planets)
                    )
                )
            }
        )
        FilterChip(
            modifier = Modifier.padding(horizontal = 2.dp),
            selected = toggleStates.species,
            label = {
                Text(text = "species")
            },
            onClick = {
                onEvent(
                    SearchUiContract.Event.OnCategorySelected(
                        toggleStates.copy(species = !toggleStates.species)
                    )
                )
            }
        )
        FilterChip(
            modifier = Modifier.padding(horizontal = 2.dp),
            selected = toggleStates.starships,
            label = {
                Text(text = "starships")
            },
            onClick = {
                onEvent(
                    SearchUiContract.Event.OnCategorySelected(
                        toggleStates.copy(starships = !toggleStates.starships)
                    )
                )
            }
        )
        FilterChip(
            modifier = Modifier.padding(horizontal = 2.dp),
            selected = toggleStates.vehicles,
            label = {
                Text(text = "vehicles")
            },
            onClick = {
                onEvent(
                    SearchUiContract.Event.OnCategorySelected(
                        toggleStates.copy(vehicles = !toggleStates.vehicles)
                    )
                )
            }
        )
    }
}


@Preview
@Composable
private fun SearchScreenPreview() {
    SwapiTheme {
        SearchScreen(
            state = SearchUiContract.State(
                isLoading = false,
                searchResults = null,
                categoryState = SearchUiContract.State.CategoryState(
                    films = false,
                    people = false,
                    planets = false,
                    species = false,
                    starships = false,
                    vehicles = false
                ),
                isMoreToLoad = true
            ),
            onEvent = {},
        )
    }
}