package de.larshauke.swapi_android.ui.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import de.larshauke.swapi_android.ui.route.credit.creditRoute
import de.larshauke.swapi_android.ui.route.details.detailsRoute
import de.larshauke.swapi_android.ui.route.search.searchRoute


@Composable
fun SearchNavGraph(
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        modifier = Modifier.fillMaxSize(),
        navController = navController,
        startDestination = Destinations.SearchDestination
    ) {

        searchRoute(
            showDetails = { navController.navigate(it) }
        )

        detailsRoute(
            navigateUp = { navController.navigateUp() },
            showDetails = { navController.navigate(it) }
        )
    }
}

@Composable
fun CreditNavGraph(
    navController: NavHostController = rememberNavController(),
) {
    NavHost(
        modifier = Modifier.fillMaxSize(),
        navController = navController,
        startDestination = Destinations.CreditDestination
    ) {

        creditRoute()
    }
}