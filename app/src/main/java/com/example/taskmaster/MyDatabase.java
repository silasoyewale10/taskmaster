package com.example.taskmaster;

import androidx.room.RoomDatabase;

@androidx.room.Database(entities = {Tasks.class}, version = 1)
public abstract class MyDatabase extends RoomDatabase {
    public abstract TasksDao taskToDatabase();

}
