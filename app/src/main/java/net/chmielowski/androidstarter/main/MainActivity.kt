package net.chmielowski.androidstarter.main

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import dagger.android.AndroidInjection
import io.reactivex.Completable
import io.reactivex.schedulers.Schedulers
import net.chmielowski.androidstarter.R
import net.chmielowski.androidstarter.room.AppDatabase
import net.chmielowski.androidstarter.room.User
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var room: AppDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val user = User()
        user.firstName = "Jeremy"
        user.lastName = "Clarkson"

        Completable.fromCallable { room.userDao().insertAll(user) }
                .subscribeOn(Schedulers.io())
                .andThen(room.userDao().all)
                .subscribe {
                    it.forEach {
                        Log.d("pchm", it.firstName + it.lastName)
                    }
                }
    }
}
