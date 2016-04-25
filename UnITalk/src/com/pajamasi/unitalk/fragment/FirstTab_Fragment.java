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
 * 전체 친구 리스트를 나타내는 페이지
 * @author pajamasi
 */
public class FirstTab_Fragment extends Fragment{

	private View view;
	
	private ListView m_lvFriend;
	
	
	
	/**
	 * Fragment Create 생성시 호출
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
		m_lvFriend = (ListView) view.findViewById(R.id.lv_friend); // 리스트 뷰
	}
	
	
	
	private void addData()
	{
		
	}
	
}
