package com.jonathanl.cartracktestapp.ui.userdisplay

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.jonathanl.cartracktestapp.data.NetworkRepository
import com.jonathanl.cartracktestapp.data.model.WebsiteUser
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class UserDisplayViewModel(private val networkRepository: NetworkRepository) {

    private val networkJob: Job = Job()
    private val coRoutineScope = CoroutineScope(Dispatchers.IO + networkJob)

    val userDataList: LiveData<List<WebsiteUser>> = liveData {
        networkRepository.websiteData.collect{
            emit(it)
        }
    }

    fun fetchData() {
        coRoutineScope.launch {
            networkRepository.getWebsiteData()
        }
    }


}