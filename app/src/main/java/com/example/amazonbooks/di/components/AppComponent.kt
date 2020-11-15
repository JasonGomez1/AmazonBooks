package com.example.amazonbooks.di.components

import android.content.Context
import com.example.amazonbooks.App
import com.example.amazonbooks.di.modules.AppModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {
    fun inject(app: App)

    // Here we're stating ActivityComponent is a child component of AppComponent
    val activityComponent: ActivityComponent.Factory

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }
}
