package com.pajamasi.unitalk.activity;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.pajamasi.unitalk.R;
import com.pajamasi.unitalk.CustomCallBackListener.CallBackListener;
import com.pajamasi.unitalk.DB.DBManager;

public class AddFriendActivity extends Activity implements CallBackListener{
	
	private DBManager m_DBManager; // DB�Ŵ���
	private ListView  m_ListView;  // ����Ʈ��
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ui_chatting);
		
		init();
		data_init();
	}

	
	private void init()
	{
		m_ListView = (ListView)findViewById(R.id.lv_addfriendList);
		
	}
	
	private void data_init()
	{
		m_DBManager = DBManager.get_DBManager();
		
		System.out.println("DBManager Check : "+m_DBManager);
		ArrayList<String> friendList = m_DBManager.m_Friend.select_PhoneNumber();
		
		System.out.println("���� �߰��Ǿ� �ִ� ģ�� ����Ʈ : "+friendList.size());
	}

	@Override
	public void callBackMethod(String str) {
		
	}


	@Override
	public void callBackMethod(int i) {
		
	}

}
