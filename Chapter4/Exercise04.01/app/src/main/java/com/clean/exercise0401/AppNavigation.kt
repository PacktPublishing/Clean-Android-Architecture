package com.clean.exercise0401

private const val ROUTE_USERS = "users"
private const val ROUTE_USER = "users/%s"
private const val ARG_USER_NAME = "name"

sealed class AppNavigation(val route: String, val argumentName: String = "") {

    object Users : AppNavigation(ROUTE_USERS)

    object User : AppNavigation(String.format(ROUTE_USER, "{$ARG_USER_NAME}"), ARG_USER_NAME) {

        fun routeForName(name: String) = String.format(ROUTE_USER, name)
    }
}