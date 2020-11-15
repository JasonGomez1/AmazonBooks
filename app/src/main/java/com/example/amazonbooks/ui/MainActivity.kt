package com.example.amazonbooks.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.amazonbooks.App
import com.example.amazonbooks.databinding.ActivityMainBinding
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    lateinit var binding: ActivityMainBinding
    private val bookAdapter: BookAdapter = BookAdapter()
    private val viewModel by viewModels<MainViewModel> {
        factory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        (application as App)
            .appComponent
            .activityComponent
            .create()
            .inject(this)
        binding.recyclerView.apply {
            layoutManager = GridLayoutManager(this@MainActivity, 2)
            adapter = bookAdapter
        }

        // Since BookAdapter implements ListAdapter, submitList will calculate the difference between
        // the old list and the new one. It calls the appropriate methods instead of just calling
        // notifyDataSetChanged()
        viewModel.books.observe(this) {
            bookAdapter.submitList(it)
        }
    }
}
