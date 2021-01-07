package com.ternovyi.to_do_list.view.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.ternovyi.to_do_list.databinding.FragmentToDoListBinding
import com.ternovyi.to_do_list.extension.onError
import com.ternovyi.to_do_list.view.adapter.TaskAdapter
import com.ternovyi.to_do_list.viewModel.ToDoListViewModel
import kotlinx.android.synthetic.main.fragment_to_do_list.*
import org.koin.android.ext.android.inject
import org.koin.android.viewmodel.ext.android.viewModel

class ToDoListFragment : Fragment() {

    private lateinit var binding: FragmentToDoListBinding

    private val viewModel: ToDoListViewModel by viewModel()
    private val adapter: TaskAdapter by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.downloadAndSaveTasksLiveData.observe(this, Observer {
            it.onError { _, _ ->
                Log.e("ERROR: ", "Download list of tasks")
            }
        })

        viewModel.tasksLiveData.observe(this, Observer {
            adapter.clearAndAddTaskItems(it)
        })

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = FragmentToDoListBinding.inflate(inflater, container, false).also {
        binding = it
    }.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupView()
        add_task_button.setOnClickListener {
            viewModel.onClickAddNewTask(add_task_edit_text.text.toString())
            add_task_edit_text.text?.clear()
        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.downloadTasks()
        viewModel.refreshTasks()
    }

    private fun setupView() {
        tasks_recycler_view.adapter = adapter
        adapter.onTaskCheckBoxClickListener = { id, isChecked ->
            viewModel.checkTask(id, isChecked)
        }
    }

}