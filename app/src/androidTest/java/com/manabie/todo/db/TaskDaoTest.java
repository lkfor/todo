/*
 * Copyright (C) 2017 The Android Open Source Project
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

package com.manabie.todo.db;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;
import androidx.room.Room;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.runner.AndroidJUnit4;

import com.manabie.todo.LiveDataTestUtil;
import com.manabie.todo.db.dao.TaskDao;
import com.manabie.todo.model.TaskModel;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static junit.framework.Assert.assertTrue;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

/**
 * Test the implementation of {@link com.manabie.todo.db.dao.TaskDao}
 */
@RunWith(AndroidJUnit4.class)
public class TaskDaoTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private AppDatabase mDatabase;

    private TaskDao mTaskDao;

    @Before
    public void initDb() throws Exception {
        // using an in-memory database because the information stored here disappears when the
        // process is killed
        mDatabase = Room.inMemoryDatabaseBuilder(ApplicationProvider.getApplicationContext(),
                AppDatabase.class)
                // allowing main thread queries, just for testing
                .allowMainThreadQueries()
                .build();

        mTaskDao = mDatabase.taskDao();
    }

    @After
    public void closeDb() throws Exception {
        mDatabase.close();
    }

    @Test
    public void getProductsWhenNoProductInserted() throws InterruptedException {
        List<TaskModel> tasks = LiveDataTestUtil.getValue(mTaskDao.loadAllTask());

        assertTrue(tasks.isEmpty());
    }

    @Test
    public void getProductsAfterInserted() throws InterruptedException {
        mTaskDao.insertAll(TestData.TASK);

        List<TaskModel> tasks = LiveDataTestUtil.getValue(mTaskDao.loadAllTask());

        assertThat(tasks.size(), is(TestData.TASK.size()));
    }

    @Test
    public void getProductById() throws InterruptedException {
        mTaskDao.insertAll(TestData.TASK);

        TaskModel task = LiveDataTestUtil.getValue(mTaskDao.loadTask
                (TestData.TASK_ENTITY.getId()));

        assertThat(task.getId(), is(TestData.TASK_ENTITY.getId()));
        assertThat(task.getName(), is(TestData.TASK_ENTITY.getName()));
        assertThat(task.getStatus(), is(TestData.TASK_ENTITY.getStatus()));
    }

    @Test
    public void updateTaskById() throws InterruptedException {
        mTaskDao.insertAll(TestData.TASK);

        TaskModel task = LiveDataTestUtil.getValue(mTaskDao.loadTask
                (TestData.TASK_ENTITY.getId()));
        assertThat(task.getId(), is(TestData.TASK_ENTITY.getId()));
        assertThat(task.getName(), is(TestData.TASK_ENTITY.getName()));
        assertThat(task.getStatus(), is(TestData.TASK_ENTITY.getStatus()));

        String name = "new name";
        int newStatus = TestData.TASK_ENTITY.getStatus() + 1;
        task.setName(name);
        task.setStatus(newStatus);
        mTaskDao.update(task);

        assertThat(task.getId(), is(TestData.TASK_ENTITY.getId()));
        assertThat(task.getName(), is(name));
        assertThat(task.getName(), not(TestData.TASK_ENTITY.getName()));
        assertThat(task.getStatus(), is(newStatus));
        assertThat(task.getStatus(), not(TestData.TASK_ENTITY.getStatus()));
    }

}
