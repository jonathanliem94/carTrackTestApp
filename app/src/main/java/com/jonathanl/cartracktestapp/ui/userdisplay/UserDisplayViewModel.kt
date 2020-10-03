package com.jonathanl.cartracktestapp.ui.userdisplay

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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

    private val _userDataList = MutableLiveData<List<WebsiteUser>>()
    val userDataList: LiveData<List<WebsiteUser>> = _userDataList

    init {
        coRoutineScope.launch {
            subscribeToWebsiteData()
        }
    }

    private suspend fun subscribeToWebsiteData() {
        networkRepository.websiteData.collect{
            _userDataList.postValue(it)
        }
    }

    fun fetchData() {
        coRoutineScope.launch {
            networkRepository.getWebsiteData()
        }
    }


}