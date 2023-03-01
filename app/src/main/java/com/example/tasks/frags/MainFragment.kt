package com.example.tasks.frags

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.tasks.R
import com.example.tasks.adaps.AreaAd
import com.example.tasks.adaps.TaskAd
import com.example.tasks.adaps.ThdAd
import com.example.tasks.alarm.AlarmServ
import com.example.tasks.data.area.Area
import com.example.tasks.data.area.AreaVM
import com.example.tasks.data.task.Task
import com.example.tasks.data.task.TaskVM
import com.example.tasks.data.thread.Thd
import com.example.tasks.data.thread.ThdVM
import com.example.tasks.databinding.FragmentMainBinding
import com.example.tasks.popups.AddPopups
import com.example.tasks.popups.AddTask
import com.example.tasks.utils.Cons
import com.example.tasks.utils.SwipeHelper
import kotlinx.coroutines.runBlocking

class MainFragment : Fragment() {
    private lateinit var bnd: FragmentMainBinding
    private lateinit var adAr: AreaAd
    private lateinit var adThd: ThdAd
    private lateinit var adTask: TaskAd
    private lateinit var itemTouchHelp: ItemTouchHelper
    private lateinit var vmAr: AreaVM
    private lateinit var vmThd: ThdVM
    private lateinit var vmTask: TaskVM
    private lateinit var rvAr: RecyclerView
    private lateinit var rvThd: RecyclerView
    private lateinit var rvTask: RecyclerView
    private lateinit var oneDayTV: TextView
    private lateinit var threeDaysTV: TextView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        bnd = FragmentMainBinding.bind(inflater.inflate(R.layout.fragment_main, container, false))

        setting()

        // initialization of Task Adapter
        // lambdas to pass to Task Adapter

        val updateTask = { task: Task, pos: Int ->
            task.alarm?.let { AlarmServ(requireContext()).setAlarm(task) }
            vmTask.update(task)
            adTask.notifyItemChanged(pos)
        }

        val deleteTask = { task: Task, pos: Int ->
            AlarmServ(requireContext()).cancel(task)
            vmTask.delete(task)
            adTask.notifyItemRemoved(pos)

        }

        adTask = TaskAd(requireContext(), updateTask, deleteTask)
        itemTouchHelp = ItemTouchHelper(SwipeHelper(adTask))
        itemTouchHelp.attachToRecyclerView(rvTask)

        upcoming(true) // to show upcoming tasks when opening the app
        oneDayTV.setOnClickListener {
            upcoming(true)
            areasIntoRv(viewLifecycleOwner)
            gray(oneDayTV)
            green(threeDaysTV)
        }
        threeDaysTV.setOnClickListener {
            upcoming(false)
            areasIntoRv(viewLifecycleOwner)
            gray(threeDaysTV)
            green(oneDayTV)
        }

        // initialization of Thread Adapter
        // lambdas to pass to Thread Adapter

        val threadTasks = { task: Task ->
            vmTask.insert(task)
            vmTask.tasks(task.thread).observe(viewLifecycleOwner) {
                tasksIntoRv(it)
            }
        }
        val addTask = { thread: Long, area: Long ->
            AddTask.addTask(requireContext(), thread, area, threadTasks)
        }

        val tasks = { thread: Long ->
            green(oneDayTV)
            green(threeDaysTV)
            vmTask.tasks(thread).observe(viewLifecycleOwner) {
                tasksIntoRv(it)
            }
        }

        val addThread = { arLong: Long ->
            AddPopups.addThdPopup(requireContext(), arLong, vmThd, null)
        }

        val renameThread = { thread: Thd ->
            AddPopups.addThdPopup(requireContext(), thread.area, vmThd, thread)
        }

        val deleteThread = { thread: Thd, pos: Int ->
            vmThd.delete(thread)
            adThd.notifyItemRemoved(pos)
        }

        adThd = ThdAd(requireContext(), addTask, tasks, renameThread, deleteThread)

        // initialization of Area Adapter
        // lambdas to pass to Area Adapter

        val setAreaThreadsAndTasks = { area: Long ->
            green(oneDayTV)
            green(threeDaysTV)
            vmThd.threads(area).observe(viewLifecycleOwner) {
                threadsIntoRv(it)
            }
            vmTask.areaTasks(area).observe(viewLifecycleOwner) {
                tasksIntoRv(it)
            }
        }

        val tasksToDelete = { area: Long ->
            runBlocking {
                val list = vmTask.tasksToDelete(area)
                AlarmServ(requireContext()).cancelAlarms(list)
            }
        }

        val deleteArea = { area: Area ->
            vmAr.delete(area)
            vmThd.deleteAreaThreads(area.areaL)
            tasksToDelete(area.areaL)
            vmTask.deleteAreaTasks(area.areaL)
        }

        val renameArea = { area: Area ->
            AddPopups.addAreaPopup(requireContext(), vmAr, area, setAreaThreadsAndTasks)
        }

        //val addArea = {
        //    AddPopups.addAreaPopup(requireContext(), vmAr, null, setThd)
        //}

        adAr = AreaAd(requireContext(), setAreaThreadsAndTasks, addThread, deleteArea, renameArea)

        // button to add a new area

        bnd.addArea.setOnClickListener {
            AddPopups.addAreaPopup(requireContext(), vmAr, null, setAreaThreadsAndTasks)
        }

        areasIntoRv(viewLifecycleOwner)

        return bnd.root
    }

    private fun setting() {
        vmAr = ViewModelProvider(this)[AreaVM::class.java]
        vmThd = ViewModelProvider(this)[ThdVM::class.java]
        vmTask = ViewModelProvider(this)[TaskVM::class.java]

        rvAr = bnd.areasRv
        rvThd = bnd.threadsRv
        rvTask = bnd.tasksRv

        oneDayTV = bnd.oneDay
        threeDaysTV = bnd.threeDays
    }

    private fun areasIntoRv(owner: LifecycleOwner) {
        vmAr.areas.observe(owner) {
            adAr.setAreas(it, -1)
            rvAr.adapter = adAr
            rvAr.layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        }
        threadsIntoRv(emptyList())
    }

    private fun threadsIntoRv(list: List<Thd>) {
        adThd.setThd(list, -1)
        rvThd.adapter = adThd
        rvThd.layoutManager = StaggeredGridLayoutManager(4, StaggeredGridLayoutManager.VERTICAL)
    }

    private fun tasksIntoRv(tasks: List<Task>) {
        adTask.setTasks(tasks)
        rvTask.adapter = adTask
        rvTask.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun upcoming(oneDay: Boolean) {
        if (oneDay) {
            gray(oneDayTV)
            green(threeDaysTV)
            val endTime = System.currentTimeMillis() + Cons.ONE_DAY
            vmTask.upcoming(endTime).observe(viewLifecycleOwner) {
                tasksIntoRv(it)
            }
        } else {
            green(oneDayTV)
            gray(threeDaysTV)
            val endTime = System.currentTimeMillis() + Cons.THREE_DAYS
            vmTask.upcoming(endTime).observe(viewLifecycleOwner) {
                tasksIntoRv(it)
            }
        }
    }

    private fun green(tv: TextView) {
        tv.setBackgroundColor(requireContext().getColor(R.color.dark_green))
    }

    private fun gray(tv: TextView) {
        tv.setBackgroundColor(requireContext().getColor(R.color.gray))
    }
}