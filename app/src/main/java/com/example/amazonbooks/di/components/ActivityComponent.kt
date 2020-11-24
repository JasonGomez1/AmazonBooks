package com.example.amazonbooks.di.components

import com.example.amazonbooks.di.ActivityScope
import com.example.amazonbooks.di.modules.ActivityModule
import com.example.amazonbooks.ui.MainActivity
import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = [ActivityModule::class])
interface ActivityComponent {
    fun inject(activity: MainActivity)

    @Subcomponent.Factory
    interface Factory {
        fun create(): ActivityComponent
    }
}
