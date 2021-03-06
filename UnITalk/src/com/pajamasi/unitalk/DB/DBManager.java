package com.pajamasi.unitalk.DB;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class DBManager extends SQLiteOpenHelper{

	private static 	DBManager				m_DBManager;// 현재 클래스
	private 		SQLiteDatabase			m_SQLite;	// SQLite
	public 			MemberDB 				m_Member;	// GCM  
	public  		FriendDB				m_Friend;	// 친구 목록
	public          ChatDB					m_ChatDB;   // 채팅방 목록
	
	
	public static DBManager get_DBManager(Context context) // 현재 자신의 객체를 리턴 
	{
		if(m_DBManager != null)
			return m_DBManager;
		
		
		if(m_DBManager == null) // null 값일 경우 생성 하여서 리턴
		{
			m_DBManager = new DBManager(context, DBMark.DB_NAME, null, DBMark.DB_VERSION);
		}
		
		return m_DBManager;
	}
	
	
	public DBManager(Context context, String name, CursorFactory factory,int version) {
		super(context, name, factory, version);
		
		this.m_DBManager = this;
	}

	// DB 최초 1회 생성시에 호출
	@Override
	public void onCreate(SQLiteDatabase db) {
		
		// TABLE CREATE
		db.execSQL(DBMark.SQL_CREATE_MEMBER);
		
		db.execSQL(DBMark.SQL_CREATE_FRIEND);
		
		db.execSQL(DBMark.SQL_CREATE_CHAT);
	}

	// 버전이 바꼈을때 호출
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
	}
	
	public void openDB()
	{
		m_SQLite = getWritableDatabase();
		m_Member = new MemberDB(m_SQLite);
		m_Friend = new FriendDB(m_SQLite);
		m_ChatDB = new ChatDB(m_SQLite);
	}
	
	public void closeDB()
	{
		m_SQLite.close();
	}
	
}
