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
 * 채팅을 하는 채팅방 액티비티
 * @author Administrator
 */
public class ChattingActivity extends Activity implements CallBackListener{
	
	private DBManager 		  m_DBManager; 	// DB매니저
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
		
		// 스크롤 최하단
		m_lv.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
		m_lv.setAdapter(m_Adapter);
		
		
		// 인텐트가 존재 할때
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
					
					// 대화 로그
					chatting.add(Const.NAME+" : "+msg);
					
					System.out.println("채팅 창의 추가 내용 : "+msg);
					
					ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
					// 프로토콜 설정 
					nameValuePairs.add(new BasicNameValuePair(ConstProtocol.PROTOCOL, ConstProtocol.NOTE));
					// 보내는사람
					nameValuePairs.add(new BasicNameValuePair(ConstParam.SENDER,Util.URLIncoding(Const.NAME)));
					// 보내는 사람 전화번호
					nameValuePairs.add(new BasicNameValuePair(ConstParam.SENDERPHONENUM,Util.URLIncoding(Const.PHONE_NUM)));
					// 받는 사람 전화 번호 
					nameValuePairs.add(new BasicNameValuePair(ConstParam.RECEIVER, URLEncoder.encode(user.getPhoneNumber(), "UTF-8")));
					
					// 메세지
					nameValuePairs.add(new BasicNameValuePair(ConstParam.MSG, URLEncoder.encode(msg, "UTF-8")));
					new HttpClient(this).sendMessageToServer(Const.SERVER_ADDRESS, nameValuePairs);
				} 
				catch (UnsupportedEncodingException e) {
					e.printStackTrace();
					System.out.println("인코딩에서 에러남");
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
                System.out.println("채팅 데이터 들어옴 : "+msg);
                
                m_Adapter.add(user.getName()+" : "+msg);
                m_Adapter.notifyDataSetChanged();
                m_lv.smoothScrollByOffset(data.size());
                
                // 대화 로그
                chatting.add(user.getName()+" : "+msg);
                System.out.println("추가 완료");
    		}
    		else if(protocol.equals(ConstProtocol.CHAT_SETTING))
    		{
    			Log.i(Const.APP, "CHAT_SETTING 들어옴");
    			
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
    			
    			
    			
    			//이전 대화가 있는지 체크 있다면
    			if(m_DBManager.m_ChatDB.isData(user))
    			{
    				// 불러 오기
    				data = m_DBManager.m_ChatDB.getChatData(user);
    				for(int i = 0; i < data.size(); i ++)
    				{
    					m_Adapter.add(data.get(i));
    				}
    				
    				// 마지막 데이터 추가
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
		chatWrite(); // 채팅 기록 남기기
		super.onDestroy();
	}
	
	// 채팅 기록 남기기
	private void chatWrite()
	{
		// 1. 폴더 존재 확인
		new FileManager().setRoot();
		
		// 2. 채팅 기록
		CustomFileWriter chat = new CustomFileWriter(user.getName());
		chat.writeAll(chatting);
		chat.close();
		
		m_DBManager = DBManager.get_DBManager(this);
		m_DBManager.openDB();
		
		// 3. 채팅 db에 존재 하는지 확인
		if(!m_DBManager.m_ChatDB.isData(user))
		{
			// 존재 하지 않는 경우 만들어 준다.
			m_DBManager.m_ChatDB.insert_ChatData(user,chatting.get(chatting.size()-1));
		}
		else
		{
			// db에 내용 업데이트 하기
			m_DBManager.m_ChatDB.upDateComment(chatting.get(chatting.size()-1), user.getName(), user.getPhoneNumber());
		}
	}
}
