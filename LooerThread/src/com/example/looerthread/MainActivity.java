package com.example.looerthread;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener{
	private Button btnStart = null;
	private TextView tv = null;
	private MyHandler mHandler = null;
	private Thread thread;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        btnStart = (Button)findViewById(R.id.btnStart);
        btnStart.setOnClickListener(this);
        tv = (TextView)findViewById(R.id.tv);
    }

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.btnStart:
			//每次單擊都會建立一個新的線程，這是不恰當的。
			thread = new MyThread();
			//開始一個子線程。
			thread.start();
			break;
		}
	}
	
	public class MyHandler extends Handler{
		public MyHandler(Looper looper){
			super(looper);
		}

		//處理消息
		@Override
		public void handleMessage(Message msg) {
			System.out.println("handle--id-->" + Thread.currentThread()); //1.
			System.out.println("handle--name-->" + Thread.currentThread().getName());// main.
			
			//此時 handle在主線程當中，可以處理介面更新。
			tv.setText(msg.obj.toString());
			
			super.handleMessage(msg);
		}
	}
	
	/**
	 * Android 會自動替主線程建立 Message Queue，在這個子線程裡並沒有建立 Message Queue，所以，myLooper值為 null，
	 * 而 mainLooper則指向主線程裡的 Looper，於是執行到: mHandler = new MyHandler(mainLooper);
	 * 此 mHandler屬於主線程。 mHandler.sendMessage(m);
	 * @author andieguo.
	 */
	
	private class MyThread extends Thread{
		/**
		 * Looper.myLooper(); 獲得當前的 Looper。
		 * Looper.getMainLooper(); 獲得 UI線程的 Looper。
		 */

		@Override
		public void run() {
			System.out.println("MyThread---id--->" + Thread.currentThread().getId());
			System.out.println("MyThread---name--->" + Thread.currentThread().getName());
			
			Looper curLooper = Looper.myLooper(); //MyThread線程
			Looper mainLooper = Looper.getMainLooper();
			
			String msg;
			if(curLooper == null){
				//把當前 handler綁定在 mainLooper線程上，此時 mainLooper線程當中。
				mHandler = new MyHandler(mainLooper);
				msg = "currLooper is null";
			}else{
				//將 mHandler與 curLooper綁定
				mHandler = new MyHandler(curLooper);
				msg = "This is curLooper";
			}
			mHandler.removeMessages(0);
			Message m = mHandler.obtainMessage(1, 1, 1, msg);
			//mHandler 對象而將消息 m傳給 curLooper，然後放入 Message Queue裡。
			//Looper 對象看到 Message Queue裡有消息 m，就將它們廣播出去，
			//    mHandler對象接到此訊息時，會呼叫其 handleMessage()來處理。
			
			//將消息添加到了 curLooper相關連的消息對列中。
			mHandler.sendMessage(m);
		}
	}	
}//end class MainActivity.
