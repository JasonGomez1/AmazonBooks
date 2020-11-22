package com.example.amazonbooks.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.amazonbooks.App
import com.example.amazonbooks.databinding.ActivityMainBinding
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    lateinit var binding: ActivityMainBinding
    val activityComponent by lazy {
        (application as App)
            .appComponent
            .activityComponent
            .create(this)
    }

    // TODO inject adapter instead
    private val bookAdapter: BookAdapter = BookAdapter(lifecycle)
    private val viewModel by viewModels<MainViewModel> {
        factory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        activityComponent.inject(this)
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = bookAdapter
        }

        // Since BookAdapter implements ListAdapter, submitList will calculate the difference between
        // the old list and the new one. It calls the appropriate methods instead of just calling
        // notifyDataSetChanged()
        viewModel.books.observe(this) {
            Log.d("MainActivity", "In observe")
            bookAdapter.submitList(it)
        }
    }
}
