package net.chmielowski.androidstarter.room;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Task {
    public boolean done;
    @PrimaryKey(autoGenerate = true)
    private int uid;
    private String name;

    public Task(String name) {
        this.name = name;
        this.done = false;
    }

    boolean isDone() {
        return done;
    }

    void setDone(boolean done) {
        this.done = done;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

