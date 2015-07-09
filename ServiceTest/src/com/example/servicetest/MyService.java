package com.example.servicetest;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

public class MyService extends Service{
	final MyHandler handler = new MyHandler();
	
	
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private class MyHandler extends Handler{

		@Override
		public void handleMessage(Message msg) {
			switch(msg.what){
			case 0:
				Log.e("givemepass", "I am alive.");
				break;
			case 1:
				Log.e("givemepass", "I am dead.");
				break;
			}
			super.handleMessage(msg);
		}
	}

	Thread thread = null;
	@Override
	public void onCreate() {
//		final MyHandler handler = new MyHandler();
		thread = new Thread(new Runnable(){

			@Override
			public void run() {
				Message message = new Message();
				message.what = 0;
				handler.sendMessage(message);
				handler.postDelayed(thread, 1000);
			}
		});
		handler.postDelayed(thread, 1000);
		super.onCreate();
	}

	@Override
	public void onDestroy() {
		Message message = new Message();
		message.what = 1;
		handler.handleMessage(message);
		handler.removeCallbacks(thread);
		super.onDestroy();
	}
}
