package com.example.endproject;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class Alarm extends BroadcastReceiver
{

	@Override
	public void onReceive(Context context, Intent intent) 
	{       
        Intent i = new Intent(context, CallAlarm.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        i.putExtra("path", "todo path to file");
        context.startActivity(i);
        Log.d("Alarm - BroadcastReceiver", "Send action"); 
	}
}
