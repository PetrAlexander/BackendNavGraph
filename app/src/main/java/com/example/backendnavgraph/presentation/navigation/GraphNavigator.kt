package com.example.backendnavgraph.presentation.navigation

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import com.example.backendnavgraph.data.network.NavigationGraphDto

class GraphNavigator(
    graph: NavigationGraphDto
) {
    private val backStack = mutableStateListOf<String>()

    var currentPayload: Map<String, Any?> = emptyMap()
        private set

    init {
        backStack.add(graph.startDestination)
    }

    @RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
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
        Log.d("GraphNavigator", "currentRoute ${backStack}")

        val current = backStack.last()

        val transition = graph.transitions.firstOrNull {
            it.from == current &&
                    it.event == event
        } ?: return

        val target = transition.to
        val index = backStack.lastIndexOf(target)

        Log.d("GraphNavigator", "transition ${transition}")

        if (index != -1) {
            // если экран уже был — режем стек до него
            while (backStack.size > index + 1) {
                backStack.removeLast()
            }
        } else {
            backStack.add(target)
        }

        currentPayload = payload
    }

    fun getCurrentRoute(): String {
        return backStack.last()
    }

    @RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
    fun onBack(): Boolean {

        if (backStack.size <= 1) {
            return false
        }

        backStack.removeLast()
        return true
    }
}