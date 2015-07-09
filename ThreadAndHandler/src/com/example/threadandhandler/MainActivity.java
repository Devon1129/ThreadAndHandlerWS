package com.example.threadandhandler;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;

public class MainActivity extends Activity {
	private TextView tv;
	private MyThread thread;
	private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        	
        tv = (TextView)findViewById(R.id.textView1);
        mHandler = new Handler(){
        	int i = 0;

			@Override
			public void handleMessage(Message msg) {
				switch(msg.what){
					case 1:
						i++;
						tv.setText(Integer.toString(i));
						break;
				}
				super.handleMessage(msg);
			}
        };
        
        new Thread(new Runnable(){

    		@Override
    		public void run() {
    			while(true){
    				try{
    					Message msg = new Message();
    					msg.what = 1;
    					mHandler.sendMessage(msg);
    					Thread.sleep(500);
    				}catch(Exception e){
    					e.printStackTrace();
    				}
    			}
    		}
        }).start();
             
        //TestANR();          
    }
  
    public void TestANR(){
    	//test:ANR(Application is Not Responding)
        tv = (TextView)findViewById(R.id.textView1);
        int  i = 0;
        while(true){
        	i++;
        	tv.setText(Integer.toString(i));
        }
    }
}//end class MianActivity.