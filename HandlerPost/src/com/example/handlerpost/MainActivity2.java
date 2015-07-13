package com.example.handlerpost;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

public class MainActivity2 extends Activity{
	
	private class MyHandler extends Handler{
		public MyHandler(){
			Log.e("HandlerThreadID", Long.toString(Thread.currentThread().getId()));
			Log.e("HandlerThreadName", Thread.currentThread().getName());
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		MyHandler mHandler3 = new MyHandler();
		mHandler3.post(new MyThread(new Runnable(){

			@Override
			public void run() {
				Log.e("RunnableThreadID_3", Long.toString(Thread.currentThread().getId()));
				Log.e("RunnableThreadName_3", Thread.currentThread().getName());
			}
		}));
		
		//testTwo
//		MyHandler mHandler2 = new MyHandler();
//		mHandler2.post(new Runnable(){
//
//			@Override
//			public void run() {
//				Log.e("RunnableThreadID", Long.toString(Thread.currentThread().getId()));
//				Log.e("RunnableThreadName", Thread.currentThread().getName());
//			}
//		});
		
		//mThead.start();
		Log.e("ActivityThreadID", Long.toString(Thread.currentThread().getId()));
		Log.e("ActivityThreadName", Thread.currentThread().getName());
	}
	
    //testTwo print:
//  result:
//	HandlerThreadID: 1
//	HandlerThreadName: main
//	ActivityThreadID: 1
//	ActivityThreadName: main
//	RunnableThreadID: 1
//	RunnableThreadName: main
	//沒有產生新的 Thread來執行程式
	
	//testThree.
	private class MyThread extends Thread{
		public MyThread(Runnable r){
			super(r);
			Log.e("ThreadID_3", Long.toString(Thread.currentThread().getId()));
			Log.e("ThreadName_3", Thread.currentThread().getName());
		}
	}
	
    //testThree print:
//  result:
//	HandlerThreadID: 1
//	HandlerThreadName: main
//	ThreadID: 1
//	ThreadName: main
//	ActivityThreadID: 1
//	ActivityThreadName: main
//	RunnableThreadID: 1
//	RunnableThreadName: main
	//沒有呼叫Thread的 start()，Handler不會啟動它。
	

}//end class MainActivity2.


	
