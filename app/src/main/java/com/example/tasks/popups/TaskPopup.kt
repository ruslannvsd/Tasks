package com.example.tasks.popups

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.Toast
import androidx.core.widget.PopupWindowCompat
import com.example.tasks.R
import com.example.tasks.data.task.Task
import com.example.tasks.databinding.TaskCardBinding
import com.example.tasks.time.Picker
import com.example.tasks.time.TimeFuns

object TaskPopup {
    fun taskCard(ctx: Context, t: Task, position: Int, updateTask: (Task, Int) -> Unit, deleteTask: (Task, Int) -> Unit) {
        val inflater =
            ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val popupView =
            inflater.inflate(R.layout.task_card, LinearLayout(ctx), false)
        val bnd = TaskCardBinding.bind(popupView)
        val window = PopupWindow(
            popupView,
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.WRAP_CONTENT,
            true
        )
        window.elevation = 2f
        window.showAtLocation(popupView, Gravity.CENTER, Gravity.CENTER, Gravity.CENTER)
        PopupWindowCompat.setWindowLayoutType(
            window,
            WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN
        )
        val taskET = bnd.taskName
        val alarmTV = bnd.alarmText
        val noteET = bnd.taskNote
        val switch = bnd.switchBtn
        val btnSave = bnd.save
        val btnDelete = bnd.delete
        bnd.taskName.setText(t.task)
        t.alarm?.let {
            val time = TimeFuns.millisToString(it)
            alarmTV.text = time
        }
        t.note?.let {
            noteET.setText(it)
        }
        alarmTV.setOnClickListener {
            Picker(ctx, alarmTV, t.alarm).pickTime()
        }
        switch.isChecked = t.status

        btnSave.setOnClickListener {
            if (taskET.text.isNotEmpty()) {
                val newTaskName = taskET.text.toString()
                val nNote = if (noteET.text.isEmpty()) null else
                    noteET.text.toString()
                val nAlarm = if (alarmTV.text.isEmpty()) null else
                    TimeFuns.stringToMillis(alarmTV.text.toString())
                val sts = switch.isChecked
                val task = Task(
                    t.id,
                    newTaskName,
                    nNote,
                    nAlarm,
                    null,
                    sts,
                    t.thread,
                    t.area
                )
                updateTask(task, position)
                window.dismiss()
            } else Toast.makeText(ctx, "Enter the task", Toast.LENGTH_LONG).show()
        }
        btnDelete.setOnClickListener {
            deleteTask(t, position)
            window.dismiss()
        }
    }
}