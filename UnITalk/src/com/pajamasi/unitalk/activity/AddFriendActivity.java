package com.pajamasi.unitalk.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.pajamasi.unitalk.R;
import com.pajamasi.unitalk.DB.DBManager;

public class AddFriendActivity extends Activity{
	
	private DBManager m_DBManager; // DB�Ŵ���
	private ListView  m_ListView;  // ����Ʈ��
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ui_chatting);
	}

}
