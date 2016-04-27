package com.pajamasi.unitalk.gcm;

import java.net.URLDecoder;
import java.util.List;

import com.pajamasi.unitalk.MainActivity;
import com.pajamasi.unitalk.R;
import com.pajamasi.unitalk.DB.DBManager;
import com.pajamasi.unitalk.Util.Const;
import com.pajamasi.unitalk.Util.ConstParam;
import com.pajamasi.unitalk.Util.ConstProtocol;
import com.pajamasi.unitalk.Util.Util;
import com.pajamasi.unitalk.activity.ChattingActivity;
import com.pajamasi.unitalk.file.CustomFileWriter;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.WakefulBroadcastReceiver;

/**
 * 푸시 메시지를 받는 Receiver 정의
 */
public class GCMBroadcastReceiver extends WakefulBroadcastReceiver {
	
	int COUNT = 0;
	
	private DBManager 		  m_DBManager; 	// DB매니저
	
	@Override
	public void onReceive(Context context, Intent intent) {	//상대방이 메시지 보낼때  intent의 부가적인 정보로 사용
		
		String action = intent.getAction();	
		
		if (action != null) 
		{
			if (action.equals("com.google.android.c2dm.intent.RECEIVE")) // 푸시 메시지 수신 시
			{ 
				String protocol = 	intent.getStringExtra(ConstProtocol.PROTOCOL);		// 서버에서 보낸 command 라는 키의 value 값 
				
				cacluProtocol(context, intent, protocol);
				
			} 
			else 
			{
				System.out.println("Unknown action : " + action);
			}
		} 
		else 
		{
			System.out.println("action is null.");
		}
		
	}
	
	
	private void cacluProtocol(Context context, Intent intent, String protocol)
	{
		String data = "";
		
		if(protocol.equals(ConstProtocol.BROADCAST)) // 브로드 캐스트 서비스 일때
		{
			System.out.println("PROTOCOL BROADCAST");
			
			String msg = 	intent.getStringExtra(ConstParam.MSG); // 서버에서 보낸 msg 받아오기
			
			// 메세지 디코딩
			msg = Util.URLDecoding(msg);
			
			broadCastToActivity(context,data);
		}
		else if(protocol.equals(ConstProtocol.NOTE))
		{
			System.out.println("PROTOCOL NOTE");
			String msg = intent.getStringExtra(ConstParam.MSG); // GCM 으로부터 메세지 받아오기
			
			msg = Util.URLDecoding(msg);
			
			check_TopActivity(context, intent, msg);
			
		}// else if end
	}
	
	// 최상위 액티비티 체크
	private void check_TopActivity(Context context, Intent intent,String msg)
	{
		String activity = getTopActivity(context);
		
		if(activity.equals(Const.MainActivity))
		{						
			sendToNotification(context, intent, msg);
		}
		else if(activity.equals(Const.ChattingActivity))
		{
			sendToActivity(context,msg);
		}
		else
		{
			sendToNotification(context, intent, msg);
		}
	}

	// 알림 생성
	private void sendToNotification(Context context, Intent intent, String msg)
	{
		
		String sender = intent.getStringExtra(ConstParam.SENDER);
		// 발신자 디코딩
		sender = Util.URLDecoding(sender);
		
		String senderphonenum = intent.getStringExtra(ConstParam.SENDERPHONENUM);
		// 발신자 전화번호 디코딩
		senderphonenum = Util.URLDecoding(senderphonenum);
		
		
		System.out.println("보낸 사람 : "+sender);
		System.out.println("보낸 사람 번호 : "+senderphonenum);
		System.out.println("메시지 : "+msg);
		
		// 파일 내용에 내용 추가 하기
		CustomFileWriter fw = new CustomFileWriter(sender);
		
		fw.write(sender+" : "+msg);
		
		fw.close();
		
		// db에 내용 업데이트 하기
		m_DBManager = DBManager.get_DBManager(context);
		m_DBManager.openDB();
		m_DBManager.m_ChatDB.upDateComment(sender+" : "+msg, sender, senderphonenum);
		
		
		
		NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

		Resources res = context.getResources();

		Intent notificationIntent = new Intent(context, ChattingActivity.class);
		notificationIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_SINGLE_TOP);

		
		notificationIntent.putExtra(ConstProtocol.PROTOCOL, ConstProtocol.CHAT_SETTING);
		notificationIntent.putExtra("Name", sender);
		notificationIntent.putExtra("PhoneNumber", senderphonenum);
		
		PendingIntent contentIntent = PendingIntent.getActivity(context, 0, notificationIntent,
				PendingIntent.FLAG_UPDATE_CURRENT);

		NotificationCompat.Builder builder = new NotificationCompat.Builder(context)

		.setContentTitle(sender)

		.setContentText(msg)

		.setTicker("U&Talk 메시지 도착")

		.setSmallIcon(R.drawable.ic_launcher)

		.setLargeIcon(BitmapFactory.decodeResource(res, R.drawable.icon_talk))

		.setContentIntent(contentIntent)

		.setAutoCancel(true)

		.setWhen(System.currentTimeMillis())

		.setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE | Notification.DEFAULT_LIGHTS)

		.setNumber(COUNT);

		Notification n = builder.build();

		// 보낸 사람의 전화번호로 고유값을 매긴다
		nm.notify(1234, n);
	}
	
	// 채팅 액티비티에 메세지 보내기
	private void broadCastToActivity(Context context, String msg) {
		
		Intent intent = new Intent(context, ChattingActivity.class);
		intent.putExtra(ConstProtocol.PROTOCOL, ConstProtocol.BROADCAST);
		intent.putExtra(ConstParam.MSG, msg);
		
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_SINGLE_TOP);

		context.startActivity(intent);
	}


	/**
	 * 메인 액티비티로 수신된 푸시 메시지의 데이터 전달
	 */
	private void sendToActivity(Context context, String msg) {
		
		Intent intent = new Intent(context, ChattingActivity.class);
		intent.putExtra(ConstProtocol.PROTOCOL, ConstProtocol.NOTE);
		intent.putExtra(ConstParam.MSG, msg);
		
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_SINGLE_TOP);

		context.startActivity(intent);
	}
	
	/** 현재 안드로이드의 최상위 액티비티를 가져온다 */
	private String getTopActivity(Context context)
	{
		String activity = "";
		
		ActivityManager am = (ActivityManager)context.getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningTaskInfo> Info = am.getRunningTasks(1);
		ComponentName topActivity = Info.get(0).topActivity;
		
		activity = topActivity.getClassName();
		
		return activity;
	}

}
