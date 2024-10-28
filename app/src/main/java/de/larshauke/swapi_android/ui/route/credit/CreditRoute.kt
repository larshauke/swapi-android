package de.larshauke.swapi_android.ui.route.credit

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import de.larshauke.swapi_android.R
import de.larshauke.swapi_android.ui.common.SwapiTopAppBar
import de.larshauke.swapi_android.ui.navigation.Destinations

fun NavGraphBuilder.creditRoute() {
    composable<Destinations.CreditDestination> {

        Scaffold(
            topBar = {
                SwapiTopAppBar(
                    title = R.string.credits_title,
                )
            }
        ) { innerPadding ->

            val credits = stringArrayResource(id = R.array.credits)

            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize()
            ) {
                // ok there are better solutions, i am sure. something automatic...
                credits.forEach {
                    Text(
                        modifier = Modifier.fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        text = it
                    )
                }
            }
        }
    }
}


