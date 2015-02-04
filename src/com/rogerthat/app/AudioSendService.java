package com.rogerthat.app;
import android.app.*;
import android.content.*;
import android.os.*;
import java.io.*;
import android.util.*;

public class AudioSendService extends Service
{
	FileInputStream is = null;
	String filename, filepath;
	String sendUrl = "";
	String TITLE = "Roger That";
	String DESC = "Audio Stream";
	@Override
	public int onStartCommand(Intent intent, int flags, int startId)
	{
		super.onStartCommand(intent, flags, startId);
		
		filename = intent.getExtras().getString("filename");
		filepath = intent.getExtras().getString("filepath");
		
		try
		{
			is = new FileInputStream(filepath);
		}
		catch (FileNotFoundException e)
		{
			Log.e("AudioSendService", e.getMessage());
		}
		
		if(is!=null)
			new Thread(null, new HttpSend(sendUrl, TITLE,DESC, is, filename), "AudioSendService").start();
		
		return START_STICKY;
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
