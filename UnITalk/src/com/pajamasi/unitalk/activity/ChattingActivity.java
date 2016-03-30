package com.pajamasi.unitalk.activity;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.pajamasi.unitalk.R;
import com.pajamasi.unitalk.CustomCallBackListener.CallBackListener;
import com.pajamasi.unitalk.HttpClient.HttpClient;
import com.pajamasi.unitalk.Util.Const;
import com.pajamasi.unitalk.Util.ConstParam;
import com.pajamasi.unitalk.Util.ConstProtocol;

/**
 * 채팅을 하는 채팅방 액티비티
 * @author Administrator
 */
public class ChattingActivity extends Activity implements CallBackListener{
	
	ListView m_lv;
	EditText m_InputMsg;
	ArrayAdapter<String> m_Adapter;
	ArrayList<String> data;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ui_chatting);
		
		m_lv = (ListView)findViewById(R.id.lv_BroadCastChatting);
		m_InputMsg = (EditText)findViewById(R.id.edt_inputMSG);
		
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
					ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
					// 프로토콜 설정 
					nameValuePairs.add(new BasicNameValuePair(ConstProtocol.PROTOCOL, ConstProtocol.BROADCAST));
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
		
		String msg = intent.getStringExtra(ConstParam.MSG);
        if (msg == null) {
            return;
        }
        System.out.println("채팅 데이터 들어옴 : "+msg);
        
        data.add(msg);
        m_Adapter.notifyDataSetChanged();
    }

    
    
	@Override
	public void callBackMethod(String str) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void callBackMethod(int i) {
		// TODO Auto-generated method stub
		
	}
}
