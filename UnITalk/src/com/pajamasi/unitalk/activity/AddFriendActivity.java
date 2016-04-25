package com.pajamasi.unitalk.activity;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.pajamasi.unitalk.R;
import com.pajamasi.unitalk.CustomCallBackListener.CallBackListener;
import com.pajamasi.unitalk.DB.DBManager;
import com.pajamasi.unitalk.HttpClient.HttpClient;
import com.pajamasi.unitalk.Util.Const;
import com.pajamasi.unitalk.Util.ConstProtocol;
import com.pajamasi.unitalk.adapter.AddFriend_Adapter;
import com.pajamasi.unitalk.itemDTO.AddFriend_ItemDTO;
import com.pajamasi.unitalk.itemDTO.User_ItemDTO;
import com.pajamasi.unitalk.json.UNTalkJsonParser;

public class AddFriendActivity extends Activity implements CallBackListener{
	
	private DBManager 		  m_DBManager; 	// DB매니저
	private ListView  		  m_ListView;  	// 리스트뷰
	private AddFriend_Adapter m_Adapter; 	// 리스트뷰 어뎁터
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ui_addfriend);
		
		init(); 		// 리소스 로딩
		
		setAdapter(); 	// 어뎁터 셋팅 
		
		
		data_init();
	}
	
	public void onClick(View v)
	{
		switch(v.getId())
		{
			case R.id.refresh_friend :
				System.out.println("친구 목록 갱신 클릭");
				refresh_Friend();
			break;
		}
	}
	
	/** 친구 목록 갱신 */
	private void refresh_Friend()
	{
		get_Friend_List();
	}
	
	/** 서버에 친구 목록 요청 */
	private void get_Friend_List()
	{
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		// 프로토콜 설정 
		nameValuePairs.add(new BasicNameValuePair(ConstProtocol.PROTOCOL, ConstProtocol.REQUEST_FRIEND_LIST));
		
		new HttpClient(this).sendMessageToServer(Const.SERVER_ADDRESS, nameValuePairs);
	}

	/** 리소스 로딩 */
	private void init() 
	{
		m_ListView = (ListView)findViewById(R.id.lv_addfriendList);
		
	}
	
	private void setAdapter()
	{
		m_Adapter = new AddFriend_Adapter();
		m_ListView.setAdapter(m_Adapter);
	}
	
	private void addData()
	{
		AddFriend_ItemDTO imsi = new AddFriend_ItemDTO();
		m_Adapter.addData(imsi);
		
		// 데이터 갱신 알리기
		m_Adapter.notifyDataSetChanged();
	}
	
	
	private void data_init()
	{
		m_DBManager = DBManager.get_DBManager();
		
		System.out.println("DBManager Check : "+m_DBManager);
		ArrayList<String> friendList = m_DBManager.m_Friend.select_PhoneNumber();
		
		System.out.println("현재 추가되어 있는 친구 리스트 : "+friendList.size());
	}

	@Override
	public void callBackMethod(String str) {
		
		System.out.println("친구 리스트 JSON : "+str);
		
		String jsonData = str;
		
		ArrayList<User_ItemDTO> list = (ArrayList<User_ItemDTO>)new UNTalkJsonParser().ParserController(jsonData);
		
		for(int i = 0 ; i < list.size(); i ++)
		{
			User_ItemDTO imsi = list.get(i);
			System.out.println(imsi);
		}
		
	}


	@Override
	public void callBackMethod(int i) {
		
	}

}
