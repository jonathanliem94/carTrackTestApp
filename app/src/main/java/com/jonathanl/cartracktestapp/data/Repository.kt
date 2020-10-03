package com.jonathanl.cartracktestapp.data

import com.jonathanl.cartracktestapp.data.model.LoggedInUser
import com.jonathanl.cartracktestapp.data.model.LoginResult
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.StateFlow

/**
 * Class that requests authentication and user information from the remote data source and
 * maintains an in-memory cache of login status and user credentials information.
 */

class Repository(private val loginDAO: LoginDAO) {

    val loggedInStatus = ConflatedBroadcastChannel<LoginResult>(LoginResult.Failure("Initial value"))

    suspend fun logoutUser() {
        loggedInStatus.send(LoginResult.Failure("Logged out"))
    }

    suspend fun loginUser(usernameQuery: String, passwordQuery: String) {
        val result = loginDAO.findUser(usernameQuery, passwordQuery)
        val newLoginResult = result?.run {
            LoginResult.Success(this.displayName)
        } ?: LoginResult.Failure("Not Registered")
        loggedInStatus.send(newLoginResult)
    }

    suspend fun insertUser(user: LoggedInUser) {
        loginDAO.insert(user)
    }
}