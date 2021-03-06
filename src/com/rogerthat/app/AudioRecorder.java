package com.rogerthat.app;
import android.content.*;
import android.media.*;
import java.text.*;
import java.util.*;
import android.os.*;
import java.io.*;
import android.widget.*;
import android.util.*;

public class AudioRecorder extends MediaRecorder implements MediaRecorder.OnErrorListener
{
	Context context;
	String TAG = "Audio Recorder";
	private String curFilePath = null;
	private String curFileName = null;
	
	public AudioRecorder(Context ctx) {
		super();
		this.context = ctx;
		
	}
	
	public String createFilePath() throws IOException{
		String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		curFileName = "TEMP_"+timestamp+"_"+((MainActivity)context).getChannel()+"_"+".3gp";

		File dir = context.getExternalFilesDir(null);	
		File temp = File.createTempFile("TEMP_"+timestamp+"_"+((MainActivity)context).getChannel()+"_", ".3gp",dir);
		
		return temp.getAbsolutePath();
	}
	
	public void startRecording() throws IOException{
		curFilePath = createFilePath();
		
		setAudioSource(MediaRecorder.AudioSource.MIC);
		setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
		setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
		setOnErrorListener(this);
		setOutputFile(curFilePath);
		prepare();
		start();
	}
	
	public void stopRecording(){
		stop();
		reset();
		
		Intent  i = new Intent(context, AudioSendService.class);
		i.putExtra("filename",curFileName);
		i.putExtra("filepath", curFilePath);
		i.putExtra("channel",((MainActivity)context).getChannel());
		// Service disabled PHP scripts not implemented yet
		//context.startService(i);
	}
	@Override
	public void onError(MediaRecorder p1, int p2, int p3)
	{
		Log.e(TAG,"OnError Callback Invoked");
		Toast.makeText(context,"Audio capture failed! Try again.",Toast.LENGTH_LONG).show();
	}
	
	public String getCurFilePath(){
		return curFilePath;
	}
	
	public String getCurFileName(){
		return curFileName;
	}
	
}
