package com.pajamasi.unitalk.DB;

import java.util.ArrayList;

import com.pajamasi.unitalk.Util.Const;

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
	
	
	public ArrayList<String> select_PhoneNumber()
	{
		ArrayList<String> list = new ArrayList<String>(1);
		
		Cursor c = m_DBManager.rawQuery(DBMark.SQL_SELECT_ALLFRIEND, null);
		
		while(c.moveToNext())
		{
			String friendPhoneNum = c.getString(0); // Select ������ ��ȣ�� �����ϹǷ� 0���� �÷��� ����
			list.add(friendPhoneNum);
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
	
	public boolean insert_PhoneNumber(String num)
	{
		boolean b = false;
		
		SQLiteStatement statement = m_DBManager.compileStatement(DBMark.SQL_INSERT_FRIEND);
		statement.bindString(1, num);
	
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
