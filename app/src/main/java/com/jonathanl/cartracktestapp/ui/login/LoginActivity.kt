package com.jonathanl.cartracktestapp.ui.login

import androidx.lifecycle.Observer
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import com.jonathanl.cartracktestapp.data.model.LoginResult

import com.jonathanl.cartracktestapp.databinding.ActivityLoginBinding
import com.jonathanl.cartracktestapp.utils.afterTextChanged
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    @Inject lateinit var loginViewModel: LoginViewModel
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        subscribeToViewModel()
        setListeners()
    }

    private fun subscribeToViewModel() {
        binding.run {
            loginViewModel.loginFormState.observe(this@LoginActivity, Observer {
                val loginState = it ?: return@Observer

                // disable login button unless both username / password is valid
                login.isEnabled = loginState.isDataValid

                if (loginState.usernameError != null) {
                    username.error = getString(loginState.usernameError)
                }
                if (loginState.passwordError != null) {
                    password.error = getString(loginState.passwordError)
                }
            })

            loginViewModel.loginStatus.observe(this@LoginActivity, {
                loading.visibility = View.GONE
                when (it) {
                    is LoginResult.Failure -> {
                        showLoginFailed(it.failure)
                    }
                    is LoginResult.Success -> {
                        showLoginSuccess(it.username)
                    }
                }
            })
        }
    }

    private fun setListeners() {
        binding.run {
            username.afterTextChanged {
                loginViewModel.loginDataChanged(
                    username.text.toString(),
                    password.text.toString()
                )
            }

            password.apply {
                afterTextChanged {
                    loginViewModel.loginDataChanged(
                        username.text.toString(),
                        password.text.toString()
                    )
                }

                setOnEditorActionListener { _, actionId, _ ->
                    when (actionId) {
                        EditorInfo.IME_ACTION_DONE ->
                            startLogin()
                    }
                    false
                }

                login.setOnClickListener {
                    startLogin()
                }
            }
        }
    }

    private fun startLogin() {
        binding.run {
            loading.visibility = View.VISIBLE
            loginViewModel.login(username.text.toString(), password.text.toString())
        }
    }

    private fun showLoginSuccess(username: String) {
        Toast.makeText(applicationContext, "Logged in as $username", Toast.LENGTH_LONG).show()
    }

    private fun showLoginFailed(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }
}
