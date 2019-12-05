package com.manabie.todo.ui.task

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.manabie.todo.DataRepository
import com.manabie.todo.MainApplication
import com.manabie.todo.model.BaseModel
import com.manabie.todo.model.TaskModel

class TaskViewModel(application: Application) : AndroidViewModel(application) {

    private val mRepository: DataRepository = (application as MainApplication).getDataRepository()

    fun getTaskByViewTypeObservable(viewType: Int): LiveData<List<TaskModel>> {
        if (viewType == TaskViewType.TYPE_ALL) {
            return mRepository.loadAllTask()
        }
        return mRepository.loadTaskByViewType(if (viewType == TaskViewType.TYPE_COMPLETED) BaseModel.STATUS_COMPLETED else BaseModel.STATUS_IN_COMPLETE)
    }
}