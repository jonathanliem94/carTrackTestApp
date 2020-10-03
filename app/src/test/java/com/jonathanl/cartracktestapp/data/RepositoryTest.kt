package com.jonathanl.cartracktestapp.data

import com.jonathanl.cartracktestapp.data.model.LoggedInUser
import com.jonathanl.cartracktestapp.data.model.LoginResult
import io.mockk.*
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*

class RepositoryTest {

    private lateinit var repoUnderTest: Repository
    private val mockDAO = mockk<LoginDAO>()
    private val testUser1 = LoggedInUser("francois", "klsjdiur", "France")

    @Before
    fun setUp() {
        repoUnderTest = Repository(mockDAO)
    }

    @Test
    fun logInWithCorrectUser() = runBlockingTest {
        coEvery { mockDAO.findUser(any(), any()) } returns testUser1
        repoUnderTest.loginUser("francois", "klsjdiur")
        assertEquals(repoUnderTest.loggedInStatus.value, LoginResult.Success(testUser1.displayName))
    }

    @Test
    fun logInWithInvalidUser() = runBlockingTest {
        coEvery { mockDAO.findUser(any(), any()) } returns null
        repoUnderTest.loginUser("francois", "klsjdiur")
        assertEquals(repoUnderTest.loggedInStatus.value, LoginResult.Failure("Not Registered"))
    }

    @Test
    fun logoutUser() = runBlockingTest {
        repoUnderTest.logoutUser()
        assertEquals(repoUnderTest.loggedInStatus.value, LoginResult.Failure("Logged out"))
    }

    @Test
    fun insertUserSuccess() = runBlockingTest {
        coEvery { mockDAO.insert(any()) } returns listOf(1L)
        repoUnderTest.insertUser(testUser1)
        assertEquals(repoUnderTest.registerStatus.value, true)
    }

    @Test
    fun insertUserFailure() = runBlockingTest {
        coEvery { mockDAO.insert(any()) } returns listOf()
        repoUnderTest.insertUser(testUser1)
        assertEquals(repoUnderTest.registerStatus.value, false)
    }
}