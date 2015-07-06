package com.example.looerthread;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.widget.TextView;

public class MyHandler extends Handler{
	private TextView tv = null;
	public MyHandler(Looper looper){
		super(looper);
	}

	//處理消息
	@Override
	public void handleMessage(Message msg) {
		System.out.println("handle--id-->" + Thread.currentThread()); //1.
		System.out.println("handle--name-->" + Thread.currentThread().getName());// main.
		
		
		super.handleMessage(msg);
	}
	
	

}
