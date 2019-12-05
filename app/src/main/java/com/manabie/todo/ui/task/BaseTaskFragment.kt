package com.manabie.todo.ui.task

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.manabie.todo.BaseFragment
import com.manabie.todo.R
import com.manabie.todo.databinding.FragmentTaskBinding
import com.manabie.todo.model.TaskModel
import com.manabie.todo.ui.TaskAdapter
import com.manabie.todo.utils.SpacingDecoration
import java.util.*

open class BaseTaskFragment : BaseFragment() {

    private lateinit var taskViewModel: TaskViewModel
    private var listener: TaskAdapter.OnListFragmentInteractionListener? = null
    private val mTaskList: MutableList<TaskModel> = ArrayList()
    private var mTaskAdapter: TaskAdapter? = null
    var taskType: Int = TaskViewType.TYPE_ALL
    private lateinit var mBinding: FragmentTaskBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        taskViewModel = ViewModelProviders.of(this).get(TaskViewModel::class.java)
        mBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_task, container, false)

        taskViewModel.getTaskByViewTypeObservable(taskType)
            .observe(this, androidx.lifecycle.Observer { rooms ->
                mTaskList.clear()
                if (rooms != null) {
                    mTaskList.addAll(rooms)
                }
                mTaskAdapter?.notifyDataSetChanged()
            })
        mBinding.list.layoutManager = LinearLayoutManager(context)
        mTaskAdapter = TaskAdapter(mTaskList, listener)
        mBinding.list.addItemDecoration(
            SpacingDecoration(
                resources.getDimensionPixelOffset(R.dimen.padding),
                SpacingDecoration.VERTICAL,
                false,
                false
            )
        )
        mBinding.list.adapter = mTaskAdapter
        return mBinding.root
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is TaskAdapter.OnListFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnListFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

}