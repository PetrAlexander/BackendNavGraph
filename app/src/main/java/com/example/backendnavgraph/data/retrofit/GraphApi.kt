package com.example.backendnavgraph.data.retrofit

import com.example.backendnavgraph.data.network.NavigationGraphDto
import retrofit2.http.GET

interface GraphApi {

    @GET("graph")
    suspend fun getGraph(): NavigationGraphDto
}
