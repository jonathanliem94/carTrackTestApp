package com.jonathanl.cartracktestapp.data.model

sealed class LoginResult {

    data class Success(val username: String) : LoginResult()
    data class Failure(val failure: String) : LoginResult()

}