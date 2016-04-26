package com.pajamasi.unitalk.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.pajamasi.unitalk.R;
import com.pajamasi.unitalk.DB.DBManager;
import com.pajamasi.unitalk.Util.ConstProtocol;
import com.pajamasi.unitalk.activity.ChattingActivity;
import com.pajamasi.unitalk.itemDTO.User_ItemDTO;
import com.pajamasi.unitalk.itemHolder.AddFriend_ItemHolder;

public class FirstTab_Adapter extends BaseAdapter{
	
	private ArrayList<User_ItemDTO> list;
	private FirstTab_Adapter 		adapter;
	private DBManager 		  		m_DBManager; 	// DB�Ŵ���
	private Handler 				handle;
	private FragmentActivity 		m_Context;

	public FirstTab_Adapter(FragmentActivity fragmentActivity) {
		this.adapter 	= this;
		list	 		= new ArrayList<User_ItemDTO>(1);
		m_DBManager 	= DBManager.get_DBManager();
		m_Context		= fragmentActivity;
		
		// ������ ����
		list = m_DBManager.m_Friend.select_allUser();
	}
	
	public void setList(ArrayList<User_ItemDTO> list)
	{
		this.list = list;
	}
	
	public void refresh()
	{	
		if(handle == null)
		handle = new Handler(Looper.getMainLooper());

		handle.post(new Runnable() {
			
			@Override
			public void run() {
				adapter.notifyDataSetChanged();
			}
		});
	}

	public ArrayList<User_ItemDTO> getData() {
		return this.list;
	}

	public void addData(User_ItemDTO item) {
		list.add(item);
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return list.get(position);
	}

	@Override
	public long getItemId(int id) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// context ������
		final Context context = parent.getContext();
		
		// �׸� ��ü
		AddFriend_ItemHolder itemHolder;

		// ����Ʈ�� ������
		User_ItemDTO itemDTO;

		if (convertView == null) // view �� ���� �Ǿ� ���� ���� ���
		{
			// ���̾ƿ� �޾ƿ���
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			convertView = inflater.inflate(R.layout.lv_item_firsttab_friendlist, parent, false);

			// ���ҽ� ����
			itemHolder = new AddFriend_ItemHolder();
			itemHolder.m_TvName   = (TextView) convertView.findViewById(R.id.first_tab_item_name);
			itemHolder.m_TvNumber = (TextView) convertView.findViewById(R.id.first_tab_item_number);
			itemHolder.m_BtnChat  = (Button)   convertView.findViewById(R.id.first_tab_item_btn_chat);

			// �±� ���
			convertView.setTag(itemHolder);
		} 
		else // �����Ǿ� �ִ� �� �϶�
		{
			itemHolder = (AddFriend_ItemHolder) convertView.getTag();
		}

		itemDTO = list.get(position);

		itemHolder.m_TvName.setText(itemDTO.getName());
		itemHolder.m_TvNumber.setText(itemDTO.getPhoneNumber());

		// ��ư �̺�Ʈ
		itemHolder.m_BtnChat.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				
				User_ItemDTO data = list.get(position);
				Toast.makeText(context, list.get(position).getName(),Toast.LENGTH_SHORT).show();
				
				Intent intent = new Intent(m_Context, ChattingActivity.class);
				intent.putExtra(ConstProtocol.PROTOCOL, ConstProtocol.CHAT_SETTING);
				intent.putExtra("Name", data.getName());
				intent.putExtra("PhoneNumber", data.getPhoneNumber());
				
				m_Context.startActivity(intent);
			}
		});

		return convertView;
	}

}
