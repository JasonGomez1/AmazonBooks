package com.example.amazonbooks.di.components

import androidx.lifecycle.LifecycleOwner
import com.example.amazonbooks.di.ViewHolderScope
import com.example.amazonbooks.di.modules.ViewHolderModule
import com.example.amazonbooks.ui.BookViewHolder
import dagger.BindsInstance
import dagger.Component

@ViewHolderScope
@Component(
    modules = [ViewHolderModule::class],
    dependencies = [AppComponent::class]
)
interface ViewHolderComponent {
    fun inject(vh: BookViewHolder)

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance owner: LifecycleOwner,
            appComponent: AppComponent
        ): ViewHolderComponent
    }
}
