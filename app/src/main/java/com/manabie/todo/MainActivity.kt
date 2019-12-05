package com.manabie.todo

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.manabie.todo.model.BaseModel
import com.manabie.todo.model.TaskModel
import com.manabie.todo.ui.TaskAdapter
import com.manabie.todo.ui.task.AllTaskFragment
import com.manabie.todo.ui.task.CompletedBaseTaskFragment
import com.manabie.todo.ui.task.InCompletedBaseTaskFragment
import com.manabie.todo.ui.task.BaseTaskFragment

class MainActivity : AppCompatActivity(), TaskAdapter.OnListFragmentInteractionListener {

    private val TAG = MainActivity::class.simpleName
    private var allTaskFragment: AllTaskFragment = AllTaskFragment()
    private val completedTaskFragment: CompletedBaseTaskFragment = CompletedBaseTaskFragment()
    private val inCompletedTaskFragment: InCompletedBaseTaskFragment = InCompletedBaseTaskFragment()
    private lateinit var active: BaseFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        active = allTaskFragment

        val fm: FragmentManager = supportFragmentManager
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        navView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.navigation_all_task -> {
                    fm.beginTransaction().hide(active).show(allTaskFragment).commit()
                    active = allTaskFragment
                    true
                }
                R.id.navigation_completed_task -> {
                    fm.beginTransaction().hide(active).show(completedTaskFragment).commit()
                    active = completedTaskFragment
                    true
                }
                R.id.navigation_in_complete_task -> {
                    fm.beginTransaction().hide(active).show(inCompletedTaskFragment).commit()
                    active = inCompletedTaskFragment
                    true
                }
                else -> {
                    fm.beginTransaction().hide(active).show(allTaskFragment).commit()
                    false
                }
            }
        }
        navView.selectedItemId = R.id.navigation_all_task

        val navController = findNavController(R.id.nav_host_fragment)
        navView.setupWithNavController(navController)
    }

    override fun onListFragmentInteraction(item: TaskModel?) {
        if (item == null) {
            Log.d(TAG, "item = null")
            return
        }
        if (item.status == BaseModel.STATUS_COMPLETED) {
            item.status = BaseModel.STATUS_IN_COMPLETE
        } else {
            item.status = BaseModel.STATUS_COMPLETED
        }
        MainApplication.instance?.getDataRepository()?.updateTask(item)
    }
}
