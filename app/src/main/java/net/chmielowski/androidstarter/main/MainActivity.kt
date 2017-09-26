package net.chmielowski.androidstarter.main

import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jakewharton.rxbinding2.view.RxView
import dagger.android.AndroidInjection
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_task.view.*
import net.chmielowski.androidstarter.R
import net.chmielowski.androidstarter.room.AppDatabase
import net.chmielowski.androidstarter.room.Task
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModel: MainViewModel

    private val disposable = CompositeDisposable()

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        task_list.layoutManager = LinearLayoutManager(this)
        val adapter = TaskAdapter()
        task_list.adapter = adapter
        disposable.addAll(
                RxView.clicks(add)
                        .subscribe {
                            viewModel.addTask(new_task.text)
                        },
                viewModel.tasks()
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe { adapter.replaceTasks(it) })
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.clear()
    }
}

class TaskAdapter : RecyclerView.Adapter<TaskViewHolder>() {
    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.itemView.name.text = list[position].name
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int) = TaskViewHolder(view(parent))

    private fun view(parent: ViewGroup?) = LayoutInflater
            .from(parent?.context)
            .inflate(R.layout.item_task, parent, false)


    private var list = listOf<Task>()
    fun replaceTasks(tasks: List<Task>) {
        list = tasks
        notifyDataSetChanged()
    }
}

class TaskViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

class MainViewModel @Inject constructor(private val db: AppDatabase) {
    fun addTask(text: CharSequence) {
        val task = Task()
        task.name = text.toString()
        AsyncTask.execute {
            db.dao().insert(task)
        }
    }

    fun tasks(): Flowable<List<Task>> = db.dao().all
}
