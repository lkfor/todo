package com.manabie.todo.db;

import com.manabie.todo.model.TaskModel;

import java.util.Arrays;
import java.util.List;

public class TestData {
    public static TaskModel TASK_ENTITY = new TaskModel(
            1L, "name", 1);
    public static TaskModel TASK_ENTITY2 = new TaskModel(
            2L, "name2", 0);

    public static List<TaskModel> TASK = Arrays.asList(TestData.TASK_ENTITY, TestData.TASK_ENTITY2);
}
