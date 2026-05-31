package com.example.backendnavgraph.data.network

data class NavigationGraph(
    val startDestination: String,
    val screens: List<Screen>
)

data class Screen(
    val route: String,
    val type: String
)
