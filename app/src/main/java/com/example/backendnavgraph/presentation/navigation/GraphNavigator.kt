package com.example.backendnavgraph.presentation.navigation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.mutableStateListOf
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
        val current = backStack.last()

        val transition = graph.transitions.firstOrNull {
            it.from == current &&
                    it.event == event
        } ?: return

        val target = transition.to
        val index = backStack.lastIndexOf(target)

        if (index != -1) {
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