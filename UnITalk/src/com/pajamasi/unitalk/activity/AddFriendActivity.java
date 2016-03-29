package com.pajamasi.unitalk.activity;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.pajamasi.unitalk.R;
import com.pajamasi.unitalk.DB.DBManager;

public class AddFriendActivity extends Activity{
	
	private DBManager m_DBManager; // DB매니저
	private ListView  m_ListView;  // 리스트뷰
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ui_chatting);
	}

}
