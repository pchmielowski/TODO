package net.chmielowski.androidstarter.main

import dagger.Subcomponent
import dagger.android.AndroidInjector


@Subcomponent
interface MainSubComponent : AndroidInjector<MainActivity> {
    @Subcomponent.Builder
    abstract class Builder : AndroidInjector.Builder<MainActivity>()
}