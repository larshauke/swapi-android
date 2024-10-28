package de.larshauke.swapi_android.ui.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import de.larshauke.swapi_android.R


sealed class Tab(
    val route: String,
    @StringRes val name: Int,
    @DrawableRes val icon: Int
) {
    data object SearchTab: Tab(
        route = "search_tab",
        name = R.string.search_title,
        icon = R.drawable.ic_search
    )

    data object CreditTab: Tab(
        route = "credit_tab",
        name = R.string.credits_title,
        icon = R.drawable.ic_info
    )

}