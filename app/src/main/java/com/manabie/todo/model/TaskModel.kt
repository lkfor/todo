package com.manabie.todo.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey

@Entity(
    tableName = "tasks"
)
class TaskModel() : BaseModel() {
    @PrimaryKey
    @ColumnInfo(name = COLUMN_ID)
    var id: Long? = null

    @ColumnInfo(name = COLUMN_NAME)
    var name: String? = null

    //0: incomplete, 1: completed
    @ColumnInfo(name = COLUMN_STATUS)
    var status: Int? = null

    @Ignore
    constructor(
        id: Long,
        name: String?,
        status: Int
    ) : this() {
        this.id = id
        this.name = name
        this.status = status
    }
}