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
	
	
	/** 회원 가입이 되어 있는 여부 판단 */
	public User_ItemDTO select_MemberData()
	{
		User_ItemDTO user = null;
		
		Cursor c = m_DBManager.rawQuery(DBMark.SQL_SELECT_REGID, null);
		
		if(c.moveToNext()) // 데이터가 있는 경우 
		{
			String reg = c.getString(0); // Select 문에서 id만 선택하므로 0번재 컬럼을 선택
			String name = c.getString(1);
			
			user = new User_ItemDTO(name, Const.PHONE_NUM, reg);
			
			System.out.println("회원 가입 데이터 존재 : "+name+" > "+Const.PHONE_NUM);
			
		}
		else // 데이터가 없는 경우
		{
			System.out.println("회원 가입 데이터 없음");
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
		System.out.println("DB 입력 결과 : "+result);
		
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
