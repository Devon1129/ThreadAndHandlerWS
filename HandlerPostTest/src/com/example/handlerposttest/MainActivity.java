package com.example.handlerposttest;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {
	 private Button btnMes1,btnMes2;
	 private TextView tvMessage;
	 private static Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        btnMes1=(Button)findViewById(R.id.btnMes1);
        btnMes2=(Button)findViewById(R.id.btnMes2);
        tvMessage=(TextView)findViewById(R.id.tvMessage);
        
        btnMes1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				//新啟動一個子線程
				new Thread(new Runnable(){

					@Override
					public void run() {
						// tvMessage.setText("...");
						// 以上操作會報錯，無法在子線程中訪問 UI组件，UI组件的屬性必須在 UI線程中訪問
						// 使用 post方式修改 UI组件 tvMessage的 Text屬性
						handler.post(new Runnable(){

							@Override
							public void run() {
								tvMessage.setText(
										"使用 Handler.post在工作線程中發送一段執行到消息隊列中，在主線程中執行。");
								
							}
						});//handler.post
					}
				}).start(); //new Thread
			}//onClick
        });
        
        btnMes2.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				new Thread(new Runnable(){

					@Override
					public void run() {
						 // 使用 postDelayed方式修改 UI组件 tvMessage的 Text屬性值，
						 // 並且延遲 3秒執行。
						handler.postDelayed(new Runnable(){

							@Override
							public void run() {
								tvMessage.setText(
										"使用 Handler.postDelayed在工作線程中發送一段執行到消息隊列中，在主線程中延遲3秒執行。");
							}
						}, 3000);
					}
					
				}).start(); //new Thread
			}//onClick.
        });
    }// onCreate.
}//end class MainActivity.
