package com.rogerthat.app;

import android.app.*;
import android.os.*;
import android.view.*;
import android.widget.*;
import android.view.View.*;
import java.io.*;
import android.util.*;
import android.widget.SeekBar.*;

public class MainActivity extends Activity
{
	private int curChannel;
	private AudioRecorder recorder;
	private AudioPlayer player;
	
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
		setContentView(R.layout.mainlayout);
		
		recorder = new AudioRecorder(this);
		player = new AudioPlayer(this);
		
		Button speak= (Button) findViewById(R.id.speakButton);
		speak.setOnTouchListener(new OnTouchListener(){

				@Override
				public boolean onTouch(View view, MotionEvent event)
				{
					String TAG = "Touch Listener";
					synchronized(this){
						int action = event.getAction() & MotionEvent.ACTION_MASK;
						int pointerIndex = (event.getAction() & MotionEvent.ACTION_POINTER_ID_MASK) >> MotionEvent.ACTION_POINTER_ID_SHIFT;
						int count = event.getPointerCount();
						Log.d(TAG, "Action: "+action);
						for(int i = 0; i < count; i++){
							if(i != pointerIndex)
								continue;
							
							switch(action){
								case MotionEvent.ACTION_DOWN:
								case MotionEvent.ACTION_POINTER_DOWN:
									((Button)view).setText("Speak Now");
									try
									{
										recorder.startRecording();
									}
									catch (IOException e)
									{
										Log.e(TAG, e.getMessage());
									}
									break;
								case MotionEvent.ACTION_UP:
								case MotionEvent.ACTION_POINTER_UP:
								case MotionEvent.ACTION_CANCEL:
									recorder.stopRecording();
									((Button)view).setText("Hold To Speak");
									try
									{
										player.playFile(recorder.getCurFilename());
									}
									catch (IOException e)
									{
										Log.e(TAG, e.getMessage());
									}

									break;
							}
						}
					}
					return false;
				}
			});
			
			SeekBar seek = (SeekBar) findViewById(R.id.seekBar);
			seek.setMax(100);
			seek.setProgress(20);
		seek.setOnSeekBarChangeListener(new OnSeekBarChangeListener(){

				@Override
				public void onProgressChanged(SeekBar p1, int p2, boolean p3)
				{
					float scalar = (float) p2 / 100f;
					player.setVolumeScalar(scalar);
				}

				@Override
				public void onStartTrackingTouch(SeekBar p1)
				{
					return;
				}

				@Override
				public void onStopTrackingTouch(SeekBar p1)
				{
					return;
				}

				
			});
		
    }
	
	public int getChannel(){
		return curChannel;
	}
}
