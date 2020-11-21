package com.example.amazonbooks.di.components

import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModelStore
import com.example.amazonbooks.di.ActivityScope
import com.example.amazonbooks.di.modules.ActivityModule
import com.example.amazonbooks.ui.MainActivity
import dagger.BindsInstance
import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = [ActivityModule::class])
interface ActivityComponent {
    fun inject(activity: MainActivity)

    fun viewModelStore(): ViewModelStore

    @Subcomponent.Factory
    interface Factory {
        fun create(@BindsInstance componentActivity: ComponentActivity): ActivityComponent
    }
}