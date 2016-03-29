package com.pajamasi.unitalk.DB;

import java.util.ArrayList;

import com.pajamasi.unitalk.Util.Const;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

/**
 * 친구 정보를 관리 하는 DB클래스
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
			String friendPhoneNum = c.getString(0); // Select 문에서 번호만 선택하므로 0번재 컬럼을 선택
			list.add(friendPhoneNum);
		}
		
		if(list.size() > 0)
		{
			System.out.println("친구 전화번호 데이터 있음");
		}
		else
		{
			System.out.println("친구 전화번호 데이터 없음");
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
		System.out.println("친구 추가 DB 입력 결과 : "+result);
		
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
