package com.pajamasi.unitalk.DB;

import java.util.ArrayList;

import com.pajamasi.unitalk.Util.Const;
import com.pajamasi.unitalk.itemDTO.User_ItemDTO;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

/**
 * ģ�� ������ ���� �ϴ� DBŬ����
 * @author Administrator
 */
public class FriendDB {

	SQLiteDatabase m_DBManager;

	public FriendDB(SQLiteDatabase manager) 
	{
		m_DBManager = manager;
	}
	
	
	public ArrayList<User_ItemDTO> select_allUser()
	{
		ArrayList<User_ItemDTO> list = new ArrayList<User_ItemDTO>(1);
		
		Cursor c = m_DBManager.rawQuery(DBMark.SQL_SELECT_ALLFRIEND, null);
		
		while(c.moveToNext())
		{
			String friendPhoneNum = c.getString(0); // Select ������ ��ȣ�� �����ϹǷ� 0���� �÷��� ����
			String friendName	  = c.getString(1); // �̸� ���� ����
			
			User_ItemDTO data = new User_ItemDTO(friendName, friendPhoneNum);
			list.add(data);
		}
		
		if(list.size() > 0)
		{
			System.out.println("ģ�� ��ȭ��ȣ ������ ����");
		}
		else
		{
			System.out.println("ģ�� ��ȭ��ȣ ������ ����");
		}
		
		c.close();
		
		return list;
	}
	
	public boolean insert_Friend(User_ItemDTO data)
	{
		boolean b = false;
		
		SQLiteStatement statement = m_DBManager.compileStatement(DBMark.SQL_INSERT_FRIEND);
		statement.bindString(1, data.getPhoneNumber());
		statement.bindString(2, data.getName());
	
		long result = statement.executeInsert();
		System.out.println("ģ�� �߰� DB �Է� ��� : "+result);
		
		if(result > 0)
		{
			b = true;
		}
		else
		{
			b = false;
		}
		
		statement.close();
		
		return b;
	}
}
