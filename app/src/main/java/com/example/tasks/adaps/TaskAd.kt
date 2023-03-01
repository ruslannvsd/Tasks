package com.example.tasks.adaps

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.tasks.R
import com.example.tasks.data.task.Task
import com.example.tasks.databinding.RvTaskBinding
import com.example.tasks.popups.TaskPopup
import com.example.tasks.time.Picker
import com.example.tasks.time.TimeFuns
import com.example.tasks.utils.OnSwipeAdapter

class TaskAd(
    private val ctx: Context,
    private val updateTask: (Task, Int) -> Unit,
    private val deleteTask: (Task, Int) -> Unit
) : RecyclerView.Adapter<TaskAd.TaskVH>(), OnSwipeAdapter {
    private var tasks = emptyList<Task>()

    class TaskVH(val bnd: RvTaskBinding) : RecyclerView.ViewHolder(bnd.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskVH {
        val bnd = RvTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TaskVH(bnd)
    }

    override fun onItemSwipe(position: Int) {
        val t = tasks[position]
            val sts = !t.status
            val nTask = Task(t.id, t.task, t.note, t.alarm, t.alarms, sts, t.thread, t.area)
            updateTask(nTask, position)
    }

    override fun onBindViewHolder(h: TaskVH, p: Int) {
        val card = h.bnd.card
        val t = tasks[p]
        val taskName = h.bnd.taskName
        taskName.text = t.task
        val time = h.bnd.time
        val note = h.bnd.note
        val alarm = t.alarm
        val notes = t.note

        if (t.status) taskColor(alarm, card)
        else card.background.setTint(ctx.getColor(R.color.eight))
        if (t.status) time.setTextColor(ctx.getColor(R.color.orange)) else
            time.setTextColor(ctx.getColor(R.color.light_gray))

        alarm?.let {
            time.visibility = View.VISIBLE
            time.text = TimeFuns.millisToString(it)
        }
        notes?.let {
            note.visibility = View.VISIBLE
            note.text = it
        }

        card.setOnClickListener {
            Toast.makeText(ctx, "Clicked", Toast.LENGTH_LONG).show()
            TaskPopup.taskCard(ctx, t, p, updateTask, deleteTask)
        }
    }

    override fun getItemCount() = tasks.size

    fun setTasks(tasks: List<Task>) {
        this.tasks = tasks
        notifyChange()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun notifyChange() = this.notifyDataSetChanged()
    private fun taskColor(time: Long?, card: CardView) {
        if (time != null) {
            val diff = diff(time)
            when {
                diff < 0 -> card.background.setTint(ctx.getColor(R.color.one)) // overdue task
                diff in 0..14400000 -> card.background.setTint(ctx.getColor(R.color.two)) // 0-4 hours
                diff in 14400001..28800000 -> card.background.setTint(ctx.getColor(R.color.three)) // 4-8 hours
                diff in 28800001..86400000 -> card.background.setTint(ctx.getColor(R.color.four)) // 8-24 hours
                diff in 86400001..172800000 -> card.background.setTint(ctx.getColor(R.color.five)) // 1-2 days
                diff in 172800001..345600000 -> card.background.setTint(ctx.getColor(R.color.six)) // 2-4 days
                diff > 345600001 -> card.background.setTint(ctx.getColor(R.color.seven)) // more than 4 days
            }
        } else card.background.setTint(ctx.getColor(R.color.black))
    }
    private fun diff(time: Long) : Long {
        val now = System.currentTimeMillis()
        return time - now
    }


}