package com.pajamasi.unitalk.DB;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteStatement;

/**
 * DB SQL, VERSION, NAME, TABLE 등 DB정보 정의 클래스
 * @author PAJAMASI
 *
 */
public class DBMark {
	
	/** DB Version*/
	public static final int DB_VERSION = 1;
	
	/** DB 이름 */
	public static final String DB_NAME = "UNITALK.db";
	
	
	// 멤버
	/** 회원정보 정보 테이블 이름 */
	public static final String TABLE_NAME_MEMBER 			= "T_MEMBER";
	
	/** 회원정보 정보 컬럼 */
	public static final String COLUMN_NAME_REGID 			= "C_REGID";
	
	/** 회원 아이디 */
	public static final String COLUMN_NAME_NICKNAME    = "C_NAME";
	
	/** 회원정보 테이블 생성 SQL */
	public static final String SQL_CREATE_MEMBER = "CREATE TABLE "+TABLE_NAME_MEMBER+"(_id INTEGER PRIMARY KEY AUTOINCREMENT, "
			                                       + COLUMN_NAME_REGID+" TEXT ,"+COLUMN_NAME_NICKNAME+" TEXT"+");";
	
	/** 회원정보 선택 SQL */
	public static final String SQL_SELECT_REGID  = "SELECT "+COLUMN_NAME_REGID+","+COLUMN_NAME_NICKNAME+" from "+TABLE_NAME_MEMBER+" where _id = 1";
	
	/** 회원 정보 입력 */
	public static final String SQL_INSERT_REGID  = "INSERT INTO "+TABLE_NAME_MEMBER+" ("+COLUMN_NAME_REGID+","+COLUMN_NAME_NICKNAME+") VALUES (?,?);";
			
			
	// 친구
	/** 친구정보 정보 테이블 이름 */
	public static final String TABLE_NAME_FRIEND = "T_FRIEND";
	/** 친구정보 전화번호 컬럼 */
	public static final String COLUMN_NAME_PHONE = "C_PHONE";
	/** 친구정보 이름 컬럼 */
	public static final String COLUMN_NAME_NAME  = "C_NAME";
	
	/** 친구 정보 테이블 생성 SQL*/
	public static final String SQL_CREATE_FRIEND = "CREATE TABLE "+TABLE_NAME_FRIEND+" ("+COLUMN_NAME_PHONE+" "
													+ "PRIMARY KEY, "+COLUMN_NAME_NAME+" TEXT);";
	/** 친구 정보 선택 SQL */
	public static final String SQL_SELECT_ALLFRIEND = "SELECT * from "+TABLE_NAME_FRIEND;
	
	/** 친구 정보 추가 SQL */  // 현재는 전화 번호만 등록 하게 되어 있다.
	public static final String SQL_INSERT_FRIEND = "INSERT INTO "+TABLE_NAME_FRIEND+" ("+COLUMN_NAME_PHONE+","+COLUMN_NAME_NAME+") VALUES (?,?);";
	
}
