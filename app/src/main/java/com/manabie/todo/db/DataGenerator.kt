/*
 * Copyright 2017, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.manabie.todo.db

import com.manabie.todo.model.BaseModel
import com.manabie.todo.model.TaskModel
import java.util.*

/**
 * Generates data to pre-populate the database
 */
object DataGenerator {

    @JvmStatic
    fun generateTask(): List<TaskModel> {
        val tasks: MutableList<TaskModel> =
            ArrayList()
        val rnd = Random()
        for (i in 1..40) {
                val taskModel = TaskModel()
                taskModel.name = "Task " + i
                taskModel.status = BaseModel.STATUS_IN_COMPLETE
                tasks.add(taskModel)
        }
        return tasks
    }
}