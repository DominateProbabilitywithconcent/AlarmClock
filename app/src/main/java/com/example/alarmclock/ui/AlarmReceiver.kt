package com.example.alarmclock.ui

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent?) {
        // Trigger Notification or Sound
        Toast.makeText(context, "Alarm Triggered!", Toast.LENGTH_SHORT).show()
    }
}
