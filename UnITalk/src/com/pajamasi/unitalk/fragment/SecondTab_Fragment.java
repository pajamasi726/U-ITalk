package com.pajamasi.unitalk.fragment;

import com.pajamasi.unitalk.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * ��ü ģ�� ����Ʈ�� ��Ÿ���� ������
 * @author pajamasi
 */
public class SecondTab_Fragment extends Fragment{

	/**
	 * Fragment Create ������ ȣ��
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View view;
		view = inflater.inflate(R.layout.second_tab, container, false);
		
		return view;
	}
	
}
