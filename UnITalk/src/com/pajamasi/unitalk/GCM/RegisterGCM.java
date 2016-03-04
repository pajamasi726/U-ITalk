package com.pajamasi.unitalk.GCM;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.pajamasi.unitalk.DB.DBManager;
import com.pajamasi.unitalk.HttpClient.HttpClient;
import com.pajamasi.unitalk.Util.Const;
import com.pajamasi.unitalk.Util.ConstParam;
import com.pajamasi.unitalk.Util.ConstProtocol;


public class RegisterGCM {
	
	private Thread m_RegisterTh;
	private Context m_Context;
	private Handler m_Handler;
	
	public RegisterGCM(Context context)
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
		             println("ȸ�� ���� �Ϸ� : "+regId);
		             
		             // ��� ���̵� DB�� ���� 
		             m_DBManager.m_Member.insertRegID(regId);
		             
		             // �������� ���̵� ���
		             Const.RegID = regId;

		         } catch(Exception ex) {
		             ex.printStackTrace();
		         }
				
			}
		});
		
		m_RegisterTh.start();
	}
	
	
	private void setRegisterServer() // �� ��� ��û
	{
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		// �������� ���� 
		nameValuePairs.add(new BasicNameValuePair(ConstProtocol.PROTOCOL, ConstProtocol.REGISTER_PHONE));
		
		// ���� ����� ���ؼ�, ����ȣ�� ID�� �ѱ��.
		nameValuePairs.add(new BasicNameValuePair(ConstParam.REGISTER_PHONENUM, Const.PHONE_NUM));
		nameValuePairs.add(new BasicNameValuePair(ConstParam.REGISTER_ID, 		Const.RegID));
		
		new HttpClient().sendMessageToServer(Const.SERVER_ADDRESS, nameValuePairs);
	}
	
	
	
	public void println(final String msg)
	{
		 m_Handler.post(new Runnable() {
	            public void run() {
	                Toast.makeText(m_Context, msg, Toast.LENGTH_SHORT).show();
	            }
	        });
	}

}
