package com.example.backendnavgraph.data.network

data class NavigationGraphDto(
    val version: Int,
    val startDestination: String,
    val screens: List<ScreenDto>,
    val transitions: List<TransitionDto>
)

data class ScreenDto(
    val route: String,
    val presentation: String? = null,
    val variant: String? = null
)

data class TransitionDto(
    val from: String,
    val event: String,
    val to: String
)

fun NavigationGraphDto.screen(
    route: String
): ScreenDto? =
    screens.firstOrNull {
        it.route == route
    }

fun NavigationGraphDto.transition(
    from: String,
    event: String
): TransitionDto? =
    transitions.firstOrNull {
        it.from == from &&
                it.event == event
    }
