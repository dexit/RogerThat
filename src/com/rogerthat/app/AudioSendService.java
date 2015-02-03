package com.rogerthat.app;
import android.app.*;
import android.content.*;
import android.os.*;

public class AudioSendService extends Service
{

	@Override
	public int onStartCommand(Intent intent, int flags, int startId)
	{
		return super.onStartCommand(intent, flags, startId);
	}
	
	@Override
	public IBinder onBind(Intent p1)
	{
		return null;
	}

	@Override
	public void onDestroy()
	{
		super.onDestroy();
	}
	
}
