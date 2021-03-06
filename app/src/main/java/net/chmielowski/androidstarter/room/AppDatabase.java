package net.chmielowski.androidstarter.room;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

@Database(entities = {Task.class}, version = 4)
public abstract class AppDatabase extends RoomDatabase {
    public abstract TaskDao dao();
}

