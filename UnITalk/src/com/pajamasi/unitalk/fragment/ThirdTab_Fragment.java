package com.pajamasi.unitalk.fragment;

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
import com.pajamasi.unitalk.activity.AddFriendActivity;
import com.pajamasi.unitalk.activity.ChattingActivity;

/**
 * 전체 친구 리스트를 나타내는 페이지
 * @author pajamasi
 */
public class ThirdTab_Fragment extends Fragment{

	private TextView m_TvID;		// 프로젝트 ID
	private TextView m_TvPhone;		// 핸드폰 번호
	private TextView m_TvRegID;		// GCM Register ID
	
	private Button m_sendbtn;				// 채팅 액티비티로 이동 (임시)
	private Button m_AddFriend;				// 친구 추가 액티비티 이동
	
	
	private View view;				// 프래그먼트 해당 뷰
	
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
		
		
		m_AddFriend.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getActivity(),AddFriendActivity.class);
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
		
	}
	
	private void init()
	{
		m_TvID      = 	(TextView)view.findViewById(R.id.tv_ID);
		m_TvPhone   =	(TextView)view.findViewById(R.id.tv_PhoneNum);
		m_TvRegID   = 	(TextView)view.findViewById(R.id.tv_RegID);
		m_sendbtn   = 	(Button)view.findViewById(R.id.imsi);
		m_AddFriend = 	(Button)view.findViewById(R.id.addFriend);
		
	}
	
}
