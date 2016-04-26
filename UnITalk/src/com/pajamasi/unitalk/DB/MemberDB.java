package com.pajamasi.unitalk.DB;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;


import com.pajamasi.unitalk.Util.Const;
import com.pajamasi.unitalk.itemDTO.*;

public class MemberDB {

	SQLiteDatabase m_DBManager;
	
	public MemberDB(SQLiteDatabase manager)
	{
		m_DBManager = manager;
	}
	
	
	/** ȸ�� ������ �Ǿ� �ִ� ���� �Ǵ� */
	public User_ItemDTO select_MemberData()
	{
		User_ItemDTO user = null;
		
		Cursor c = m_DBManager.rawQuery(DBMark.SQL_SELECT_REGID, null);
		
		if(c.moveToNext()) // �����Ͱ� �ִ� ��� 
		{
			String reg = c.getString(0); // Select ������ id�� �����ϹǷ� 0���� �÷��� ����
			String name = c.getString(1);
			
			user = new User_ItemDTO(name, Const.PHONE_NUM, reg);
			
			System.out.println("ȸ�� ���� ������ ���� : "+name+" > "+Const.PHONE_NUM);
			
		}
		else // �����Ͱ� ���� ���
		{
			System.out.println("ȸ�� ���� ������ ����");
		}
		
		c.close();
		return user;
	}
	
	public boolean insertMember(String name, String redID)
	{
		boolean b = false;
		SQLiteStatement statement = m_DBManager.compileStatement(DBMark.SQL_INSERT_REGID);
		statement.bindString(1, redID);
		statement.bindString(2, name);
	
		long result = statement.executeInsert();
		System.out.println("DB �Է� ��� : "+result);
		
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
