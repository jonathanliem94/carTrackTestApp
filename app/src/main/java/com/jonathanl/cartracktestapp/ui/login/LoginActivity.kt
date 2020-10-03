package com.jonathanl.cartracktestapp.ui.login

import android.R
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.jonathanl.cartracktestapp.data.model.LoginResult
import com.jonathanl.cartracktestapp.databinding.ActivityLoginBinding
import com.jonathanl.cartracktestapp.utils.afterTextChanged
import dagger.hilt.android.AndroidEntryPoint
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList


@AndroidEntryPoint
class LoginActivity : AppCompatActivity() {

    @Inject lateinit var loginViewModel: LoginViewModel
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpCountrySpinner()
        subscribeToViewModel()
        setListeners()
    }

    private fun subscribeToViewModel() {
        binding.run {
            loginViewModel.loginFormState.observe(this@LoginActivity, Observer {
                val loginState = it ?: return@Observer

                // disable login button unless both username / password is valid
                login.isEnabled = loginState.isDataValid
                register.isEnabled = loginState.isDataValid

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

            loginViewModel.registerStatus.observe(this@LoginActivity, {
                if (it) showRegisterSuccess()
                else showRegisterFailed()
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
                register.setOnClickListener {
                    startRegister()
                }
            }
        }
    }

    private fun setUpCountrySpinner() {
        val locales: Array<Locale> = Locale.getAvailableLocales()
        val countries = ArrayList<String>()
        for (locale in locales) {
            val country: String = locale.displayCountry
            if (country.trim { it <= ' ' }.isNotEmpty() && !countries.contains(country)) {
                countries.add(country)
            }
        }
        countries.sort()
        val countryAdapter = ArrayAdapter(
            this,
            R.layout.simple_spinner_item, countries
        )
        countryAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item)
        binding.countrySpinner.adapter = countryAdapter
    }

    private fun startLogin() {
        binding.run {
            loading.visibility = View.VISIBLE
            loginViewModel.login(username.text.toString(), password.text.toString())
        }
    }

    private fun startRegister() {
        binding.run {
            loginViewModel.registerNewUser(username.text.toString(), password.text.toString(), countrySpinner.selectedItem.toString())
        }
    }

    private fun showLoginSuccess(username: String) {
        Snackbar.make(binding.root, "Logged in as $username", Snackbar.LENGTH_LONG).show()
    }

    private fun showLoginFailed(message: String) {
        Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
    }

    private fun showRegisterSuccess() {
        Snackbar.make(binding.root, "Register success", Snackbar.LENGTH_LONG).show()
    }

    private fun showRegisterFailed() {
        Snackbar.make(binding.root, "Register failed", Snackbar.LENGTH_SHORT).show()
    }
}
