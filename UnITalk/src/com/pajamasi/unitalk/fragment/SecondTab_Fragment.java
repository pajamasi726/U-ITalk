package com.pajamasi.unitalk.fragment;

import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.ComponentName;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import com.pajamasi.unitalk.R;
import com.pajamasi.unitalk.adapter.FirstTab_Adapter;
import com.pajamasi.unitalk.adapter.SecondTab_Adapter;

/**
 * ��ü ä�ù� ����Ʈ�� ��Ÿ���� ������
 * @author pajamasi
 */
public class SecondTab_Fragment extends Fragment{

	
	private View view;
	private ListView m_lvFriend;
	private SecondTab_Adapter m_Adapter;
	
	/**
	 * Fragment Create ������ ȣ��
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		view = inflater.inflate(R.layout.second_tab, container, false);
		
		init();
		return view;
	}
	
	private void init()
	{
		m_Adapter = new SecondTab_Adapter(getActivity()); // ��Ƽ��Ƽ �̵��� ���� �Ѱ� �ش� 
		m_lvFriend = (ListView) view.findViewById(R.id.lv_chatting); // ����Ʈ ��
		m_lvFriend.setAdapter(m_Adapter);
	}
	
	
	@Override
	public void onResume() {
		m_Adapter.refresh();
		super.onResume();
	}
	
}
