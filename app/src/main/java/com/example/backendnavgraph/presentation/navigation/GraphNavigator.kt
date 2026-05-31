package com.example.backendnavgraph.presentation.navigation

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.backendnavgraph.data.network.NavigationGraphDto

class GraphNavigator(
    graph: NavigationGraphDto
) {

    var currentRoute by mutableStateOf(
        graph.startDestination
    )
        private set

    var currentPayload: Map<String, Any?> = emptyMap()
        private set

    fun sendEvent(
        graph: NavigationGraphDto,
        event: String,
        payload: Map<String, Any?> = emptyMap()
    ) {
//        navigator.sendEvent(
//            graph = graph,
//            event = "add_note"
//        )
        Log.d("GraphNavigator", "graph ${graph}")
        Log.d("GraphNavigator", "currentRoute ${currentRoute}")


        val transition =
            graph.transitions.firstOrNull {
                it.from == currentRoute &&
                        it.event == event
            }

        Log.d("GraphNavigator", "transition ${transition}")

        if (transition != null) {
            currentRoute = transition.to
            currentPayload = payload
        }
    }
}