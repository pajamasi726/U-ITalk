package com.pajamasi.unitalk.activity;

import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import com.pajamasi.unitalk.R;
import com.pajamasi.unitalk.CustomCallBackListener.CallBackListener;
import com.pajamasi.unitalk.DB.DBManager;
import com.pajamasi.unitalk.HttpClient.HttpClient;
import com.pajamasi.unitalk.Util.Const;
import com.pajamasi.unitalk.Util.ConstProtocol;
import com.pajamasi.unitalk.adapter.AddFriend_Adapter;
import com.pajamasi.unitalk.itemDTO.User_ItemDTO;
import com.pajamasi.unitalk.json.UNTalkJsonParser;

public class AddFriendActivity extends Activity implements CallBackListener{
	
	private DBManager 		  m_DBManager; 	// DB�Ŵ���
	private ListView  		  m_ListView;  	// ����Ʈ��
	private AddFriend_Adapter m_Adapter; 	// ����Ʈ�� ���
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ui_addfriend);
		
		init(); 		// ���ҽ� �ε�
		
		setAdapter(); 	// ��� ���� 
	}
	
	public void onClick(View v)
	{
		switch(v.getId())
		{
			case R.id.refresh_friend :
				System.out.println("ģ�� ��� ���� Ŭ��");
				refresh_Friend();
			break;
		}
	}
	
	/** ģ�� ��� ���� */
	private void refresh_Friend()
	{
		get_Friend_List();
	}
	
	/** ������ ģ�� ��� ��û */
	private void get_Friend_List()
	{
		ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		// �������� ���� 
		nameValuePairs.add(new BasicNameValuePair(ConstProtocol.PROTOCOL, ConstProtocol.REQUEST_FRIEND_LIST));
		
		new HttpClient(this).sendMessageToServer(Const.SERVER_ADDRESS, nameValuePairs);
	}

	/** ���ҽ� �ε� */
	private void init() 
	{
		m_ListView = (ListView)findViewById(R.id.lv_addfriendList);
		
	}
	
	private void setAdapter()
	{
		m_Adapter = new AddFriend_Adapter(this);
		m_ListView.setAdapter(m_Adapter);
	}
	
	private void addData(User_ItemDTO data)
	{
		m_Adapter.addData(data);
		// ������ ���� �˸���
		m_Adapter.notifyDataSetChanged();
	}
	
	
	private ArrayList<User_ItemDTO> client_DataInit()
	{
		m_DBManager = DBManager.get_DBManager(this);
		System.out.println("DBManager Check : "+m_DBManager);
		ArrayList<User_ItemDTO> friendList = m_DBManager.m_Friend.select_allUser();
		System.out.println("���� �߰��Ǿ� �ִ� ģ�� ����Ʈ : "+friendList.size());
		return friendList;
	}
	
	private ArrayList<User_ItemDTO> server_DataInit(String json_Data)
	{
		ArrayList<User_ItemDTO> list = 
				(ArrayList<User_ItemDTO>)new UNTalkJsonParser().ParserController(json_Data);
		
		for(int i = 0 ; i < list.size(); i ++)
		{
			User_ItemDTO imsi = list.get(i);
		}
		
		return list;
	}
	
	/**
	 * @param server
	 * @param client
	 */
	private void setFriendListView(ArrayList<User_ItemDTO> server, ArrayList<User_ItemDTO> client)
	{
		for(int i = 0 ; i < client.size(); i++)
		{
			User_ItemDTO c = client.get(i);
			
			for(int j = 0 ; j < server.size(); j++)
			{
				User_ItemDTO s = server.get(j);
				
				// ������ Ŭ���̾�Ʈ �� 
				if(c.equals(s))
				{
					// ���� �����Ͱ� ������ �����.
					server.remove(j);
				}
			}
		}
		
		System.out.println("��������Ʈ�� ���� : "+server.size());
		System.out.println("Ŭ���̾�Ʈ�� ���� : "+client.size());
		
		// ��������� server_list���� ���� �����͸� ���Եȴ�.
		m_Adapter.setList(server);
		m_Adapter.refresh();
		
	}

	@Override
	public void callBackMethod(String str) {
		System.out.println("ģ�� ����Ʈ JSON : "+str);
		// ���� ģ�� ��� ����
		ArrayList<User_ItemDTO> server_list = server_DataInit(str);
		
		// Ŭ���̾�Ʈ ������ ��� ����
		ArrayList<User_ItemDTO> client_list = client_DataInit();
		
		// �ΰ��� ���ؼ� ����Ʈ�信 ����
		setFriendListView(server_list,client_list);
	}


	@Override
	public void callBackMethod(int i) {
		
	}

}
