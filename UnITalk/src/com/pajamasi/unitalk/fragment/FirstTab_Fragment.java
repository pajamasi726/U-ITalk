package com.pajamasi.unitalk.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.pajamasi.unitalk.R;
import com.pajamasi.unitalk.adapter.AddFriend_Adapter;
import com.pajamasi.unitalk.adapter.FirstTab_Adapter;

/**
 * 전체 친구 리스트를 나타내는 페이지
 * @author pajamasi
 */
public class FirstTab_Fragment extends Fragment{

	private View view;
	private ListView m_lvFriend;
	private FirstTab_Adapter m_Adapter;
	
	
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
		m_Adapter = new FirstTab_Adapter();
		
		m_lvFriend = (ListView) view.findViewById(R.id.lv_friend); // 리스트 뷰
		
		m_lvFriend.setAdapter(m_Adapter);
	}
	
	
	@Override
	public void onResume() {
		m_Adapter.refresh();
		super.onResume();
	}
	
}
