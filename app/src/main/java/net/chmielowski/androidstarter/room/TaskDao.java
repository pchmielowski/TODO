package net.chmielowski.androidstarter.room;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import io.reactivex.Flowable;

@Dao
public interface TaskDao {
    @Query("SELECT * FROM Task ORDER BY task.uid DESC")
    Flowable<List<Task>> getAll();

    @Insert
    void insert(Task task);

    @Delete
    void delete(Task task);
}

