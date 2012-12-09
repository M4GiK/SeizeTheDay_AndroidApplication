package com.example.endproject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class Alarm extends BroadcastReceiver
{

	/**
	 * Catch message form Android AlarmManager object.
	 */
	@Override
	public void onReceive(Context context, Intent intent) 
	{       		
        Intent i = new Intent(context, CallAlarm.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
	}
}
