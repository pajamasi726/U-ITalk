package com.pajamasi.unitalk.DB;

import java.util.ArrayList;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;




import android.util.Log;

import com.pajamasi.unitalk.Util.Const;
import com.pajamasi.unitalk.file.CustomFileReader;
import com.pajamasi.unitalk.itemDTO.*;

public class ChatDB {

	SQLiteDatabase m_DBManager;
	
	public ChatDB(SQLiteDatabase manager)
	{
		m_DBManager = manager;
	}
	
	
	public ArrayList<String> getChatData(User_ItemDTO data)
	{
		ArrayList<String> list = new ArrayList<String>(1);
		String sql = "SELECT * from "+DBMark.TABLE_NAME_CHAT
				+ " WHERE "+DBMark.COLUMN_NAME_FPATH+" = '"+data.getName()+"'"
				+ " AND "+DBMark.COLUMN_NAME_FPHONE+" = '"+data.getPhoneNumber()+"';";
		
		Cursor c = m_DBManager.rawQuery(sql, null);
		
		if(c.getCount() > 0) // 데이터 존재
		{
			while(c.moveToNext())
			{
				String chat_path 	= c.getString(1);
				
				CustomFileReader fr = new CustomFileReader(chat_path);
				
				while(true)
				{
					String str = fr.read();
					
					if(str == null || str.equals("") || str.equals("null"))
						break;
					
					list.add(str);
				}
			}
		}
				
		Log.i(Const.APP, "채팅 데이터 로드 완료 : "+list.size());
		return list;
	}
	
	
	public boolean isData(User_ItemDTO data) // 해당 유저의 DB정보가 있는지 체크
	{
		boolean b = false;
		
		String sql = "SELECT * from "+DBMark.TABLE_NAME_CHAT
				+ " WHERE "+DBMark.COLUMN_NAME_FPATH+" = '"+data.getName()+"'"
				+ " AND "+DBMark.COLUMN_NAME_FPHONE+" = '"+data.getPhoneNumber()+"';";
		
		Cursor c = m_DBManager.rawQuery(sql, null);
		
		if(c.getCount() > 0) // 데이터 존재
		{
			b = true;
			Log.i(Const.APP, "이미 채팅방DB가 존재 합니다");
		}
		else
		{
			Log.i(Const.APP, "채팅방 DB가 존재 하지 않습니다.");
		}
		
		return b;
	}
	
	public ArrayList<Chat_ItemDTO> select_allChat()
	{
		ArrayList<Chat_ItemDTO> list = new ArrayList<Chat_ItemDTO>(1);
		
		Cursor c = m_DBManager.rawQuery(DBMark.SQL_SELECT_ALLCHAT, null);
		
		Log.i(Const.APP,"채팅 방 데이터 갯수 : "+c.getCount());
		
		if(c.getCount() == 0)
		{
			Log.i(Const.APP,"채팅 방 정보 데이터 없음");
		}
		else
		{
			Log.i(Const.APP, "채팅 방 정도 데이터 존재");
			while(c.moveToNext())
			{
				String chat_path 	= c.getString(1); 
				String chat_phone	= c.getString(2); 
				String chat_comment = c.getString(3);
				int    chat_count   = c.getInt(4);
				Chat_ItemDTO data = new Chat_ItemDTO(chat_path, chat_phone, chat_comment, chat_count);
				list.add(data);
			}
		}
		
		c.close();
		
		return list;
	}
	
	public boolean insert_ChatData(User_ItemDTO data, String comment)
	{
		boolean b = false;
		
		SQLiteStatement statement = m_DBManager.compileStatement(DBMark.SQL_INSERT_CHAT);
		statement.bindString(1, data.getName());
		statement.bindString(2, data.getPhoneNumber());
		statement.bindString(3, comment);
		statement.bindDouble(4, 0);
		
	
		long result = statement.executeInsert();
		Log.i(Const.APP, "채팅 내용 DB초기 입력 결과 : "+result);
		
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
	
	public void upDateCount()
	{
		
	}
	
	public void upDateComment(String comment, String name, String phone)
	{
		SQLiteStatement statement = m_DBManager.compileStatement(DBMark.SQL_UPDATE_COMMENT);
		statement.bindString(1, comment);
		statement.bindString(2, name);
		statement.bindString(3, phone);
		
		long result = statement.executeInsert();
		Log.i(Const.APP, "채팅 내용 DB초 코멘트 수정 결과 : "+result);
	}
	
	
}
