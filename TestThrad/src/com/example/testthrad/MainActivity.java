package com.example.testthrad;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;

public class MainActivity extends Activity {
	private Handler mUI_Handler = new Handler();
	private Handler mThreadHandler;
	private HandlerThread mThread;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        mThread = new HandlerThread("name");
        mThread.start();
        mThreadHandler = new Handler(mThread.getLooper());
        mThreadHandler.post(r1);
    }
    
    private Runnable r1 = new Runnable(){

		@Override
		public void run() {
			// TODO Auto-generated method stub
			//......do thing.
			mUI_Handler.post(r2);
		}
    };
    
    private Runnable r2 = new Runnable(){

		@Override
		public void run() {
			// TODO Auto-generated method stub
			//顯示畫面動作.
		}
    };

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if(mThreadHandler != null){
			mThreadHandler.removeCallbacks(r1);
		}
		if(mThread != null){
			mThread.quit();
		}
	}
    
}
