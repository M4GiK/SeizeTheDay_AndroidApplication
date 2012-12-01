package com.example.endproject;

import java.util.Calendar;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TimePicker;
import android.widget.Toast;

public class SetAlarm extends Activity
{
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.set_alarm);
	}
	
//	@Override
//	protected Dialog onCreateDialog(int id) {
//	   Toast.makeText(SetAlarm.this,
//	     "- onCreateDialog(ID_TIMEPICKER) -",
//	     Toast.LENGTH_LONG).show();
//	   final Calendar c = Calendar.getInstance();
//	   int hour = c.get(Calendar.HOUR_OF_DAY);
//	   int minute = c.get(Calendar.MINUTE);
//	   return new TimePickerDialog(this,
//	     theTimeSetListener,
//	     hour, minute, false);
//	 }  // end onCreate
	
	public void press_set_alarm(View v)
	{
		TimePicker tp = (TimePicker)findViewById(R.id.timePicker1);
//		
		int hour = tp.getCurrentHour();
		int minute = tp.getCurrentMinute();
//		
//		      String time = "User picked Hour: " + String.valueOf(hour) + "\n"
//		                        + "Minute: " + String.valueOf(minute);
//		      Toast.makeText(SetAlarm.this, time, Toast.LENGTH_LONG).show();
//		      int theHour = hour;
//		      int theMinute = minute;
//		 
////		      Intent alarmIntent = new Intent(SetAlarm.this, MainActivity.class);
//		      Intent i = new Intent(SetAlarm.this,MainActivity.class);
//		      //alarmIntent.putExtra("nel.example.alarms1","My message");
//		      PendingIntent pendingAlarmIntent = PendingIntent.getService(SetAlarm.this, 0,
//		                                   i, 0);
//		 
//		      AlarmManager alarmManager = (AlarmManager)getSystemService(ALARM_SERVICE);
//		 
		      Calendar AlarmCal = Calendar.getInstance();
		      AlarmCal.setTimeInMillis(System.currentTimeMillis());
		      AlarmCal.set(Calendar.HOUR_OF_DAY, hour);  // set user selection
		      AlarmCal.set(Calendar.MINUTE, minute);        // set user selection
		      AlarmCal.set(Calendar.SECOND, 0);
//		 
//		      alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,
//		           AlarmCal.getTimeInMillis(), AlarmManager.INTERVAL_FIFTEEN_MINUTES,
//		           pendingAlarmIntent);
//		      alarmManager.set(AlarmManager.RTC_WAKEUP, AlarmCal.getTimeInMillis(), pendingAlarmIntent);
////		      startActivity(i);
		
		
		
        AlarmManager am=(AlarmManager)getApplicationContext().getSystemService(Context.ALARM_SERVICE);
        Intent i = new Intent(getApplicationContext(), Alarm.class);
        PendingIntent pi = PendingIntent.getBroadcast(getApplicationContext(), 0, i, 0);
        am.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), AlarmManager.INTERVAL_FIFTEEN_MINUTES/15/2, pi); // Millisec * Second * Minute
        am.set(AlarmManager.RTC_WAKEUP, AlarmCal.getTimeInMillis(), pi);
		
	}
	
	public void bCancelPressed(View v)
    {
		finish();
    }
}
