package com.example.tasks.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.tasks.MainActivity

class BootRec : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (Intent.ACTION_BOOT_COMPLETED == intent.action) {
            val int = Intent(context, MainActivity::class.java)
            int.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(int)
        }
    }
}