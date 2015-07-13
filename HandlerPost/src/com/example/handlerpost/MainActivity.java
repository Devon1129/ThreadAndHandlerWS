package com.example.handlerpost;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mThread.start();
        Log.e("ActivityThreadID", Long.toString(Thread.currentThread().getId()));
        Log.e("ActivityThreadName", Thread.currentThread().getName());
        
    }
    
    private Handler mHandler = new Handler(){
    	public void handleMessage(Message msg){
    		switch(msg.what){
    		case 0:
    			Log.e("HandlerThreadID", Long.toString(Thread.currentThread().getId()));
    			Log.e("HandlerThreadName", Thread.currentThread().getName());
    			break;
    		}
    	}
    };
    
    private Thread mThread = new Thread(new Runnable(){

		@Override
		public void run() {
			Log.e("mThreadID", Long.toString(Thread.currentThread().getId()));
			Log.e("mThreadName", Thread.currentThread().getName());
			Message msg = new Message();
			msg.what = 0;
			mHandler.sendMessage(msg);
		}
    });
    
    //testOne print:
//    result:
//    mThreadID: 78
//    mThreadName: Thread-78
//    ActivityThreadID: 1
//    ActivityThreadName: main
//    HandlerThreadID: 1
//    HandlerThreadName: main
    //產生一支新的 Thread來執行程式。
  
}
