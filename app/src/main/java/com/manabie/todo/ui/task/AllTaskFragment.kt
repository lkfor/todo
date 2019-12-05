package com.manabie.todo.ui.task

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class AllTaskFragment : BaseTaskFragment(){
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        taskType = TaskViewType.TYPE_ALL
        return super.onCreateView(inflater, container, savedInstanceState)
    }
}