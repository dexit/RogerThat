package com.rogerthat.app;
import org.apache.http.*;
import org.apache.http.client.methods.*;
import org.apache.http.client.*;
import java.util.*;
import org.apache.http.message.*;
import org.apache.http.client.entity.*;
import java.io.*;
import android.util.*;
import android.content.*;

public class HttpGet implements Runnable
{
	String url = "";
	HttpPost post;
	HttpClient client;
	HttpResponse response;
	FileInputStream in;
	Context context;
	String channel;
	String TAG = "HttpGet";
	
	public HttpGet(Context context){
		this.context = context;
		channel = Integer.toString(((MainActivity)context).getChannel());
	}
	
	@Override
	public void run()
	{
		post = new HttpPost(url);
		List<NameValuePair> action = new ArrayList<NameValuePair>();
		action.add(new BasicNameValuePair("action","get"));
		action.add(new BasicNameValuePair("device",""));
		action.add(new BasicNameValuePair("channel",channel));
		try
		{
			post.setEntity(new UrlEncodedFormEntity(action));
			response = client.execute(post);
			in = (FileInputStream) response.getEntity().getContent();
		}
		catch (UnsupportedEncodingException e)
		{
			Log.e(TAG,e.getMessage());
		}
		catch(IOException e){
			Log.e(TAG,e.getMessage());
		}
		catch(Exception e){
			Log.e(TAG,e.getMessage());
		}
	}

}
