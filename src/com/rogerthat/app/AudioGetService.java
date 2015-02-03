package com.rogerthat.app;
import android.app.*;
import android.content.*;
import android.os.*;

public class AudioGetService extends Service
{
	Context context;
	AudioPlayer player;
	
	public AudioGetService(Context context, AudioPlayer player){
		this.context = context;
		this.player = player;
	}
	
	@Override
	public IBinder onBind(Intent p1)
	{
		return null;
	}

}
