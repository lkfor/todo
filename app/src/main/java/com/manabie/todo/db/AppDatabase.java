package com.manabie.todo.db;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.VisibleForTesting;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.manabie.todo.AppExecutors;
import com.manabie.todo.db.dao.TaskDao;
import com.manabie.todo.model.TaskModel;

import java.util.List;

@Database(entities = {TaskModel.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    @VisibleForTesting
    public static final String DATABASE_NAME = "todo.db";
    private static AppDatabase sInstance;
    private final MutableLiveData<Boolean> mIsDatabaseCreated = new MutableLiveData<>();

    public static AppDatabase getInstance(final Context context, final AppExecutors executors) {
        if (sInstance == null) {
            synchronized (AppDatabase.class) {
                if (sInstance == null) {
                    sInstance = buildDatabase(context.getApplicationContext(), executors);
                    sInstance.updateDatabaseCreated(context.getApplicationContext());
                }
            }
        }
        return sInstance;
    }

    private static AppDatabase buildDatabase(final Context appContext,
                                             final AppExecutors executors) {
        return Room.databaseBuilder(appContext, AppDatabase.class, DATABASE_NAME)
                .addCallback(new RoomDatabase.Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
                        executors.diskIO().execute(() -> {
                            Log.d("Andy", "insert database");
                            AppDatabase database = AppDatabase.getInstance(appContext, executors);
                            List<TaskModel> tasks = DataGenerator.generateTask();

                            insertData(database, tasks);
                            // notify that the database was created and it's ready to be used
                            database.setDatabaseCreated();
                        });
                    }
                })
                .allowMainThreadQueries()
                .build();
    }

    private static void insertData(final AppDatabase database, final List<TaskModel> tasks) {
        database.runInTransaction(() -> {
            database.taskDao().insertAll(tasks);
        });
    }

    public abstract TaskDao taskDao();

    /**
     * Check whether the database already exists and expose it via {@link #getDatabaseCreated()}
     */
    private void updateDatabaseCreated(final Context context) {
        if (context.getDatabasePath(DATABASE_NAME).exists()) {
            setDatabaseCreated();
        }
    }

    private void setDatabaseCreated() {
        mIsDatabaseCreated.postValue(true);
    }

    public LiveData<Boolean> getDatabaseCreated() {
        return mIsDatabaseCreated;
    }
}
