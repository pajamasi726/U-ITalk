package com.pajamasi.unitalk.DB;

import com.pajamasi.unitalk.Util.Const;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DBManager extends SQLiteOpenHelper{

	SQLiteDatabase m_DBManager;
	
	public DBManager(Context context, String name, CursorFactory factory,int version) {
		super(context, name, factory, version);
	}

	// DB 최초 1회 생성시에 호출
	@Override
	public void onCreate(SQLiteDatabase db) {
		
		// TABLE CREATE
		db.execSQL(DBMark.SQL_CREATE_MEMBER);
	}

	// 버전이 바꼈을때 호출
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
	}
	
	/** 회원 가입이 되어 있는 여부 판단 */
	public boolean select_RegID()
	{
		boolean b = false;
		
		Cursor c = m_DBManager.rawQuery(DBMark.SQL_SELECT_REGID, null);
		
		if(c.moveToNext()) // 데이터가 있는 경우 
		{
			b = true;
			String reg = c.getString(0); // Select 문에서 id만 선택하므로 0번재 컬럼을 선택
			System.out.println("회원 가입 데이터 존재 : "+reg);
			Const.RegID = reg;
		}
		else // 데이터가 없는 경우
		{
			b = false;
			System.out.println("회원 가입 데이터 없음");
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
		System.out.println("DB 입력 결과 : "+result);
		
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
	
	
	public void openDB()
	{
		m_DBManager = getWritableDatabase();
	}
	
	public void closeDB()
	{
		m_DBManager.close();
	}
	
}
