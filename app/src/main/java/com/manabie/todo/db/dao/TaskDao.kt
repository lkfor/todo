package com.manabie.todo.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.manabie.todo.model.TaskModel

@Dao
interface TaskDao : BaseDao<TaskModel> {
    @Query("SELECT * FROM tasks")
    fun loadAllTask(): LiveData<List<TaskModel>>

    @Query("SELECT * FROM tasks where status = :type")
    fun loadAllTaskByType(type: Int): LiveData<List<TaskModel>>

    @Query("select * from tasks where id = :taskId")
    fun loadTask(taskId: Long): LiveData<TaskModel?>?
}