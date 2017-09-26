package net.chmielowski.androidstarter

import android.arch.persistence.room.Room
import android.content.Context
import dagger.Module
import dagger.Provides
import net.chmielowski.androidstarter.main.MainSubComponent
import net.chmielowski.androidstarter.room.AppDatabase
import javax.inject.Singleton


@Module(subcomponents = arrayOf(MainSubComponent::class))
class AppModule {
    @Provides
    fun provideContext(application: CustomApplication): Context {
        return application.applicationContext
    }

    @Singleton
    @Provides
    fun provideRoom(application: CustomApplication): AppDatabase {
        return Room.databaseBuilder(application,
                AppDatabase::class.java, "database-name").build()
    }
}
