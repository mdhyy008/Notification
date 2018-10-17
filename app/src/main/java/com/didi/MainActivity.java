package com.didi;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.*;
import android.support.v7.app.*;
import android.content.*;
import android.widget.*;
import android.graphics.*;
import android.os.*;
import android.telephony.*;

public class MainActivity extends AppCompatActivity
{

	Switch sw;
	TextView te;
	int ii;
	boolean issend;
	
	String tm1;
    @Override
    protected void onCreate(Bundle savedInstanceState)
	{
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

		//handler.post(task);//立即调用
		
		te=(TextView)findViewById(R.id.mainactivityTextView1);
		sw =(Switch)findViewById(R.id.mainactivitySwitch1);
		sw.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
				{
					if (isChecked)
					{
						issend = true;
						te.setText("正在安排接单");
						te.setTextColor(Color.GREEN);
						handler.post(task);//立即调用
						//Toast.makeText(getApplicationContext(),"",1).show();
					}
					else
					{
						te.setText("休息一会儿！");
						te.setTextColor(Color.RED);
						issend = false;
						handler.removeCallbacks(task);
					}

				}
			});
			
			
			

	}




	private Handler handler = new Handler();   
    private Runnable task = new Runnable() {  
        public void run() {   

			handler.postDelayed(this,2*1000);//设置循环时间，此处是5秒
			//取得当前时间
			sendN();

        }   
    };




	void sendN()
	{
		ii++;
		NotificationManager manager = (NotificationManager) this.getSystemService(getApplicationContext().NOTIFICATION_SERVICE);
		NotificationCompat.Builder builder = new NotificationCompat.Builder(this);

//设置显示的图标
		builder.setDefaults(Notification.DEFAULT_SOUND|Notification.DEFAULT_VIBRATE);
		
		TelephonyManager tm = (TelephonyManager)this.getSystemService(Context.TELEPHONY_SERVICE);  
		//String deviceid = tm.getDeviceId();//获取智能设备唯一编号  
		String te1  = tm.getLine1Number();//获取本
		
		String name[]={"刘","李","麻痹","鸡掰","辣鸡","谢广坤","狗鹿晗","傻逼","二狗","张三"};
		
		String text ="滴滴！"+name[(int)(Math.random()*name.length)]+"师傅接到手机号为"+te1.replace("+86","")+"的单子。请" + ii + "分钟内接到乘客,逾期罚款一万元";
		
		builder.setWhen(System.currentTimeMillis());
		builder.setSmallIcon(R.mipmap.ic_notification);
		builder.setLargeIcon(BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher));
		
		builder.setContentTitle("接单成功");
		builder.setContentText(text);
		//builder.setTicker("接单成功！");
//构建
		builder.setPriority(Notification.PRIORITY_MAX);
		Intent resolveIntent = getApplicationContext().getPackageManager().getLaunchIntentForPackage("com.sdu.didi.psnger");

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            builder.setStyle(new NotificationCompat.BigTextStyle().bigText(text)
							 .setBigContentTitle("接单成功"));
        }

				PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, resolveIntent, 0);  
		builder.setContentIntent(pendingIntent);

		Notification notification = builder.build();
		notification.flags = Notification.FLAG_SHOW_LIGHTS;

		//发出通知，参数一为通知ID，
		manager.notify(ii, notification);
	}

}
