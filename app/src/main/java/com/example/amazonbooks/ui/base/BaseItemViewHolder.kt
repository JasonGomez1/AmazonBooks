package com.example.amazonbooks.ui.base

import android.util.Log
import androidx.lifecycle.*
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.example.amazonbooks.App
import com.example.amazonbooks.di.components.DaggerViewHolderComponent
import com.example.amazonbooks.di.components.ViewHolderComponent
import com.example.amazonbooks.ui.MainActivity
import com.example.amazonbooks.ui.MainViewModel
import javax.inject.Inject

abstract class BaseItemViewHolder<T : Data, VM : BaseItemViewModel<T>>(
    binding: ViewBinding
) : RecyclerView.ViewHolder(binding.root),
    LifecycleOwner {

    @Inject
    lateinit var viewModel: VM

    @Inject
    lateinit var mainViewModel: MainViewModel

    @Inject
    lateinit var viewModelStore: ViewModelStore

    @Inject
    lateinit var lifecycleRegistry: LifecycleRegistry

    @set:Inject
    var itemId: Int = -1

    override fun getLifecycle() = lifecycleRegistry

    open fun bind(data: T, id: Int) {
        onCreate(id)
        Log.d("BaseViewHolder binding", "ID: $itemId ViewModelStore: $viewModelStore")
        viewModel.updateData(data)
    }

    protected fun onCreate(id: Int) {
        injectDependencies(buildViewHolderComponent(id))
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
        mainViewModel.setViewHolderStore(itemId, viewModelStore)
    }

    private fun buildViewHolderComponent(id: Int) =
        DaggerViewHolderComponent
            .factory()
            .create(
                owner = this,
                id = id,
                appComponent = (itemView.context.applicationContext as App).appComponent,
                activityComponent = (itemView.context as MainActivity).activityComponent
            )

    abstract fun injectDependencies(component: ViewHolderComponent)

    protected abstract fun setupObservers()
}