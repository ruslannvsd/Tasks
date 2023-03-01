package com.example.tasks.popups

import android.content.Context
import android.view.Gravity
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.Toast
import androidx.core.widget.PopupWindowCompat
import com.example.tasks.R
import com.example.tasks.data.task.Task
import com.example.tasks.data.task.TaskVM
import com.example.tasks.databinding.AddTaskBinding

object AddTask {
    fun addTask(ctx: Context, thread: Long, area: Long, insert: (Task) -> Unit) {
        val inflater =
            ctx.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val popupView =
            inflater.inflate(R.layout.add_task, LinearLayout(ctx), false)
        val bnd = AddTaskBinding.bind(popupView)
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
        val enter = bnd.taskName
        enter.setOnKeyListener { _, keyCode, _ ->
            if (keyCode == KeyEvent.KEYCODE_ENTER) {
                if (enter.text.isNotEmpty()) {
                    val t = Task(
                        id = 0,
                        task = enter.text.toString(),
                        note = null,
                        alarm = null,
                        alarms = null,
                        status = true,
                        thread = thread,
                        area = area
                    )
                    insert(t)
                    window.dismiss()
                } else Toast.makeText(ctx, "Enter your task", Toast.LENGTH_LONG).show()
            }
            true
        }
    }
}