package com.example.tasks.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.example.tasks.data.task.Task
import com.example.tasks.time.TimeFuns
import com.example.tasks.utils.Cons

class AlarmServ(private val ctx: Context) {
    private val alarmManager = ctx.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    private val intent = Intent(ctx, AlarmRec::class.java)
    fun setAlarm(task: Task) {
        if (task.status) {
            intent.apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                action = Cons.ACTION_SET_EXACT
                putExtra(Cons.ALARM_ID, task.id)
                putExtra(Cons.ALARM_TIME, task.alarm)
                putExtra(Cons.TITLE, task.task)
                putExtra(Cons.BODY, task.note)
            }
            val pendingIntent: PendingIntent = PendingIntent
                .getBroadcast(
                    ctx,
                    task.id + 12345,
                    intent,
                    PendingIntent.FLAG_IMMUTABLE
                )
            task.alarm?.let {
                alarmManager.setAndAllowWhileIdle(
                    AlarmManager.RTC_WAKEUP,
                    it,
                    pendingIntent
                )
            }
            val alarmTimeString = task.alarm?.let { TimeFuns.millisToString(it) } ?: "ERROR"
            Toast.makeText(ctx, "Alarm set at $alarmTimeString", Toast.LENGTH_LONG).show()
        }
    }

    fun cancelAlarms(list: List<Task>) {
        for (i in list) {
            cancel(i)
        }
    }
    fun cancel(task: Task) {
        val pendingIntent = PendingIntent.getBroadcast(
            ctx, task.id + 12345, intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
        alarmManager.cancel(pendingIntent)
    }
}