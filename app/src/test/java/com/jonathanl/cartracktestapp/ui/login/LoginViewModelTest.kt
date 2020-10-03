package com.jonathanl.cartracktestapp.ui.login

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.jonathanl.cartracktestapp.R
import com.jonathanl.cartracktestapp.data.Repository
import com.jonathanl.cartracktestapp.data.model.LoggedInUser
import com.jonathanl.cartracktestapp.data.model.LoginResult
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.StateFlow
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class LoginViewModelTest {

    private lateinit var viewModelUnderTest: LoginViewModel
    private val mockRepo = mockk<Repository>()
    private val mockLoggedInStatus = mockk<ConflatedBroadcastChannel<LoginResult>>(relaxed = true)
    private val mockObserver = mockk<Observer<LoginFormState>>()
    private val testName = "francois"
    private val testPw = "klsjdiur"
    private val testCountry = "France"

    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        every { mockRepo.loggedInStatus } returns mockLoggedInStatus
        every { mockObserver.onChanged(any()) } returns Unit
        viewModelUnderTest = LoginViewModel(mockRepo)
        viewModelUnderTest.loginFormState.observeForever(mockObserver)
    }

    @Test
    fun login() {
        coEvery { mockRepo.loginUser(any(), any()) } returns Unit
        viewModelUnderTest.login(testName, testPw)
        coVerify { mockRepo.loginUser(testName, testPw) }
    }

    @Test
    fun registerNewUser() {
        coEvery { mockRepo.insertUser(any()) } returns Unit
        viewModelUnderTest.registerNewUser(testName, testPw, testCountry)
        coVerify {
            mockRepo.insertUser(
                LoggedInUser("francois", "klsjdiur", "France")
            )
        }
    }

    @Test
    fun loginDataUserNameInvalid() {
        viewModelUnderTest.loginDataChanged("", testPw)
        assertEquals(
            viewModelUnderTest.loginFormState.value,
            LoginFormState(usernameError = R.string.invalid_username)
        )
    }

    @Test
    fun loginDataPasswordInvalid() {
        viewModelUnderTest.loginDataChanged(testName, "123")
        assertEquals(
            viewModelUnderTest.loginFormState.value,
            LoginFormState(passwordError = R.string.invalid_password)
        )
    }

    @Test
    fun loginDataValid() {
        viewModelUnderTest.loginDataChanged(testName, testPw)
        assertEquals(
            viewModelUnderTest.loginFormState.value,
            LoginFormState(isDataValid = true)
        )
    }
}