package com.manabie.todo.model

open class BaseModel {
    companion object {
        const val COLUMN_ID = "id"
        const val COLUMN_NAME = "name"
        const val COLUMN_STATUS = "status"

        const val STATUS_IN_COMPLETE = 0
        const val STATUS_COMPLETED = 1
    }

}