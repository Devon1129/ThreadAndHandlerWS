package com.example.handlerthreaddemo;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class MainActivity extends Activity {

	private Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch(msg.what){
			case 0:
				Log.e("HandlerThreadID",Long.toBinaryString(Thread.currentThread().getId()));  
	        	Log.e("HandlerThreadName",Thread.currentThread().getName());
	        	break;
			}
			super.handleMessage(msg);
		}
		
	};
	private Thread mThread = new Thread(new Runnable() {  
        public void run() {  
        	Log.e("ThreadID",Long.toBinaryString(Thread.currentThread().getId()));  
        	Log.e("ThreadName",Thread.currentThread().getName());
        	Message msg = new Message();
        	msg.what = 0;
        	handler.sendMessage(msg);
        }  
         
    });
	private class MyHandler extends Handler{
		public MyHandler(){
			Log.e("HandlerThreadID",Long.toBinaryString(Thread.currentThread().getId()));  
        	Log.e("HandlerThreadName",Thread.currentThread().getName());
		}
	}
	private class MyThread extends Thread{
		public MyThread(Runnable r){
			super(r);
			Log.e("ThreadID",Long.toBinaryString(Thread.currentThread().getId()));  
        	Log.e("ThreadName",Thread.currentThread().getName());
		}
	}
    @Override  
    public void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);
        MyHandler mHandler2 = new MyHandler();
        mHandler2.post(new MyThread(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				Log.e("RunnableThreadID",Long.toBinaryString(Thread.currentThread().getId()));  
		        Log.e("RunnableThreadName",Thread.currentThread().getName());
			}
        	
        }));
        //mThread.start();
        Log.e("ActivityThreadID",Long.toBinaryString(Thread.currentThread().getId()));  
        Log.e("ActivityThreadName",Thread.currentThread().getName());  
    }  
 
}
