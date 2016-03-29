package com.pajamasi.unitalk.fragment;

import com.pajamasi.unitalk.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 전체 친구 리스트를 나타내는 페이지
 * @author pajamasi
 */
public class FirstTab_Fragment extends Fragment{

	/**
	 * Fragment Create 생성시 호출
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View view;
		view = inflater.inflate(R.layout.first_tab, container, false);
		
		return view;
	}
	
}
