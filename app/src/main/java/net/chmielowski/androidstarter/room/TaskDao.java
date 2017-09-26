package net.chmielowski.androidstarter.room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface TaskDao {
    @Query("SELECT * FROM Task WHERE done = 0 ORDER BY task.uid DESC")
    Flowable<List<Task>> getAll();

    @Insert
    void insert(Task task);

    @Update
    void update(Task task);
}

