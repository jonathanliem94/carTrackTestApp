package com.jonathanl.cartracktestapp.ui.userdisplay

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.jonathanl.cartracktestapp.databinding.ActivityUserDisplayBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class UserDisplayActivity: AppCompatActivity() {

    @Inject lateinit var viewModel: UserDisplayViewModel
    @Inject lateinit var viewAdapter: UserDisplayViewAdapter
    private lateinit var binding: ActivityUserDisplayBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDisplayBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initRecyclerView()
        subscribeToViewModel()
        fetchData()
    }

    private fun initRecyclerView() {
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@UserDisplayActivity)
            adapter = viewAdapter
        }
    }

    private fun subscribeToViewModel() {
        viewModel.userDataList.observe(this, {
            viewAdapter.setNewDataSet(it)
            binding.progressBar.visibility = View.INVISIBLE
        })
    }

    private fun fetchData() {
        viewModel.fetchData()
    }

}