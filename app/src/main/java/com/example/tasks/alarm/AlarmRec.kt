package com.example.tasks.alarm

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.example.tasks.R
import com.example.tasks.utils.Cons

class AlarmRec : BroadcastReceiver() {
    private var reqCode = 0
    private lateinit var nMng: NotificationManager
    override fun onReceive(ctx: Context, intent: Intent) {
        if (intent.action == Cons.ACTION_SET_EXACT) {
            val id = intent.getIntExtra(Cons.ALARM_ID, 0)
            val time = intent.getLongExtra(Cons.ALARM_TIME, 0L)
            val title = intent.getStringExtra(Cons.TITLE)
            val body = intent.getStringExtra(Cons.BODY)
            nMng =
                ctx.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            time.let { reqCode = id + 12345 }
            createChannel()
            val pendingIntent =
                PendingIntent.getActivity(ctx, reqCode, intent, PendingIntent.FLAG_IMMUTABLE)
            val builder = NotificationCompat.Builder(ctx, Cons.CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(title)
                .setContentText(body)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
            nMng.notify(reqCode, builder.build())
        }
    }
    private fun createChannel() {
        val name = "Task App"
        val descriptionText = "Notification"
        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel(Cons.CHANNEL_ID, name, importance).apply {
            description = descriptionText
        }
        nMng.createNotificationChannel(channel)
    }
}