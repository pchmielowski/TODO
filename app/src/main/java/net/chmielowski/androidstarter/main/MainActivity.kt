package net.chmielowski.androidstarter.main

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
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.item_task.view.*
import net.chmielowski.androidstarter.R
import net.chmielowski.androidstarter.room.AppDatabase
import net.chmielowski.androidstarter.room.Task
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var tasks: Tasks

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
                        .observeOn(Schedulers.io())
                        .subscribe {
                            tasks.add(new_task.text)
                        },
                tasks.all()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe { adapter.replaceTasks(it) },
                adapter.itemDone()
                        .observeOn(Schedulers.io())
                        .subscribe {
                            tasks.markAsDone(it)
                        })
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.clear()
    }
}

class TaskAdapter : RecyclerView.Adapter<TaskViewHolder>() {
    override fun getItemCount(): Int = list.size

    private val doneSubject: Subject<Task> = PublishSubject.create()

    fun itemDone(): Observable<Task> = doneSubject

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.itemView.name.text = list[position].name
        RxView.clicks(holder.itemView.done)
                .map { list[position] }
                .subscribe(doneSubject)
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

class Tasks @Inject constructor(private val db: AppDatabase) {
    fun add(text: CharSequence) {
        db.dao().insert(Task(text.toString()))
    }

    fun all(): Flowable<List<Task>> = db.dao().all.subscribeOn(Schedulers.io())

    fun markAsDone(task: Task) {
        task.done = true
        db.dao().update(task)
    }
}
