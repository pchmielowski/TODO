package net.chmielowski.androidstarter

import android.app.Activity
import dagger.Binds
import dagger.Module
import dagger.android.ActivityKey
import dagger.android.AndroidInjector
import dagger.multibindings.IntoMap
import net.chmielowski.androidstarter.main.MainActivity
import net.chmielowski.androidstarter.main.MainSubComponent


@Module
abstract class BuildersModule {
    @Binds
    @IntoMap
    @ActivityKey(MainActivity::class)
    internal abstract
    fun bindFeatureActivityInjectorFactory(builder: MainSubComponent.Builder):
            AndroidInjector.Factory<out Activity>

    // Add more bindings here for other sub components
}