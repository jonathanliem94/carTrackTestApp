package com.jonathanl.cartracktestapp.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.util.Patterns

import com.jonathanl.cartracktestapp.R
import com.jonathanl.cartracktestapp.data.Repository
import com.jonathanl.cartracktestapp.data.model.LoggedInUser
import com.jonathanl.cartracktestapp.data.model.LoginResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: Repository) : ViewModel() {

    private val databaseJob: Job = Job()
    private val coRoutineScope = CoroutineScope(Dispatchers.IO + databaseJob)

    private val _loginForm = MutableLiveData<LoginFormState>()
    val loginFormState: LiveData<LoginFormState> = _loginForm

    private val _loginStatus = MutableLiveData<LoginResult>()
    val loginStatus: LiveData<LoginResult> = _loginStatus

    private val _registerStatus = MutableLiveData<Boolean>()
    val registerStatus: LiveData<Boolean> = _registerStatus

    init {
        coRoutineScope.launch {
            launch { observeLoginStatus() }
            launch { observeRegisterStatus() }
        }
    }

    private suspend fun observeLoginStatus() {
        val subscription = repository.loggedInStatus.openSubscription()
        subscription.receiveAsFlow().collect {
            _loginStatus.postValue(it)
        }
    }

    private suspend fun observeRegisterStatus() {
        val subscription = repository.registerStatus.openSubscription()
        subscription.receiveAsFlow().collect {
            _registerStatus.postValue(it)
        }
    }

    fun login(username: String, password: String) {
        coRoutineScope.launch {
            repository.loginUser(username, password)
        }
    }

    fun registerNewUser(username: String, password: String, country: String) {
        val newUser = LoggedInUser(username, password, country)
        coRoutineScope.launch {
            repository.insertUser(newUser)
        }
    }

    fun loginDataChanged(username: String, password: String) {
        if (!isUserNameValid(username)) {
            _loginForm.value = LoginFormState(usernameError = R.string.invalid_username)
        } else if (!isPasswordValid(password)) {
            _loginForm.value = LoginFormState(passwordError = R.string.invalid_password)
        } else {
            _loginForm.value = LoginFormState(isDataValid = true)
        }
    }

    // A placeholder username validation check
    private fun isUserNameValid(username: String): Boolean {
        return if (username.contains('@')) {
            Patterns.EMAIL_ADDRESS.matcher(username).matches()
        } else {
            username.isNotBlank()
        }
    }

    // A placeholder password validation check
    private fun isPasswordValid(password: String): Boolean {
        return password.length > 7
    }
}