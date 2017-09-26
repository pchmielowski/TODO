package net.chmielowski.androidstarter.main

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import dagger.android.AndroidInjection
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import net.chmielowski.androidstarter.R
import net.chmielowski.androidstarter.room.AppDatabase
import net.chmielowski.androidstarter.room.User
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel.hello()
                .map { it.map { it.lastName }.reduceRight { a, b -> a + b } }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    txt ->
                    text.text = txt
                }
    }
}

class MainViewModel @Inject constructor(val db: AppDatabase) {
    fun hello(): Flowable<MutableList<User>> = db.userDao().all.subscribeOn(Schedulers.io())
}
