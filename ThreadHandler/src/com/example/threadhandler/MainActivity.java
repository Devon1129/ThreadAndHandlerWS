package com.example.threadhandler;

import java.util.Date;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {
	 private static final int MSG_SUCCESS = 0;//獲取圖片成功的標誌
	 private static final int MSG_FAILURE = 1;//獲取圖片失敗的標誌 
	 
	 private ImageView mImageView;  
	 private Button mButton, tButton;
	 private TextView tv;
	 private Thread mThread;
	
	 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        
        mImageView= (ImageView) findViewById(R.id.iv);//显示图片的ImageView
        tv = (TextView)findViewById(R.id.textView1);
        mButton = (Button) findViewById(R.id.button);
        tButton = (Button) findViewById(R.id.button2);
        
        tButton.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				Log.d("test Handler", "time clikced!!");
				Date curDate = new Date();
				String time = curDate.toString();
				tv.setText(time);
			}
        });
       
        mButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				 if(mThread == null) {					 
					 mBitmap = null;
					 
					 // Clear image
					 mImageView.setImageBitmap(null);
					 mButton.setEnabled(false);
					 
                    mThread = new Thread(runnable);  
                    mThread.start();
	                }  
	                else {  
	                    Toast.makeText(
	                    		getApplication(),
	                    		getApplication().getString(R.string.thread_started), 
	                    		Toast.LENGTH_LONG).show();  
	                }
				
				 // wait for image downloaded
//				 while(mBitmap == null)
//				 {
//					 try {
//						Thread.sleep(500);
//					} catch (InterruptedException e) {
//						// TODO Auto-generated catch block
//						e.printStackTrace();
//					}
//				 }
//				 
//				 rSetImg.run();

			} 
        });
    }
    
    private Bitmap mBitmap;
    protected void downloadImgTest() {
		// Main Thread
    	
    	HttpClient hc = new DefaultHttpClient();  
        HttpGet hg =
       		new HttpGet("http://csdnimg.cn/www/images/csdnindex_logo.gif");//獲取csdn的logo.  
        
        //使用 bitmap對象儲存這個 Logo的像素信息。   
          
        try {  
           HttpResponse hr = hc.execute(hg);  
           mBitmap = BitmapFactory.decodeStream(hr.getEntity().getContent());  
        } catch (Exception e) {  
           mHandler.obtainMessage(MSG_FAILURE).sendToTarget();//獲取圖片失敗。  
           return;  
        }  
        
        // After downloading Image
        
        rSetImg.run();
	}
    
    //另外一种更简洁的发送消息给ui线程的方法。  
    Runnable rSetImg = new Runnable() {
        
		@Override  
		//run()方法会在ui线程执行
		public void run() {  
			mImageView.setImageBitmap(mBitmap);
			mButton.setEnabled(true);
			mThread = null;
		}
    };

	//為了不阻塞 ui線程，我們使用 mThread從網路獲取了 CSDN的 LOGO。
    Runnable runnable = new Runnable() {

		@Override
		public void run() {
			 HttpClient hc = new DefaultHttpClient();  
	         HttpGet hg =
	        		new HttpGet("http://csdnimg.cn/www/images/csdnindex_logo.gif");//獲取csdn的logo.
//	        		 new HttpGet("http://sales.artlib.net.tw/albums/20120708RAN_MAO_CIN/826_13417409194.jpg");
	         
	         //使用 bitmap對象儲存這個 Logo的像素信息。   
	         final Bitmap bm;  
	         try {  
	            HttpResponse hr = hc.execute(hg);  
	            //bm = BitmapFactory.decodeStream(hr.getEntity().getContent());
	            mBitmap = BitmapFactory.decodeStream(hr.getEntity().getContent());		            
             } catch (Exception e) {  
                mHandler.obtainMessage(MSG_FAILURE).sendToTarget();//獲取圖片失敗。  
                return;  
             }  
            //獲取圖片成功，向ui線程發送MSG_SUCCESS標誌和bitmap對象。
//          mHandler.obtainMessage(MSG_SUCCESS,bm).sendToTarget();
//		    mImageView.setImageBitmap(bm); //出错！不能在非 ui线程操作 ui元素  
    
//		    mImageView.post(new Runnable() {//另外一种更简洁的发送消息给ui线程的方法。  
//		                
//		    	@Override  
//		        public void run() {//run()方法会在ui线程执行  
//		        	mImageView.setImageBitmap(bm);  
//		        }  
//		 	});
	         
	         Log.d("test Handler", "complete download");
	         mImageView.post(rSetImg);
		}  
    };
    
	 private Handler mHandler = new Handler(){
		 public void handleMessage(Message msg){//此方法在 ui線程運行.
			 switch(msg.what){
			 case MSG_SUCCESS:
				 mImageView.setImageBitmap((Bitmap)msg.obj);
				 Toast.makeText(
						 MainActivity.this,
						 getApplication().getString(R.string.thread_started), 
						 Toast.LENGTH_LONG).show();
				 break;
			 case MSG_FAILURE:
				 Toast.makeText(
						 getApplicationContext(), 
						 getApplication().getString(R.string.get_pic_failure), 
						 Toast.LENGTH_LONG).show();
				 break;
			 }
		 }
	 };
}
