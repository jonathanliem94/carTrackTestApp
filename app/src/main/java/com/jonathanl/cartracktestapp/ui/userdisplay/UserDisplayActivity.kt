package com.jonathanl.cartracktestapp.ui.userdisplay

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.jonathanl.cartracktestapp.databinding.ActivityUserDisplayBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class UserDisplayActivity: AppCompatActivity() {

    @Inject lateinit var viewModel: UserDisplayViewModel
    private lateinit var binding: ActivityUserDisplayBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDisplayBinding.inflate(layoutInflater)
        setContentView(binding.root)
        subscribeToViewModel()
        setListeners()
        fetchData()
    }

    private fun subscribeToViewModel() {

    }

    private fun setListeners() {

    }

    private fun fetchData() {
        viewModel.fetchData()
    }


}