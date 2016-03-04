package com.pajamasi.unitalk.DB;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

import com.pajamasi.unitalk.Util.Const;

public class MemberDB {

	SQLiteDatabase m_DBManager;
	
	public MemberDB(SQLiteDatabase manager)
	{
		m_DBManager = manager;
	}
	
	
	/** ȸ�� ������ �Ǿ� �ִ� ���� �Ǵ� */
	public boolean select_RegID()
	{
		boolean b = false;
		
		Cursor c = m_DBManager.rawQuery(DBMark.SQL_SELECT_REGID, null);
		
		if(c.moveToNext()) // �����Ͱ� �ִ� ��� 
		{
			b = true;
			String reg = c.getString(0); // Select ������ id�� �����ϹǷ� 0���� �÷��� ����
			System.out.println("ȸ�� ���� ������ ���� : "+reg);
			Const.RegID = reg;
		}
		else // �����Ͱ� ���� ���
		{
			b = false;
			System.out.println("ȸ�� ���� ������ ����");
		}
		
		c.close();
		return b;
	}
	
	public boolean insertRegID(String reg)
	{
		boolean b = false;
		SQLiteStatement statement = m_DBManager.compileStatement(DBMark.SQL_INSERT_REGID);
		statement.bindString(1, reg);
	
		long result = statement.executeInsert();
		System.out.println("DB �Է� ��� : "+result);
		
		if(result > 0)
		{
			b = true;
			Const.RegID = reg;
		}
		else
		{
			b = false;
		}
		statement.close();
		
		return b;
	}
}
