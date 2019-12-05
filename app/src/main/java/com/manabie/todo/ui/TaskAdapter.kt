package com.manabie.todo.ui


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.manabie.todo.R
import com.manabie.todo.model.BaseModel
import com.manabie.todo.model.TaskModel
import kotlinx.android.synthetic.main.item_task.view.*

class TaskAdapter(
    private val mValues: List<TaskModel>,
    private val mListener: OnListFragmentInteractionListener?
) : RecyclerView.Adapter<TaskAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_task, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mValues[position]
        holder.cbCompleted.isChecked = item.status == BaseModel.STATUS_COMPLETED
        holder.tvTaskName.text = item.name

        holder.cbCompleted.setOnClickListener {
            mListener?.onListFragmentInteraction(item)
        }
    }

    override fun getItemCount(): Int = mValues.size

    inner class ViewHolder(val mView: View) : RecyclerView.ViewHolder(mView) {
        val cbCompleted: CheckBox = mView.cbCompleted
        val tvTaskName: TextView = mView.tvTaskName

    }

    interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onListFragmentInteraction(item: TaskModel?)
    }
}
