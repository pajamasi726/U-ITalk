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
 * ��ü ģ�� ����Ʈ�� ��Ÿ���� ������
 * @author pajamasi
 */
public class ThirdTab_Fragment extends Fragment{

	private TextView m_TvID;		// ������Ʈ ID
	private TextView m_TvPhone;		// �ڵ��� ��ȣ
	private TextView m_TvRegID;		// GCM Register ID
	
	private Button m_sendbtn;				// ä�� ��Ƽ��Ƽ�� �̵� (�ӽ�)
	private Button m_AddFriend;				// ģ�� �߰� ��Ƽ��Ƽ �̵�
	
	
	private View view;				// �����׸�Ʈ �ش� ��
	
	/**
	 * Fragment Create ������ ȣ��
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
