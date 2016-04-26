package com.pajamasi.unitalk.gcm;

import java.net.URLEncoder;
import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.pajamasi.unitalk.CustomCallBackListener.CallBackListener;
import com.pajamasi.unitalk.DB.DBManager;
import com.pajamasi.unitalk.HttpClient.HttpClient;
import com.pajamasi.unitalk.Util.Const;
import com.pajamasi.unitalk.Util.ConstParam;
import com.pajamasi.unitalk.Util.ConstProtocol;


public class GCMRegister implements CallBackListener{
	
	private Thread m_RegisterTh;
	private Context m_Context;
	private Handler m_Handler;
	
	public GCMRegister(Context context)
	{
		this.m_Context = context;
		m_Handler = new Handler(Looper.getMainLooper());
	}
	
	public void setRegister(final DBManager m_DBManager)
	{
		m_RegisterTh = new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
		             GoogleCloudMessaging gcm = GoogleCloudMessaging.getInstance(m_Context);
		             String regId = gcm.register(Const.PROJECT_ID);
		             println("회원 가입 완료 : "+regId);
		             
		             // 등록 아이디 DB에 저장 
		             m_DBManager.m_Member.insertRegID(regId);
		             
		             // 레지스터 아이디 등록
		             Const.RegID = regId;
		             
		             setRegisterServer();

		         } catch(Exception ex) {
		             ex.printStackTrace();
		         }
				
			}
		});
		
		m_RegisterTh.start();
	}
	
	
	private void setRegisterServer() // 폰 등록 요청
	{
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		// 프로토콜 설정 
		nameValuePairs.add(new BasicNameValuePair(ConstProtocol.PROTOCOL, ConstProtocol.REGISTER_PHONE));
		
		try
		{
			// 서비스 등록을 위해서, 폰번호와 ID를 넘긴다.
			nameValuePairs.add(new BasicNameValuePair(ConstParam.REGISTER_NAME,URLEncoder.encode(Const.NAME, "UTF-8")));
			nameValuePairs.add(new BasicNameValuePair(ConstParam.REGISTER_PHONENUM,URLEncoder.encode(Const.PHONE_NUM, "UTF-8")));
			nameValuePairs.add(new BasicNameValuePair(ConstParam.REGISTER_ID,URLEncoder.encode(Const.RegID, "UTF-8")));
		}catch(Exception e){e.printStackTrace();}
		
		
		new HttpClient(this).sendMessageToServer(Const.SERVER_ADDRESS, nameValuePairs);
	}
	
	
	
	public void println(final String msg)
	{
		 m_Handler.post(new Runnable() {
	            public void run() {
	                Toast.makeText(m_Context, msg, Toast.LENGTH_SHORT).show();
	            }
	        });
	}

	@Override
	public void callBackMethod(String str) {
		
	}

	@Override
	public void callBackMethod(int i) {
		
	}

}
