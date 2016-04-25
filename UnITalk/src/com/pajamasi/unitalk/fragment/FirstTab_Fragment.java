package com.pajamasi.unitalk.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.pajamasi.unitalk.R;
import com.pajamasi.unitalk.adapter.AddFriend_Adapter;

/**
 * ��ü ģ�� ����Ʈ�� ��Ÿ���� ������
 * @author pajamasi
 */
public class FirstTab_Fragment extends Fragment{

	private View view;
	
	private ListView m_lvFriend;
	
	
	
	/**
	 * Fragment Create ������ ȣ��
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		view = inflater.inflate(R.layout.first_tab, container, false);
		
		init();
		
		return view;
	}
	
	private void init()
	{
		m_lvFriend = (ListView) view.findViewById(R.id.lv_friend); // ����Ʈ ��
	}
	
	
	
	private void addData()
	{
		
	}
	
}
