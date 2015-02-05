package com.rogerthat.app;
import android.media.*;
import android.content.*;
import java.io.*;
import android.util.*;

public class AudioPlayer extends MediaPlayer implements MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener,
MediaPlayer.OnCompletionListener
{
	Context context;
	private float volumeScalar = 0.2f;
	
	public AudioPlayer(Context context){
		super();
		this.context = context;
		
		setAudioStreamType(AudioManager.STREAM_MUSIC);
		setOnPreparedListener(this);
		setOnErrorListener(this);
		setOnCompletionListener(this);
		}
		
	@Override
	public boolean onError(MediaPlayer p1, int p2, int p3)
	{
		reset();
		return false;
	}

	@Override
	public void onPrepared(MediaPlayer p1)
	{
		setVolume(volumeScalar,volumeScalar);
		start();
	}
	
	public void playFile(String filename) throws IOException{
		setDataSource(filename);
		prepareAsync();
	}
	
	public void playResource(int id){
		create(context, id);
		start();
	}
	
	public void playFromStream(FileDescriptor fd){
		try
		{
			setDataSource(fd);
			prepareAsync();
		}
		catch (Exception e)
		{
			Log.e("AudioPlayer",e.getMessage());	
		}
	}
	
	@Override
	public void onCompletion(MediaPlayer p1)
	{
		stop();
		reset();
	}
	
	public void setVolumeScalar(float scalar){
		volumeScalar = scalar;	
	}
}
