package com.pajamasi.unitalk.thirdTab.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.pajamasi.unitalk.R;
import com.pajamasi.unitalk.Util.Const;
import com.pajamasi.unitalk.activity.ChattingActivity;

/**
 * 전체 친구 리스트를 나타내는 페이지
 * @author pajamasi
 */
public class ThirdTab_Fragment extends Fragment{

	TextView m_TvID;
	TextView m_TvPhone;
	TextView m_TvRegID;
	Button m_sendbtn;
	
	private View view;
	
	/**
	 * Fragment Create 생성시 호출
	 */
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		view = inflater.inflate(R.layout.third_tab, container, false);
		
		
		
		init();
		setData();
		
		
		m_sendbtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(),ChattingActivity.class);
				startActivity(intent);
				
			}
		});
		
		return view;
	}
	
	
	private void setData()
	{
		m_TvID.setText("ID : "+Const.PROJECT_ID);
		m_TvPhone.setText("PHONE : "+Const.PHONE_NUM);
		m_TvRegID.setText("REGID : "+Const.RegID);
		
		System.out.println(Const.RegID);
	}
	
	private void init()
	{
		m_TvID = 	(TextView)view.findViewById(R.id.tv_ID);
		m_TvPhone = (TextView)view.findViewById(R.id.tv_PhoneNum);
		m_TvRegID = (TextView)view.findViewById(R.id.tv_RegID);
		m_sendbtn  = (Button)view.findViewById(R.id.imsi);
	}
	
}
