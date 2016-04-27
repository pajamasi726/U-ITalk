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
 * Ǫ�� �޽����� �޴� Receiver ����
 */
public class GCMBroadcastReceiver extends WakefulBroadcastReceiver {
	
	int COUNT = 0;
	
	private DBManager 		  m_DBManager; 	// DB�Ŵ���
	
	@Override
	public void onReceive(Context context, Intent intent) {	//������ �޽��� ������  intent�� �ΰ����� ������ ���
		
		String action = intent.getAction();	
		
		if (action != null) 
		{
			if (action.equals("com.google.android.c2dm.intent.RECEIVE")) // Ǫ�� �޽��� ���� ��
			{ 
				String protocol = 	intent.getStringExtra(ConstProtocol.PROTOCOL);		// �������� ���� command ��� Ű�� value �� 
				
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
		
		if(protocol.equals(ConstProtocol.BROADCAST)) // ��ε� ĳ��Ʈ ���� �϶�
		{
			System.out.println("PROTOCOL BROADCAST");
			
			String msg = 	intent.getStringExtra(ConstParam.MSG); // �������� ���� msg �޾ƿ���
			
			// �޼��� ���ڵ�
			msg = Util.URLDecoding(msg);
			
			broadCastToActivity(context,data);
		}
		else if(protocol.equals(ConstProtocol.NOTE))
		{
			System.out.println("PROTOCOL NOTE");
			String msg = intent.getStringExtra(ConstParam.MSG); // GCM ���κ��� �޼��� �޾ƿ���
			
			msg = Util.URLDecoding(msg);
			
			check_TopActivity(context, intent, msg);
			
		}// else if end
	}
	
	// �ֻ��� ��Ƽ��Ƽ üũ
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

	// �˸� ����
	private void sendToNotification(Context context, Intent intent, String msg)
	{
		
		String sender = intent.getStringExtra(ConstParam.SENDER);
		// �߽��� ���ڵ�
		sender = Util.URLDecoding(sender);
		
		String senderphonenum = intent.getStringExtra(ConstParam.SENDERPHONENUM);
		// �߽��� ��ȭ��ȣ ���ڵ�
		senderphonenum = Util.URLDecoding(senderphonenum);
		
		
		System.out.println("���� ��� : "+sender);
		System.out.println("���� ��� ��ȣ : "+senderphonenum);
		System.out.println("�޽��� : "+msg);
		
		// ���� ���뿡 ���� �߰� �ϱ�
		CustomFileWriter fw = new CustomFileWriter(sender);
		
		fw.write(sender+" : "+msg);
		
		fw.close();
		
		// db�� ���� ������Ʈ �ϱ�
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

		.setTicker("U&Talk �޽��� ����")

		.setSmallIcon(R.drawable.ic_launcher)

		.setLargeIcon(BitmapFactory.decodeResource(res, R.drawable.icon_talk))

		.setContentIntent(contentIntent)

		.setAutoCancel(true)

		.setWhen(System.currentTimeMillis())

		.setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE | Notification.DEFAULT_LIGHTS)

		.setNumber(COUNT);

		Notification n = builder.build();

		// ���� ����� ��ȭ��ȣ�� �������� �ű��
		nm.notify(1234, n);
	}
	
	// ä�� ��Ƽ��Ƽ�� �޼��� ������
	private void broadCastToActivity(Context context, String msg) {
		
		Intent intent = new Intent(context, ChattingActivity.class);
		intent.putExtra(ConstProtocol.PROTOCOL, ConstProtocol.BROADCAST);
		intent.putExtra(ConstParam.MSG, msg);
		
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_SINGLE_TOP);

		context.startActivity(intent);
	}


	/**
	 * ���� ��Ƽ��Ƽ�� ���ŵ� Ǫ�� �޽����� ������ ����
	 */
	private void sendToActivity(Context context, String msg) {
		
		Intent intent = new Intent(context, ChattingActivity.class);
		intent.putExtra(ConstProtocol.PROTOCOL, ConstProtocol.NOTE);
		intent.putExtra(ConstParam.MSG, msg);
		
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_SINGLE_TOP);

		context.startActivity(intent);
	}
	
	/** ���� �ȵ���̵��� �ֻ��� ��Ƽ��Ƽ�� �����´� */
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
