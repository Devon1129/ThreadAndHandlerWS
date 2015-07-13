package com.example.handlerposttest2;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends Activity {
	private Button btnDown;
	private ImageView ivImage;
	private static String image_path =
			"http://ww4.sinaimg.cn/bmiddle/786013a5jw1e7akotp4bcj20c80i3aao.jpg";
	private ProgressDialog dialog;
	private static int IS_FINISH = 1;
    
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        btnDown = (Button) findViewById(R.id.btnDown);
        ivImage = (ImageView) findViewById(R.id.ivSinaImage);
        
        dialog = new ProgressDialog(this);
        dialog.setTitle("提示");
        dialog.setMessage("正在下载，請稍候...");
        dialog.setCancelable(false);
        
        btnDown.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				//開啟一個子線程，用於下載圖片。
				new Thread(new MyThread()).start();
				dialog.show();
			}
        });
    }
	
	// 一個静態的 Handler，Handler建議聲明為靜態的。
	private static  Handler handler = new Handler(){

		//在 Handler中獲取消息，重寫 handlerMessage()方法
		@Override
		public void handleMessage(Message msg) {
			//判斷消息碼是否為1
			if(msg.what == IS_FINISH){
				byte[] data = (byte[])msg.obj;
				Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
				ivImage.setImageBitmap(bmp);
				dialog.dismiss();
			}
		}
	};
	
	
	public class MyThread implements Runnable{

		@Override
		public void run() {
			HttpClient hc = new DefaultHttpClient();
			HttpGet hg = new HttpGet(image_path);
			HttpResponse hr = null;
			try{
				hr = hc.execute(hg);
				if(hr.getStatusLine().getStatusCode() == 200){
					byte[] data = EntityUtils.toByteArray(hr.getEntity());
					//使用 Message的方式:
					//獲取一個 Message 對象，設置 what為1
					Message msg = Message.obtain();
					msg.obj = data;
					msg.what = IS_FINISH;
					//發送這個消息到消息隊列中
					handler.sendMessage(msg);
//					
//					
//					使用 post的方式:
//					// 得到一個 Bitmap對象，並且為了使其在 post内部可以訪問，必須聲明為 final.
//					final Bitmap bmp = BitmapFactory.decodeByteArray(data, 0, data.length);
//					handler.post(new Runnable(){
//
//						@Override
//						public void run() {
//							// 在 Post中操作 UI组件 ImageView
//							ivImage.setImageBitmap(bmp);
//						}
//					});
//					//隱藏對話框。
//					dialog.dismiss();
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
	
}//end class MainActivity.
