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
		
		m_DBManager = DBManager.get_DBManager(this);
		
		m_lv = (ListView)findViewById(R.id.lv_BroadCastChatting);
		m_InputMsg = (EditText)findViewById(R.id.edt_inputMSG);

		chatting = new ArrayList<String>(1);
		data = new ArrayList<String>(1);
		m_Adapter = new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1, data);
		
		// ��ũ�� ���ϴ�
		m_lv.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
		m_lv.setAdapter(m_Adapter);
		m_lv.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				return true;
			}
		});
		
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
					data.add(Const.NAME+" : "+msg);
					m_Adapter.notifyDataSetChanged();
					m_lv.smoothScrollByOffset(data.size());
					
					// ��ȭ �α�
					chatting.add(Const.NAME+" : "+msg);
					
					ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
					// �������� ���� 
					nameValuePairs.add(new BasicNameValuePair(ConstProtocol.PROTOCOL, ConstProtocol.NOTE));
					nameValuePairs.add(new BasicNameValuePair(ConstParam.RECEIVER, URLEncoder.encode(user.getPhoneNumber(), "UTF-8")));
					nameValuePairs.add(new BasicNameValuePair(ConstParam.MSG, URLEncoder.encode(msg, "UTF-8")));
					new HttpClient(this).sendMessageToServer(Const.SERVER_ADDRESS, nameValuePairs);
				} 
				catch (UnsupportedEncodingException e) {
					e.printStackTrace();
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
                
                data.add(user.getName()+" : "+msg);
                m_Adapter.notifyDataSetChanged();
                m_lv.smoothScrollByOffset(data.size());
                
                // ��ȭ �α�
                chatting.add(user.getName()+" : "+msg);
    		}
    		else if(protocol.equals(ConstProtocol.CHAT_SETTING))
    		{
    			String name = intent.getStringExtra("Name");
    			String phone = intent.getStringExtra("PhoneNumber");
    			
    			user = new User_ItemDTO(name, phone);
    			
    			this.setTitle(user.getName());
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
		
		// 3. ä�� db�� ���� �ϴ��� Ȯ��
		if(!m_DBManager.m_ChatDB.isData(user))
		{
			// ���� ���� �ʴ� ��� ����� �ش�.
			m_DBManager.m_ChatDB.insert_ChatData(user,chatting.get(chatting.size()-1));
		}
	}
}
