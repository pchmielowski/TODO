package net.chmielowski.androidstarter

import android.content.Context
import dagger.Module
import dagger.Provides
import net.chmielowski.androidstarter.main.MainSubComponent

@Module(subcomponents = arrayOf(MainSubComponent::class))
class AppModule {
    @Provides
    fun provideContext(application: CustomApplication): Context {
        return application.applicationContext
    }
}
