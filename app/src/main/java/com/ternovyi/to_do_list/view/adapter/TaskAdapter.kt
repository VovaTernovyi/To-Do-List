package com.ternovyi.to_do_list.view.adapter

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ternovyi.to_do_list.R
import com.ternovyi.to_do_list.databinding.ItemTaskBinding
import com.ternovyi.to_do_list.model.entity.Task

class TaskAdapter : RecyclerView.Adapter<TaskAdapter.TaskViewHolder>() {

    var onTaskCheckBoxClickListener: ((Long, Boolean) -> Unit)? = null
    private val taskList = mutableListOf<Task>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder =
        TaskViewHolder(
            ItemTaskBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        taskList[position].let { task ->
            holder.binding.run {
                name = task.title
                if (task.isCompleted) {
                    taskCardView.setCardBackgroundColor(root.context.getColorStateList(R.color.green))
                    taskNameTextView.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                    taskCheckBox.isChecked = true
                } else {
                    taskCardView.setCardBackgroundColor(root.context.getColorStateList(R.color.white))
                    taskCheckBox.isChecked = false
                    taskNameTextView.paintFlags = Paint.ANTI_ALIAS_FLAG
                }
                root.setOnClickListener {
                    if (taskNameTextView.maxLines != 1) {
                        taskNameTextView.maxLines = 1;
                    } else {
                        taskNameTextView.maxLines = 10;
                    }
                }
                taskCheckBox.setOnClickListener {
                    if (taskCheckBox.isChecked) {
                        taskCardView.setCardBackgroundColor(root.context.getColorStateList(R.color.green))
                        taskNameTextView.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                    } else {
                        taskCardView.setCardBackgroundColor(root.context.getColorStateList(R.color.green))
                        taskNameTextView.paintFlags = Paint.ANTI_ALIAS_FLAG
                    }
                    onTaskCheckBoxClickListener?.invoke(task.id, taskCheckBox.isChecked)
                }

            }
        }
    }

    override fun getItemCount(): Int {
        return taskList.size
    }

    fun clearAndAddTaskItems(taskItems: List<Task>) {
        if (taskList == taskItems) {
            return
        }
        taskList.clear()
        taskList.addAll(taskItems)
        notifyDataSetChanged()
    }

    fun addTaskItem(task: Task) {
        taskList.add(task)
        notifyDataSetChanged();
    }

    class TaskViewHolder(
        val binding: ItemTaskBinding
    ) : RecyclerView.ViewHolder(binding.root)
}