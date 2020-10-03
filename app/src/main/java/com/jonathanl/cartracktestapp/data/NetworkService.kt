package com.jonathanl.cartracktestapp.data

import com.jonathanl.cartracktestapp.data.model.WebsiteUser
import retrofit2.http.GET


const val BASE_URL = "https://jsonplaceholder.typicode.com/"

interface NetworkService {

    @GET("/users")
    suspend fun getUserList(): List<WebsiteUser>

}