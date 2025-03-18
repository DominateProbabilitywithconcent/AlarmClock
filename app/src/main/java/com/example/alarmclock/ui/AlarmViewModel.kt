package com.example.alarmclock.ui

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import java.util.Calendar

class AlarmViewModel(private val context: Context) : ViewModel() {
    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    var alarmTime = mutableStateOf<Long?>(null)
    var alarmMessage = mutableStateOf("")

    fun setAlarm(hour: Int, minute: Int, message: String) {
        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
            if (timeInMillis < System.currentTimeMillis()) {
                add(Calendar.DAY_OF_MONTH, 1) // Schedule for the next day if time has passed
            }
        }

        alarmTime.value = calendar.timeInMillis
        alarmMessage.value = message

        val intent = Intent(context, AlarmReceiver::class.java).apply {
            putExtra("ALARM_MESSAGE", message) // Pass the message to receiver
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
    }
}
