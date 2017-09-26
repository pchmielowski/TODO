package net.chmielowski.androidstarter.room;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity
public class Task {
    public boolean removed;
    @PrimaryKey(autoGenerate = true)
    private int uid;
    private String name;

    public Task(String name) {
        this.name = name;
        this.removed = false;
    }

    boolean isRemoved() {
        return removed;
    }

    void setRemoved(boolean removed) {
        this.removed = removed;
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

