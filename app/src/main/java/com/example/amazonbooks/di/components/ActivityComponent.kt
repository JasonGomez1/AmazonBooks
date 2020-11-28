package com.example.amazonbooks.di.components

import androidx.lifecycle.ViewModelStoreOwner
import com.example.amazonbooks.di.ActivityScope
import com.example.amazonbooks.di.modules.ActivityModule
import com.example.amazonbooks.ui.MainActivity
import com.example.amazonbooks.ui.MainViewModel
import dagger.BindsInstance
import dagger.Subcomponent

@ActivityScope
@Subcomponent(modules = [ActivityModule::class])
interface ActivityComponent {
    fun inject(activity: MainActivity)
    fun mainViewModel(): MainViewModel

    @Subcomponent.Factory
    interface Factory {
        fun create(@BindsInstance storeOwner: ViewModelStoreOwner): ActivityComponent
    }
}
