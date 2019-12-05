package com.manabie.todo.db.dao

import androidx.room.*

@Dao
interface BaseDao<T> {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insert(entity: T): Long

    @Update
    abstract fun update(entity: T): Int

    @Delete
    abstract fun delete(entity: T)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertAll(entities: List<T>)

}