package com.example.taskmaster;


import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TasksDao {
    @Query("select * from tasks order by id desc")
    List<Tasks> getAll();

    @Query("select * from tasks where id = :id")
    Tasks getOne(long id);
    @Insert
    void save(Tasks task);
}
