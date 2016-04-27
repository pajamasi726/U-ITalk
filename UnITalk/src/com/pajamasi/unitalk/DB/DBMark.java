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
			
			
	// 친구 정보
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
	
	
	// 대화 정보 
	/** 대화 정보 테이블 이름 */
	public static final String TABLE_NAME_CHAT = "T_CHAT";
	
	/** 대화 정보 참가 친구 이름 */
	public static final String COLUMN_NAME_FPATH = "C_FPATH";
	
	/** 대화 정보 참가 친구 번호 */
	public static final String COLUMN_NAME_FPHONE = "C_FPHONE";
	
	/** 대화 정보 파일 경로 */
	public static final String COLUMN_NAME_FCOMMENT = "C_FCOMMENT";
	
	/** 안읽은 메세지 갯수 */
	public static final String COLUMN_NAME_FMSGCOUNT = "C_FMSGCOUNT";
	
	
	/** 대화 정보 테이블 생성 SQL*/
	public static final String SQL_CREATE_CHAT = "CREATE TABLE "+TABLE_NAME_CHAT+" (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
												+COLUMN_NAME_FPATH+" TEXT, "
												+COLUMN_NAME_FPHONE+" TEXT, "
												+COLUMN_NAME_FCOMMENT+" TEXT, "
												+COLUMN_NAME_FMSGCOUNT+" INTEGER default '0')";
	
	/** 대화 정보 추가 SQL */
	public static final String SQL_INSERT_CHAT = "INSERT INTO "+TABLE_NAME_CHAT+" ("
													+COLUMN_NAME_FPATH+","
													+COLUMN_NAME_FPHONE+","
													+COLUMN_NAME_FCOMMENT+","
													+COLUMN_NAME_FMSGCOUNT+") VALUES (?,?,?,?);";
	
	/** 대화 안읽은 메세지 카운트 추가 */
	public static final String SQL_UPDATE_CHATCOUNT = "UPDATE "+TABLE_NAME_CHAT+" SET "+COLUMN_NAME_FMSGCOUNT+"+1 "
													+ "WHERE "+COLUMN_NAME_FPATH+" =? "
													+ "AND "+COLUMN_NAME_FPHONE+" =?";
	
	/** 마지막 코멘트 수정 */
	public static final String SQL_UPDATE_COMMENT = "UPDATE "+TABLE_NAME_CHAT+" SET "+COLUMN_NAME_FCOMMENT+"= ?"
													+ "WHERE "+COLUMN_NAME_FPATH+" =? "
													+ "AND "+COLUMN_NAME_FPHONE+" =?";
	
	/** 모든 대화 정보 선택 SQL */
	public static final String SQL_SELECT_ALLCHAT = "SELECT * from "+TABLE_NAME_CHAT;
	
	
	
}
