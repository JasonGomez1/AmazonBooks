package com.example.amazonbooks.ui.base

import androidx.lifecycle.*
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.amazonbooks.App
import com.example.amazonbooks.di.components.DaggerViewHolderComponent
import com.example.amazonbooks.di.components.ViewHolderComponent
import javax.inject.Inject

abstract class BaseItemViewHolder<T : Data, VM : BaseItemViewModel<T>>(
    binding: ViewBinding
) : RecyclerView.ViewHolder(binding.root),
    LifecycleOwner {

    init {
        onCreate()
    }

    @Inject
    lateinit var viewModel: VM

    @Inject
    lateinit var lifecycleRegistry: LifecycleRegistry

    override fun getLifecycle() = lifecycleRegistry

    open fun bind(data: T) {
        viewModel.updateData(data)
    }

    protected fun onCreate() {
        injectDependencies(buildViewHolderComponent())
        lifecycleRegistry.currentState = Lifecycle.State.INITIALIZED
        lifecycleRegistry.currentState = Lifecycle.State.CREATED
        setupObservers()
    }

    fun onStart() {
        lifecycleRegistry.currentState = Lifecycle.State.STARTED
        lifecycleRegistry.currentState = Lifecycle.State.RESUMED
    }

    fun onStop() {
        lifecycleRegistry.currentState = Lifecycle.State.STARTED
        lifecycleRegistry.currentState = Lifecycle.State.CREATED
    }

    fun onDestroy() {
        lifecycleRegistry.currentState = Lifecycle.State.DESTROYED
    }

    private fun buildViewHolderComponent() =
        DaggerViewHolderComponent
            .factory()
            .create(this, (itemView.context.applicationContext as App).appComponent)

    abstract fun injectDependencies(component: ViewHolderComponent)

    protected abstract fun setupObservers()
}