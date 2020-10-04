package com.jonathanl.cartracktestapp.ui.userdisplay

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.jonathanl.cartracktestapp.MainCoroutineScopeRule
import com.jonathanl.cartracktestapp.data.NetworkRepository
import com.jonathanl.cartracktestapp.data.model.WebsiteUser
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class UserDisplayViewModelTest {

    private lateinit var viewModelUnderTest: UserDisplayViewModel
    private val mockRepo = mockk<NetworkRepository>()
    private val mockStateFlow = mockk<StateFlow<List<WebsiteUser>>>(relaxed = true)
    private val mockObserver = mockk<Observer<List<WebsiteUser>>>()

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val coroutineScope = MainCoroutineScopeRule()

    @Before
    fun setUp() {
        every { mockRepo.websiteData } returns mockStateFlow
        every { mockObserver.onChanged(any()) } returns Unit
        viewModelUnderTest = UserDisplayViewModel(mockRepo)
        viewModelUnderTest.userDataList.observeForever(mockObserver)
    }

    @Test
    fun fetchData() = coroutineScope.runBlockingTest {
        coEvery { mockRepo.getWebsiteData() } returns Unit
        viewModelUnderTest.fetchData()
        coVerify { mockRepo.getWebsiteData() }
    }
}