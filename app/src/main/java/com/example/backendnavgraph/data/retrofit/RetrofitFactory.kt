package com.example.backendnavgraph.data.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitFactory {

    private val retrofit = Retrofit.Builder()
        .baseUrl("http://10.0.2.2:8080/")
        .addConverterFactory(
            GsonConverterFactory.create()
        )
        .build()

    val api: GraphApi =
        retrofit.create(GraphApi::class.java)
}
