package com.pajamasi.unitalk.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DBManager extends SQLiteOpenHelper{

	private SQLiteDatabase	m_DBManager;
	public 	MemberDB 		m_Member;
	
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
	
	public void openDB()
	{
		m_DBManager = getWritableDatabase();
		m_Member = new MemberDB(m_DBManager);
	}
	
	public void closeDB()
	{
		m_DBManager.close();
	}
	
}
