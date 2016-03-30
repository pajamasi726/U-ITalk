package com.pajamasi.unitalk.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DBManager extends SQLiteOpenHelper{

	private static 	DBManager				m_DBManager;
	private 		SQLiteDatabase			m_SQLite;
	public 			MemberDB 				m_Member;
	public  		FriendDB				m_Friend;
	
	
	public static DBManager get_DBManager()
	{
		if(m_DBManager != null)
			return m_DBManager;
		
		return null;
	}
	
	
	public DBManager(Context context, String name, CursorFactory factory,int version) {
		super(context, name, factory, version);
		
		this.m_DBManager = this;
	}

	// DB ���� 1ȸ �����ÿ� ȣ��
	@Override
	public void onCreate(SQLiteDatabase db) {
		
		// TABLE CREATE
		db.execSQL(DBMark.SQL_CREATE_MEMBER);
		
		db.execSQL(DBMark.SQL_CREATE_FRIEND);
	}

	// ������ �ٲ����� ȣ��
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
	}
	
	public void openDB()
	{
		m_SQLite = getWritableDatabase();
		m_Member = new MemberDB(m_SQLite);
		m_Friend = new FriendDB(m_SQLite);
	}
	
	public void closeDB()
	{
		m_SQLite.close();
	}
	
}
