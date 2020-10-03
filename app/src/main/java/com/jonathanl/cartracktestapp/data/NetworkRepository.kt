package com.jonathanl.cartracktestapp.data

import com.jonathanl.cartracktestapp.data.model.WebsiteUser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class NetworkRepository(private val service: NetworkService) {

    private val _websiteData = MutableStateFlow<List<WebsiteUser>>(listOf()) // private mutable state flow
    val websiteData: StateFlow<List<WebsiteUser>> get() = _websiteData

    suspend fun getWebsiteData() {
        val result = service.getUserList()
        _websiteData.value = result
    }

}