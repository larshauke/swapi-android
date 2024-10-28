package de.larshauke.swapi_android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.add
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import de.larshauke.swapi_android.ui.navigation.CreditNavGraph
import de.larshauke.swapi_android.ui.navigation.SearchNavGraph
import de.larshauke.swapi_android.ui.navigation.Tab
import de.larshauke.swapi_android.ui.theme.SwapiTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            Content()
        }
    }
}

@Preview
@Composable
fun Content() {
    SwapiTheme {
        val navController: NavHostController = rememberNavController()

        Surface (
            modifier = Modifier.fillMaxSize(),
        ) {
            Scaffold(
                contentWindowInsets = WindowInsets.systemBars
                    .add(WindowInsets.navigationBars
                        .only(WindowInsetsSides.Bottom)
                    ),
                bottomBar = {
                    TabView(
                        tabBarItems = listOf(
                            Tab.SearchTab,
                            Tab.CreditTab
                        ),
                        navController = navController
                    )
                }
            ) { innerPadding ->

                val bottomPadding = WindowInsets.navigationBars
                    .only(WindowInsetsSides.Bottom)
                    .asPaddingValues()

                NavHost(
                    modifier = Modifier
                        .padding(innerPadding)
                        .padding(bottomPadding)
                        .fillMaxSize(),
                    navController = navController,
                    startDestination = Tab.SearchTab.route
                ) {

                    composable(
                        route = Tab.SearchTab.route
                    ) {
                        SearchNavGraph()
                    }

                    composable(
                        route = Tab.CreditTab.route
                    ) {
                        CreditNavGraph()
                    }
                }
            }
        }
    }
}


@Composable
fun TabView(tabBarItems: List<Tab>, navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavigationBar {
        // looping over each tab to generate the views and navigation for each item
        tabBarItems.forEach { tabBarItem ->
            NavigationBarItem(
                selected = currentDestination?.hierarchy?.any { it.route == tabBarItem.route } == true,
                modifier = Modifier,
                icon = {
                    Icon(
                        painter = painterResource(tabBarItem.icon),
                        contentDescription = null
                    )
                },
                label = {
                    Text(text = stringResource(tabBarItem.name))
                },


                onClick = {
                    navController.navigate(tabBarItem.route) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}
