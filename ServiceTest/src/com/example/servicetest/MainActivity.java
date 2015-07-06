package com.example.servicetest;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {
	private Button btnStart, btnStop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        btnStart = (Button)findViewById(R.id.btnSart);
        btnStop = (Button)findViewById(R.id.btnStop);
        
        final Intent intent = new Intent();
        intent.setClass(MainActivity.this, MyService.class);
        
        btnStart.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				startService(intent);
			}
        });
        
        btnStop.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				stopService(intent);
			}
        });
    }//end method onCreate.
}//end class MainActivity.
