package com.pajamasi.unitalk.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.pajamasi.unitalk.R;
import com.pajamasi.unitalk.itemDTO.AddFriend_ItemDTO;
import com.pajamasi.unitalk.itemHolder.AddFriend_ItemHolder;

public class AddFriend_Adapter extends BaseAdapter{
	
	private ArrayList<AddFriend_ItemDTO> list;
	private AddFriend_Adapter adapter;

	public AddFriend_Adapter() {
		this.adapter 	= this;
		list	 		= new ArrayList<AddFriend_ItemDTO>(1);
	}

	public ArrayList<AddFriend_ItemDTO> getData() {
		return this.list;
	}

	public void addData(AddFriend_ItemDTO item) {
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
		AddFriend_ItemDTO itemDTO;

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

		itemHolder.m_TvName.setText(itemDTO.m_Name);
		itemHolder.m_TvNumber.setText(itemDTO.m_Number);

		// ��ư �̺�Ʈ
		itemHolder.m_BtnChat.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Toast.makeText(context, list.get(position).m_Name,Toast.LENGTH_SHORT).show();
				
				// ������ ����
				adapter.notifyDataSetChanged();
			}
		});

		return convertView;
	}

}
