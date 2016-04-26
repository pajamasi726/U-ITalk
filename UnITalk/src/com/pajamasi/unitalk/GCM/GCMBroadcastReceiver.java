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
 * 푸시 메시지를 받는 Receiver 정의
 */
public class GCMBroadcastReceiver extends WakefulBroadcastReceiver {
	
	@Override
	public void onReceive(Context context, Intent intent) {		//상대방이 메시지 보낼때  intent의 부가적인 정보로 사용
		String action = intent.getAction();	
		
		if (action != null) 
		{
			if (action.equals("com.google.android.c2dm.intent.RECEIVE")) // 푸시 메시지 수신 시
			{ 
				String protocol = 	intent.getStringExtra(ConstProtocol.PROTOCOL);		// 서버에서 보낸 command 라는 키의 value 값 
				
				cacluProtocol(context, intent, protocol);
				// 액티비티로 전달
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
		
		if(protocol.equals(ConstProtocol.BROADCAST)) // 브로드 캐스트 서비스 일때
		{
			System.out.println("PROTOCOL BROADCAST");
			String rawData = 	intent.getStringExtra(ConstParam.MSG); // 서버에서 보낸 msg 받아오기
			
			try 
			{
				// 메세지 디코딩
				data = URLDecoder.decode(rawData, "UTF-8");
			} 
			catch(Exception ex) {
				ex.printStackTrace();
			}
			
			broadCastToActivity(context,data);
		}
		else if(protocol.equals(ConstProtocol.NOTE))
		{
			System.out.println("PROTOCOL NOTE");
			String msg = intent.getStringExtra(ConstParam.MSG); // GCM 으로부터 메세지 받아오기
			
			try 
			{
				// 메세지 디코딩
				msg = URLDecoder.decode(msg, "UTF-8");
			} 
			catch(Exception ex) {
				ex.printStackTrace();
			}
			
			sendToActivity(context,msg);
			
		}// else if end
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

}
