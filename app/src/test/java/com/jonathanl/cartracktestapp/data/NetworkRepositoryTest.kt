package com.jonathanl.cartracktestapp.data

import com.jonathanl.cartracktestapp.data.model.WebsiteUser
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*

class NetworkRepositoryTest {

    private lateinit var repoUnderTest: NetworkRepository
    private val mockNetworkService = mockk<NetworkService>()
    private val testData1 = WebsiteUser()

    @Before
    fun setUp() {
        repoUnderTest = NetworkRepository(mockNetworkService)
    }

    @Test
    fun testGetValidWebsiteData() = runBlockingTest {
        coEvery { mockNetworkService.getUserList() } returns listOf(testData1)
        repoUnderTest.getWebsiteData()
        assertEquals(repoUnderTest.websiteData.value, listOf(testData1))
    }

    @Test
    fun testGetEmptyWebsiteData() = runBlockingTest {
        coEvery { mockNetworkService.getUserList() } returns listOf()
        repoUnderTest.getWebsiteData()
        assertEquals(repoUnderTest.websiteData.value, emptyList<WebsiteUser>())
    }

}