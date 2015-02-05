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
	String TAG ="HttpSend";
	private String response =null;
	private int responseCode = -1 ;
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
		catch (Exception e)
		{
			Log.i(TAG, e.getMessage());
		}
	}

	@Override
	public void run()
	{
		String lineEnd = "\r\n";
		String twoHyphens = "--";
		String boundary = "*****";
		
		try
		{
			HttpURLConnection conn = (HttpURLConnection) sendUrl.openConnection();
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setUseCaches(false);
			// setFixedLengthStreamingMode experimental
			conn.setFixedLengthStreamingMode(is.available());
			//..
			conn.setRequestMethod("POST");
			conn.setRequestProperty("Connection", "Keep-Alive");
			conn.setRequestProperty("Content-Type", "multipart/form-data;boundary="+boundary);
			
			DataOutputStream dos = new DataOutputStream(conn.getOutputStream());
			dos.writeBytes(twoHyphens + boundary + lineEnd);
			dos.writeBytes("Content-Disposition: form-data;name=\"title\"" +lineEnd);
			dos.writeBytes(lineEnd);
			dos.writeBytes(title);
			dos.writeBytes(lineEnd);
			dos.writeBytes(twoHyphens+boundary+lineEnd);
			
			
			dos.writeBytes("Content-Disposition: form-data;name=\"description\""+lineEnd);
			dos.writeBytes(lineEnd);
			dos.writeBytes(desc);
			dos.writeBytes(lineEnd);
			dos.writeBytes(twoHyphens+boundary+lineEnd);
			
			dos.writeBytes("Content-Disposition: form-data;name=\"uploadedmsg\";filename=\""+filename+"\""+lineEnd);
			dos.writeBytes(lineEnd);
			
			int bytesAvailable = is.available();
			int maxBufferSize = 1024;
			int maxSize = Math.min(bytesAvailable, maxBufferSize);
			byte[] buffer = new byte[maxSize];
			int bytesRead = is.read(buffer, 0, maxSize);
			
			while(bytesRead > 0){
				dos.write(buffer,0,maxSize);
				bytesAvailable = is.available();
				maxSize = Math.min(bytesAvailable,maxBufferSize);
				bytesRead = is.read(buffer,0,maxSize);
			}
			
			is.close();
			dos.flush();
			responseCode = conn.getResponseCode();
			InputStream rs = conn.getInputStream();
			int chr;
			StringBuffer s = new StringBuffer();
			while((chr = rs.read()) != -1){
				s.append((char) chr);
			}
			response = s.toString();
		}
		catch (Exception e)
		{
			Log.e(TAG, e.getMessage());
		}

	}
	
	public String getResponse(){
		return response;
	}
	
	public int getResponseCode(){
		return responseCode;
	}

}
