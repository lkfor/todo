package com.manabie.todo

import androidx.lifecycle.LiveData
import com.manabie.todo.db.AppDatabase
import com.manabie.todo.model.TaskModel
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import java.util.concurrent.Callable

class DataRepository private constructor(private val mDatabase: AppDatabase) {
    var instance: AppDatabase? = null

    private fun <T> makeObservable(func: Callable<T>): Observable<T> {
        return Observable.create { subscriber: ObservableEmitter<T> ->
            try {
                if (func != null) subscriber.onNext(func.call())
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        }
    }

    companion object {
        private var sInstance: DataRepository? = null

        fun getInstance(database: AppDatabase): DataRepository {
            if (sInstance == null) {
                synchronized(DataRepository::class.java) {
                    if (sInstance == null) {
                        sInstance = DataRepository(database)
                    }
                }
            }
            return sInstance!!
        }
    }

    fun loadAllTask(): LiveData<List<TaskModel>> {
        return mDatabase.taskDao().loadAllTask()
    }

    fun loadTaskByViewType(type: Int): LiveData<List<TaskModel>> {
        return mDatabase.taskDao().loadAllTaskByType(type)
    }

    fun updateTask(task: TaskModel): Int {
        return mDatabase.taskDao().update(task)
    }
}
