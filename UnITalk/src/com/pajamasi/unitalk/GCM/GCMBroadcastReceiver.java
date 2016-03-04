package com.pajamasi.unitalk.gcm;

import java.net.URLDecoder;

import android.content.Context;
import android.content.Intent;
import android.support.v4.content.WakefulBroadcastReceiver;
import android.widget.Toast;

import com.pajamasi.unitalk.MainActivity;
import com.pajamasi.unitalk.Util.ConstParam;
import com.pajamasi.unitalk.Util.ConstProtocol;
import com.pajamasi.unitalk.activity.ChattingActivity;

/**
 * Ǫ�� �޽����� �޴� Receiver ����
 */
public class GCMBroadcastReceiver extends WakefulBroadcastReceiver {
	
	@Override
	public void onReceive(Context context, Intent intent) {		//������ �޽��� ������  intent�� �ΰ����� ������ ���
		String action = intent.getAction();	
		
		if (action != null) 
		{
			if (action.equals("com.google.android.c2dm.intent.RECEIVE")) // Ǫ�� �޽��� ���� ��
			{ 
				String protocol = 	intent.getStringExtra(ConstProtocol.PROTOCOL);		// �������� ���� command ��� Ű�� value �� 
				
				cacluProtocol(context, intent, protocol);
				// ��Ƽ��Ƽ�� ����
				//sendToActivity(context, from, command, type, data);
				
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
			String rawData = 	intent.getStringExtra(ConstParam.MSG); // �������� ���� msg �޾ƿ���
			
			try 
			{
				// �޼��� ���ڵ�
				data = URLDecoder.decode(rawData, "UTF-8");
			} 
			catch(Exception ex) {
				ex.printStackTrace();
			}
			
			broadCastToActivity(context, "��ε�ĳ��Ʈ : "+data);
		}
	}

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
	private void sendToActivity(Context context,String data) {
		
		
		Toast.makeText(context, data, Toast.LENGTH_SHORT).show();
		System.out.println("GCM RECEIVE : "+data);
		/*
		Intent intent = new Intent(context, MainActivity.class);
		intent.putExtra("from", from);
		intent.putExtra("command", command);
		intent.putExtra("type", type);
		intent.putExtra("data", data);
		
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_SINGLE_TOP);

		context.startActivity(intent);
		*/
	}

}
