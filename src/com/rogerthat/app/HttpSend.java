package com.rogerthat.app;
import java.io.*;
import java.net.*;
import android.util.*;

public class HttpSend implements Runnable
{
	URL sendUrl;
	String title;
	String desc;
	FileInputStream is;
	byte[] data;
	String TAG ="HttpSend";
	String response;
	String filename;
	public HttpSend(String url, String title, String desc, FileInputStream is, String filename){
		this.title = title;
		this.desc = desc;
		this.is = is;
		this.filename = filename;
		try
		{
			sendUrl = new URL(url);
		}
		catch (MalformedURLException e)
		{
			Log.i(TAG, "Malformed Url Exception");
		}
	}

	@Override
	public void run()
	{
		
	}

}
