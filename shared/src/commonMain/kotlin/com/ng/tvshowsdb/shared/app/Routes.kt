package com.ng.tvshowsdb.shared.app

import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hasRoute
import kotlinx.serialization.Serializable
import kotlin.reflect.KClass

sealed interface Routes {

    @Serializable
    data object Home : Routes

    @Serializable
    data class Movie(
        val id: Long
    ) : Routes
}

fun <T : Any> NavDestination?.isRoute(route: KClass<T>): Boolean =
    this?.hasRoute(route) == true
