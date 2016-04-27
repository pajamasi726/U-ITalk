package com.pajamasi.unitalk.activity;

import java.io.FileWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.pajamasi.unitalk.R;
import com.pajamasi.unitalk.CustomCallBackListener.CallBackListener;
import com.pajamasi.unitalk.DB.DBManager;
import com.pajamasi.unitalk.HttpClient.HttpClient;
import com.pajamasi.unitalk.Util.Const;
import com.pajamasi.unitalk.Util.ConstParam;
import com.pajamasi.unitalk.Util.ConstProtocol;
import com.pajamasi.unitalk.Util.Util;
import com.pajamasi.unitalk.file.CustomFileWriter;
import com.pajamasi.unitalk.file.FileManager;
import com.pajamasi.unitalk.itemDTO.User_ItemDTO;

/**
 * ä���� �ϴ� ä�ù� ��Ƽ��Ƽ
 * @author Administrator
 */
public class ChattingActivity extends Activity implements CallBackListener{
	
	private DBManager 		  m_DBManager; 	// DB�Ŵ���
	private ListView m_lv;
	private EditText m_InputMsg;
	private ArrayAdapter<String> m_Adapter;
	private ArrayList<String> data;
	private User_ItemDTO user;
	private ArrayList<String> chatting;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ui_chatting);
		
		System.out.println("@@@@@@@ chatting activity oncreate");
		
		m_DBManager = DBManager.get_DBManager(this);
		m_DBManager.openDB();
		
		m_lv = (ListView)findViewById(R.id.lv_BroadCastChatting);
		m_InputMsg = (EditText)findViewById(R.id.edt_inputMSG);

		chatting = new ArrayList<String>(1);
		data = new ArrayList<String>(1);
		m_Adapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, data);
		
		// ��ũ�� ���ϴ�
		m_lv.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
		m_lv.setAdapter(m_Adapter);
		
		
		// ����Ʈ�� ���� �Ҷ�
		Intent intent = getIntent();
		if(intent != null)
		{
			processIntent(intent);
		}
	}
	
	public void onClick(View v)
	{
		switch(v.getId())
		{
			case R.id.btn_Send :
				try 
				{
					String msg = m_InputMsg.getText().toString();
					m_InputMsg.setText("");
					m_Adapter.add(Const.NAME+" : "+msg);
					m_Adapter.notifyDataSetChanged();
					m_lv.smoothScrollByOffset(data.size());
					
					// ��ȭ �α�
					chatting.add(Const.NAME+" : "+msg);
					
					System.out.println("ä�� â�� �߰� ���� : "+msg);
					
					ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
					// �������� ���� 
					nameValuePairs.add(new BasicNameValuePair(ConstProtocol.PROTOCOL, ConstProtocol.NOTE));
					// �����»��
					nameValuePairs.add(new BasicNameValuePair(ConstParam.SENDER,Util.URLIncoding(Const.NAME)));
					// ������ ��� ��ȭ��ȣ
					nameValuePairs.add(new BasicNameValuePair(ConstParam.SENDERPHONENUM,Util.URLIncoding(Const.PHONE_NUM)));
					// �޴� ��� ��ȭ ��ȣ 
					nameValuePairs.add(new BasicNameValuePair(ConstParam.RECEIVER, URLEncoder.encode(user.getPhoneNumber(), "UTF-8")));
					
					// �޼���
					nameValuePairs.add(new BasicNameValuePair(ConstParam.MSG, URLEncoder.encode(msg, "UTF-8")));
					new HttpClient(this).sendMessageToServer(Const.SERVER_ADDRESS, nameValuePairs);
				} 
				catch (UnsupportedEncodingException e) {
					e.printStackTrace();
					System.out.println("���ڵ����� ������");
				}
				
			break;
		}
	}

	
	@Override
	protected void onNewIntent(Intent intent) {
			processIntent(intent);
		super.onNewIntent(intent);
	}
	
	
    private void processIntent(Intent intent) {
		
    	String protocol = intent.getStringExtra(ConstProtocol.PROTOCOL);
    	
    	if(protocol == null)
    	{
    		return;
    	}
    	else
    	{
    		if(protocol.equals(ConstProtocol.NOTE))
    		{
    			String msg = intent.getStringExtra(ConstParam.MSG);
                if (msg == null) {
                    return;
                }
                System.out.println("ä�� ������ ���� : "+msg);
                
                m_Adapter.add(user.getName()+" : "+msg);
                m_Adapter.notifyDataSetChanged();
                m_lv.smoothScrollByOffset(data.size());
                
                // ��ȭ �α�
                chatting.add(user.getName()+" : "+msg);
                System.out.println("�߰� �Ϸ�");
    		}
    		else if(protocol.equals(ConstProtocol.CHAT_SETTING))
    		{
    			Log.i(Const.APP, "CHAT_SETTING ����");
    			
    			User_ItemDTO my = m_DBManager.m_Member.select_MemberData();
    			
    			if(my != null)
    			{
    				Const.PHONE_NUM = my.getPhoneNumber();
    				Const.RegID = my.getRegID();
    				Const.NAME = my.getName();
    				
    			}
    			
    			
    			String name = intent.getStringExtra("Name");
    			String phone = intent.getStringExtra("PhoneNumber");
    			this.setTitle(name);
    			
    			
    			user = new User_ItemDTO(name, phone);
    			
    			
    			
    			//���� ��ȭ�� �ִ��� üũ �ִٸ�
    			if(m_DBManager.m_ChatDB.isData(user))
    			{
    				// �ҷ� ����
    				data = m_DBManager.m_ChatDB.getChatData(user);
    				for(int i = 0; i < data.size(); i ++)
    				{
    					m_Adapter.add(data.get(i));
    				}
    				
    				// ������ ������ �߰�
    				chatting.add(data.get(data.size()-1));
        			m_Adapter.notifyDataSetChanged();
    			}
    			
    			
    		}
    		
    		
    	}
    }// METHOD END

    
    
	@Override
	public void callBackMethod(String str) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void callBackMethod(int i) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	protected void onDestroy() {
		System.out.println("chatting on destory");
		chatWrite(); // ä�� ��� �����
		super.onDestroy();
	}
	
	// ä�� ��� �����
	private void chatWrite()
	{
		// 1. ���� ���� Ȯ��
		new FileManager().setRoot();
		
		// 2. ä�� ���
		CustomFileWriter chat = new CustomFileWriter(user.getName());
		chat.writeAll(chatting);
		chat.close();
		
		m_DBManager = DBManager.get_DBManager(this);
		m_DBManager.openDB();
		
		// 3. ä�� db�� ���� �ϴ��� Ȯ��
		if(!m_DBManager.m_ChatDB.isData(user))
		{
			// ���� ���� �ʴ� ��� ����� �ش�.
			m_DBManager.m_ChatDB.insert_ChatData(user,chatting.get(chatting.size()-1));
		}
		else
		{
			// db�� ���� ������Ʈ �ϱ�
			m_DBManager.m_ChatDB.upDateComment(chatting.get(chatting.size()-1), user.getName(), user.getPhoneNumber());
		}
	}
}
