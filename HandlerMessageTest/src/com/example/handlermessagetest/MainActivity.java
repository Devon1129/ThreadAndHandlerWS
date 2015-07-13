package com.example.handlermessagetest;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {
	private Button btn1, btn2, btn3, btn4,btn5;
	private static TextView tvMes;
	private static Handler handler = new Handler() {
		@Override
		public void handleMessage(android.os.Message msg) {
			if(msg.what == 3 || msg.what == 5){
				tvMes.setText("what = " + msg.what + ",�o�O�@�ӪŮ���");
			}else{
				tvMes.setText("what= " + msg.what + ", " + msg.obj.toString());
			}
		}//this is method.
	};
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        tvMes = (TextView) findViewById(R.id.tvMes);
        btn1 = (Button) findViewById(R.id.btnMessage1);
        btn2 = (Button) findViewById(R.id.btnMessage2);
        btn3 = (Button) findViewById(R.id.btnMessage3);
        btn4 = (Button) findViewById(R.id.btnMessage4);
        btn5 = (Button) findViewById(R.id.btnMessage5);
        
        btn1.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				//�ϥ� Messagwe.Obtain + Handler.sendMessage()�o�e�����C
				new Thread(new Runnable(){

					@Override
					public void run() {
						Message msg = Message.obtain();
//						Message msg = new Message();
						//�ۭq��1�A���եΡC
						msg.what = 1;
						msg.obj = "�ϥ� Message.Obtain + Handler.sendMessage()�o�e����";
						handler.sendMessage(msg);
					}
				}).start();
			}
        });//end btn1.setOnClickListener.
    
        btn2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				//�ϥ� Message.sendToTarget�o�e�����C
				new Thread(new Runnable(){

					@Override
					public void run() {
						Message msg = Message.obtain(handler);
						msg.what = 2;
						msg.obj = "�ϥ� Message.sendToTarget�o�e�����C";
						msg.sendToTarget();
					}
				}).start();
			}
        });//end btn2.setOnClickListenr.
        
        btn3.setOnClickListener(new View.OnClickListener() {
        	// �o�e�@�ө�������C
			
        	@Override
			public void onClick(View v) {
        		new Thread(new Runnable(){

					@Override
					public void run() {
//						handler.sendEmptyMessage(3);
						handler.sendEmptyMessageDelayed(3, 3000);
					}
        		}).start();
			}
        });//end btn3.setOnClickListener.
        
        btn4.setOnClickListener(new View.OnClickListener() {
        	//�� Handler.sendMessage�o�e�������
			@Override
			public void onClick(View v) {
				new Thread(new Runnable(){

					@Override
					public void run() {
						Message msg = Message.obtain();
						msg.what = 4;
						msg.obj = "�ϥ� Message.Obtain + Handler.sendMessage()�o�e�������";
						handler.sendMessageDelayed(msg, 3000);
					}
				}).start();
			}
        });//end btn4.setOnClickListener.
        
        btn5.setOnClickListener(new View.OnClickListener() {
        	//�o�e�@�ө��𪺪Ů���
			@Override
			public void onClick(View v) {
				new Thread(new Runnable(){

					@Override
					public void run() {
						handler.sendEmptyMessageDelayed(5, 3000);
					}
				}).start();
			}
        });
        
    }//end method onCreate.
}
