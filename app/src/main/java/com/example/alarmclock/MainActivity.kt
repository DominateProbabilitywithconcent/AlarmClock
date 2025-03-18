package com.example.alarmclock

import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.widget.TimePicker
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.alarmclock.ui.AlarmViewModel
import com.example.alarmclock.ui.theme.AlarmClockTheme
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AlarmClockTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    AlarmScreen(
                        title = "Alarm Clock",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}
object ButtonColors{
    val ContainerColor = Color(0xFF6750A4)
    val ContentColor = Color(0xFFFFFFFF)
}

fun getFormattedTime(): String {
    val calendar = Calendar.getInstance()
    val formatter = SimpleDateFormat("HH:mm", Locale.getDefault()) // 24-hour format
    return formatter.format(calendar.time) // Returns "14:30" (example)
}

@Composable
fun AlarmScreen(title: String, modifier: Modifier = Modifier)
{
    val context = LocalContext.current
    var selectedTime by remember { mutableStateOf(Calendar.getInstance()) } // Store Calendar instance
    Column(modifier = Modifier.fillMaxSize().padding(16.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Text("Alarm Clock", style = MaterialTheme.typography.headlineLarge)
        Text(
            text = "${selectedTime.get(Calendar.HOUR_OF_DAY)}:${selectedTime.get(Calendar.MINUTE)}",
            style = MaterialTheme.typography.headlineLarge
        )
        Button(onClick = {
            showTimePicker(context, selectedTime) { updatedCalendar ->
                selectedTime = updatedCalendar // Update selected time
            } },
            colors = ButtonDefaults.buttonColors(
                containerColor = ButtonColors.ContainerColor,
                contentColor = ButtonColors.ContentColor
            ),
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(0.5f) // Adjust the button's width (80% of screen width)
                .aspectRatio(3f)    // Maintain a fixed aspect ratio (3:1)) {
        ){
            Text("Set Alarm")

        }
        Button(onClick = { /* Open Time Picker */ },
            colors = ButtonDefaults.buttonColors(
                containerColor = ButtonColors.ContainerColor,
                contentColor = ButtonColors.ContentColor
            ),
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(0.5f) // Adjust the button's width (80% of screen width)
                .aspectRatio(3f)    // Maintain a fixed aspect ratio (3:1))) {
        ){
            Text("Cancel Alarm")
        }


    }

}
@Composable
fun TimePickerDialogAPI24_Calendar() {
    val context = LocalContext.current
    var selectedTime by remember { mutableStateOf(Calendar.getInstance()) } // Store Calendar instance

    Column(modifier = Modifier.padding(16.dp)) {
        Text(
            text = "Selected Time: ${selectedTime.get(Calendar.HOUR_OF_DAY)}:${selectedTime.get(Calendar.MINUTE)}"
        )

        Button(onClick = {
            showTimePicker(context, selectedTime) { updatedCalendar ->
                selectedTime = updatedCalendar // Update selected time
            }
        }) {
            Text("Pick a Time")
        }
    }
}

fun showTimePicker(context: Context, currentTime: Calendar, onTimeSelected: (Calendar) -> Unit) {
    val hour = currentTime.get(Calendar.HOUR_OF_DAY)
    val minute = currentTime.get(Calendar.MINUTE)

    TimePickerDialog(
        context,
        { TimePicker, selectedHour: Int, selectedMinute: Int ->
            val newTime = Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, selectedHour)
                set(Calendar.MINUTE, selectedMinute)
            }
            onTimeSelected(newTime) // Return the updated Calendar instance
        },
        hour,
        minute,
        false // true = 24-hour format, false = 12-hour format
    ).show()
}

@Preview(showBackground = true)
@Composable
fun AlarmScreenPreview() {
    AlarmClockTheme {
        AlarmScreen("Alarm Clock")
    }
}
