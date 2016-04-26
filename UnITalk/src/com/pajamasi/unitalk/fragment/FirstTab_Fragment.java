package com.pajamasi.unitalk.fragment;

import java.util.List;

import com.pajamasi.unitalk.R;
import com.pajamasi.unitalk.adapter.FirstTab_Adapter;

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

/**
 * ��ü ģ�� ����Ʈ�� ��Ÿ���� ������
 * @author pajamasi
 */
public class FirstTab_Fragment extends Fragment{

	private View view;
	private ListView m_lvFriend;
	private FirstTab_Adapter m_Adapter;
	
	
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
		m_Adapter = new FirstTab_Adapter(getActivity()); // ��Ƽ��Ƽ �̵��� ���� �Ѱ� �ش� 
		
		m_lvFriend = (ListView) view.findViewById(R.id.lv_friend); // ����Ʈ ��
		
		m_lvFriend.setAdapter(m_Adapter);
	}
	
	
	@Override
	public void onResume() {
		
		ActivityManager am = (ActivityManager)getActivity().getSystemService(Context.ACTIVITY_SERVICE);
		List<RunningTaskInfo> Info = am.getRunningTasks(1);
		ComponentName topActivity = Info.get(0).topActivity;
		
		
		String msg = topActivity.getClassName();
		
		Toast.makeText(getContext(), msg, Toast.LENGTH_LONG);
		System.out.println("���� ��Ƽ��Ƽ : "+msg);
		
		m_Adapter.refresh();
		super.onResume();
	}
	
}
