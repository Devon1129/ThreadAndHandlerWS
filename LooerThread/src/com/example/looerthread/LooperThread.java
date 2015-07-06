package com.example.looerthread;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;

//帶有 Looper的線程實現
public class LooperThread extends Thread {
	public Handler mHandler;
	public void run(){
		Looper.prepare();
		mHandler = new Handler(){
			public void handlerMessage(Message msg){
				//process incomingmessages here.
			}
		};
		Looper.loop();
	}
}
