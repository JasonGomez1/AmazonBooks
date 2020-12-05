package com.example.amazonbooks.ui

import android.app.NotificationChannel
import android.app.NotificationManager
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.amazonbooks.App
import com.example.amazonbooks.R
import com.example.amazonbooks.databinding.ActivityMainBinding
import com.example.amazonbooks.utils.sendNotification
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    @Inject
    lateinit var viewModel: MainViewModel
    lateinit var binding: ActivityMainBinding
    val activityComponent by lazy {
        (application as App)
            .appComponent
            .activityComponent
            .create(this)
    }

    // TODO inject adapter instead
    private val bookAdapter: BookAdapter = BookAdapter(lifecycle)

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
            bookAdapter.submitList(it)
            sendDbNotification()
        }
    }

    private fun sendDbNotification() {
        // We create the notification channel first, then send the notification.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                getString(R.string.notification_channel_id),
                getString(R.string.notification_channel_name),
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                enableLights(true)
                lightColor = Color.GREEN
                enableVibration(true)
                description = getString(R.string.notification_channel_description)
            }

            ContextCompat
                .getSystemService(applicationContext, NotificationManager::class.java)
                ?.apply {
                    createNotificationChannel(notificationChannel)
                    sendNotification(getString(R.string.db_updated), applicationContext)
                }
        }
    }
}
